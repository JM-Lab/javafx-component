package kr.jm.fx.path.treeview;

import static java.util.stream.Collectors.toList;
import static kr.jm.utils.helper.JMPredicate.getTrue;
import static kr.jm.utils.helper.JMPredicate.negate;
import static kr.jm.utils.helper.JMPredicate.peek;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javafx.scene.control.TreeItem;
import kr.jm.fx.path.JMFXPath;
import kr.jm.fx.template.AbstractJMFXTreeItemModel;

/**
 * The Class PathTreeViewModel.
 */
public class PathTreeViewModel extends AbstractJMFXTreeItemModel<JMFXPath> {

	/**
	 * Instantiates a new path tree view model.
	 *
	 * @param jmfxRootPath
	 *            the jmfx root path
	 */
	public PathTreeViewModel(JMFXPath jmfxRootPath) {
		super(jmfxRootPath);
	}

	private boolean directoriesOnly = false;
	private boolean showHidden = false;

	@Override
	protected TreeItem<JMFXPath> buildNewTreeItem(JMFXPath value) {
		return new TreeItem<JMFXPath>(value, value.getIcon()) {
			@Override
			public boolean isLeaf() {
				return !getValue().isDirectory();
			}
		};
	}

	@Override
	protected List<JMFXPath> buildChildrenValueList(JMFXPath value) {
		return directoriesOnly
				? buildChildrenValueList(value.getChildrenDirctoryJMFXStream())
				: buildChildrenValueList(value.getChildrenJMFXPathStream());
	}

	private List<JMFXPath> buildChildrenValueList(Stream<JMFXPath> stream) {
		return stream
				.filter(showHidden ? getTrue() : negate(JMFXPath::isHidden))
				.collect(toList());
	}

	/**
	 * Gets the children tree item stream.
	 *
	 * @param value
	 *            the value
	 * @return the children tree item stream
	 */
	public Stream<TreeItem<JMFXPath>>
			getChildrenTreeItemStream(TreeItem<JMFXPath> value) {
		return buildChildenTreeItemList(value).stream();
	}

	/**
	 * Open directory tree item.
	 *
	 * @param jmfxPath
	 *            the jmfx path
	 * @return the optional
	 */
	public Optional<TreeItem<JMFXPath>>
			openDirectoryTreeItem(JMFXPath jmfxPath) {
		return Optional.of(jmfxPath)
				.filter(negate(JMFXPath.getRootPath()::equals))
				.filter(JMFXPath::isDirectory)
				.filter(peek(this::openAllAncestors)).map(this::getTreeItem);
	}

	private void openAllAncestors(JMFXPath jmfxPath) {
		Optional<JMFXPath> jmfxPathAsOpt = Optional.ofNullable(jmfxPath);
		jmfxPathAsOpt.ifPresent(jp -> jp.getAncestorDirectoryJMFXPathStream()
				.forEach(this::expandTreeItem));
		expandTreeItem(jmfxPath);
	}

	private void expandTreeItem(JMFXPath jmfxPath) {
		Optional.ofNullable(getTreeItem(jmfxPath))
				.ifPresent(this::expandTreeItem);
	}

	private void expandTreeItem(TreeItem<JMFXPath> treeItem) {
		if (!treeItem.isLeaf())
			treeItem.setExpanded(true);
	}

	public void setDirectoriesOnly(boolean directoriesOnly) {
		this.directoriesOnly = directoriesOnly;
	}

	/**
	 * Reexpand.
	 *
	 * @param jmfxPath
	 *            the jmfx path
	 */
	public void reexpand(JMFXPath jmfxPath) {
		Optional.ofNullable(getTreeItem(jmfxPath)).filter(TreeItem::isExpanded)
				.ifPresent(this::reexpand);
	}

	private void reexpand(TreeItem<JMFXPath> treeItem) {
		treeItem.setExpanded(false);
		treeItem.setExpanded(true);
	}

	synchronized public void setShowHidden(boolean bool) {
		if (this.showHidden == bool)
			return;
		this.showHidden = bool;
		// model.getChildren().stream().filter(ti -> ti.isExpanded())
		// .forEach(this::reexpand);
		getAllTreeItems().stream().filter(TreeItem::isExpanded)
				.map(TreeItem::getValue).forEach(this::openDirectoryTreeItem);
	}

}
