package kr.jm.fx.template;

import static java.util.stream.Collectors.toList;
import static kr.jm.utils.helper.JMLambda.consumeByBoolean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import kr.jm.utils.datastructure.JMMap;
import kr.jm.utils.helper.JMOptional;

public abstract class AbstractJMFXTreeItemModel<B>
		extends AbstractJMFXModel<TreeItem<B>> {
	private Map<B, TreeItem<B>> indexedTreeItemMap = new ConcurrentHashMap<>();
	protected Consumer<TreeItem<B>> expansionChangeHook;

	public AbstractJMFXTreeItemModel(B root) {
		super(new TreeItem<B>(root) {
			@Override
			public boolean isLeaf() {
				return false;
			}
		});
		indexedTreeItemMap.put(root, model);
		setExpansionChangeListener(model);
		model.setExpanded(true);
	}

	public boolean isExist(B value) {
		return indexedTreeItemMap.containsKey(value);
	}

	public TreeItem<B> getTreeItem(B value) {
		return indexedTreeItemMap.get(value);
	}

	public void setChildrenInRoot(List<B> chidrenList) {
		synchronized (model) {
			model.getChildren().clear();
			chidrenList.stream()
					.forEach(child -> addChildInParent(model, child));
		}
	}

	public boolean addChildInRoot(B value) {
		return addChildInParent(model, value);
	}

	public boolean addChildInParent(TreeItem<B> parent, B value) {
		synchronized (parent) {
			return parent.getChildren().add(JMMap.putGetNew(indexedTreeItemMap,
					value,
					setExpansionChangeListener(buildNewTreeItem(value))));
		}
	}

	public boolean addChildrenInTreeItem(TreeItem<B> parent,
			List<B> chidrenList) {
		synchronized (parent) {
			return chidrenList.stream()
					.allMatch(value -> addChildInParent(parent, value));
		}
	}

	abstract protected List<B> buildChildrenValueList(B value);

	abstract protected TreeItem<B> buildNewTreeItem(B value);

	public Optional<TreeItem<B>> findTreeItemInChildren(
			TreeItem<B> parentTreeItem, B conditionTarget) {
		return getChildenTreeItemList(parentTreeItem).stream()
				.filter(treeItem -> treeItem.getValue().equals(conditionTarget))
				.findFirst();
	}

	public List<TreeItem<B>>
			getChildenTreeItemList(TreeItem<B> parentTreeItem) {
		return parentTreeItem.isLeaf() ? Collections.emptyList()
				: JMOptional.getOptional(parentTreeItem.getChildren())
						.orElseGet(
								() -> buildChildenTreeItemList(parentTreeItem));
	}

	public ObservableList<TreeItem<B>>
			buildChildenTreeItemList(TreeItem<B> parent) {
		return addChildrenInTreeItem(parent,
				buildChildrenValueList(parent.getValue()))
						? parent.getChildren()
						: FXCollections.emptyObservableList();
	}

	public List<TreeItem<B>> removeAllInParent(TreeItem<B> treeItem) {
		synchronized (treeItem) {
			return new ArrayList<>(treeItem.getChildren()).stream()
					.map(this::removeInParent).collect(toList());
		}
	}

	public TreeItem<B> removeInParent(TreeItem<B> treeItem) {
		synchronized (treeItem) {
			TreeItem<B> parent = treeItem.getParent();
			ObservableList<TreeItem<B>> children = parent.getChildren();
			children.remove(treeItem);
			return indexedTreeItemMap.remove(treeItem.getValue());
		}
	}

	protected TreeItem<B> setExpansionChangeListener(TreeItem<B> treeItem) {
		if (!treeItem.isLeaf())
			treeItem.expandedProperty()
					.addListener(getExpansionControl(treeItem));
		return treeItem;
	}

	private ChangeListener<Boolean> getExpansionControl(TreeItem<B> treeItem) {
		return (observable, oldValue, newValue) -> {
			Optional.ofNullable(expansionChangeHook)
					.ifPresent(supply -> supply.accept(treeItem));
			consumeByBoolean(newValue, treeItem, this::fireExpansionAction,
					this::removeAllInParent);
		};
	}

	private void fireExpansionAction(TreeItem<B> treeItem) {
		addChildrenInTreeItem(treeItem,
				buildChildrenValueList(treeItem.getValue()));
	}

	public void
			setExpansionChangeHook(Consumer<TreeItem<B>> expansionChangeHook) {
		this.expansionChangeHook = expansionChangeHook;
	}

	public Collection<TreeItem<B>> getAllTreeItems() {
		return indexedTreeItemMap.values();
	}

}
