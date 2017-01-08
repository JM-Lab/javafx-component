package kr.jm.fx.path.treeview;

import static java.util.stream.Collectors.toList;
import static kr.jm.utils.helper.JMLambda.consumeIfTrue;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import kr.jm.fx.JMFXComponentInterface;
import kr.jm.fx.helper.JMFXKeyEvent;
import kr.jm.fx.helper.JMFXMouseEvent;
import kr.jm.fx.helper.JMFXValueEvent;
import kr.jm.fx.path.JMFXPath;
import kr.jm.utils.helper.JMOptional;

public class PathTreeView extends TreeView<JMFXPath>
		implements JMFXComponentInterface {

	protected PathTreeViewModel pathTreeViewModel;
	protected JMFXValueEvent<JMFXPath> openTreePathEvent =
			new JMFXValueEvent<>();
	protected JMFXValueEvent<JMFXPath> selectTreePathEvent =
			new JMFXValueEvent<>();

	public PathTreeView() {
		this(DefaultJMFXComponentI18nResourceBundle);
	}

	public PathTreeView(ResourceBundle i18nResourceBundle) {
		initJMFXComponent(i18nResourceBundle);
	}

	@FXML
	public void onKeyReleasedOfPathTreeView(KeyEvent event) {
		JMFXKeyEvent.fireEnter(event, this::fireTreeNodeOpenEvent);
	}

	@FXML
	public void onMousePressedOfPathTreeView(MouseEvent event) {
		if (event.getClickCount() == 2)
			JMFXMouseEvent.fireLeftDoublePressed(event,
					this::fireTreeNodeOpenEvent);
	}

	private void fireTreeNodeOpenEvent() {
		TreeItem<JMFXPath> selectedItem = getSelectionModel().getSelectedItem();
		consumeIfTrue(!selectedItem.isLeaf(), selectedItem.getValue(),
				openTreePathEvent::changeValue);
	}

	@Override
	public void bindModelToView() {
		this.pathTreeViewModel = new PathTreeViewModel(
				JMFXPath.getInstance("PathTreeView-" + System.nanoTime()));
		setRoot(pathTreeViewModel.getModel());
	}

	@Override
	public void initializeJMFXEvent() {
		getSelectionModel().selectedItemProperty()
				.addListener((ob, o, n) -> Optional.ofNullable(n)
						.map(TreeItem::getValue)
						.ifPresent(selectTreePathEvent::changeValue));
	}

	@Override
	public void initializeView() {

	}

	public void setDirectoriesOnly(boolean directoriesOnly) {
		this.pathTreeViewModel.setDirectoriesOnly(directoriesOnly);
	}

	public Optional<List<JMFXPath>> getCurrentSelectionListAsOpt() {
		return JMOptional.getOptional(getSelectionModel().getSelectedItems())
				.map(list -> list.stream().map(TreeItem::getValue)
						.collect(toList()));
	}

	public JMFXValueEvent<JMFXPath> getOpenTreePathEvent() {
		return openTreePathEvent;
	}

	public JMFXValueEvent<JMFXPath> getSelectTreePathEvent() {
		return selectTreePathEvent;
	}

	public void setShowHidden(boolean b) {
		List<TreeItem<JMFXPath>> expandedList =
				pathTreeViewModel.getAllTreeItems().stream()
						.filter(TreeItem::isExpanded).collect(toList());
		synchronized (this) {
			pathTreeViewModel.setShowHidden(b);
			expandedList.stream().map(TreeItem::getValue)
					.forEach(pathTreeViewModel::openDirectoryTreeItem);
		}
	}

	public void openDirectoryPath(JMFXPath jmfxPath) {
		pathTreeViewModel.openDirectoryTreeItem(jmfxPath)
				.ifPresent(treeItem -> scrollTo(getRow(treeItem)));
	}

	public void setRootPathList(List<JMFXPath> rootPathList) {
		pathTreeViewModel.setChildrenInRoot(rootPathList);
	}

	public void addRootPath(JMFXPath jmfxPath) {
		pathTreeViewModel.addChildInRoot(jmfxPath);
	}

}
