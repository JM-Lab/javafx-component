package kr.jm.fx.path.treeview;

import static java.util.stream.Collectors.toList;
import static kr.jm.utils.helper.JMLambda.runIfTrue;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import kr.jm.fx.JMFXComponentInterface;
import kr.jm.fx.helper.JMFXValueEvent;
import kr.jm.fx.path.JMFXPath;
import kr.jm.utils.helper.JMOptional;

public class PathTreeView extends TreeView<JMFXPath>
		implements JMFXComponentInterface {

	protected PathTreeViewModel pathTreeViewModel;
	protected JMFXValueEvent<JMFXPath> selectDirectoryEvent =
			new JMFXValueEvent<>();
	protected JMFXValueEvent<List<JMFXPath>> selectedJMFXPathListEvent =
			new JMFXValueEvent<>();
	protected JMFXValueEvent<TreeItem<JMFXPath>> expansionChangeEvent =
			new JMFXValueEvent<>();

	public PathTreeView() {
		this(DefaultJMFXComponentI18nResourceBundle);
	}

	public PathTreeView(ResourceBundle i18nResourceBundle) {
		initJMFXComponent(i18nResourceBundle);
	}

	@Override
	public void bindModelToView() {
		this.pathTreeViewModel = new PathTreeViewModel(
				JMFXPath.getInstance("PathTreeView-" + System.nanoTime()));
		TreeItem<JMFXPath> model = pathTreeViewModel.getModel();
		JMFXPath.getRootPath().getObservableChildrenList().forEach(
				jmfxPath -> this.pathTreeViewModel.addChildInRoot(jmfxPath));
		setRoot(model);
	}

	@Override
	public void initializeJMFXEvent() {
		getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue,
						newValue) -> getCurrentSelectionListAsOpt().ifPresent(
								selectedJMFXPathListEvent::changeValue));
		pathTreeViewModel
				.setExpansionChangeHook(expansionChangeEvent::changeValue);
		selectedJMFXPathListEvent
				.addListener(list -> JMOptional.getOptional(list)
						.map(l -> l.get(0)).ifPresent(this::selectDircotyPath));
	}

	private void selectDircotyPath(JMFXPath jmfxPath) {
		runIfTrue(jmfxPath.isDirectory(),
				() -> selectDirectoryEvent.changeValue(jmfxPath));
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

	public JMFXValueEvent<JMFXPath> getSelectDirectoryEvent() {
		return selectDirectoryEvent;
	}

	public JMFXValueEvent<List<JMFXPath>> getSelectedJMFXPathListEvent() {
		return selectedJMFXPathListEvent;
	}

	public JMFXValueEvent<TreeItem<JMFXPath>> getExpansionChangeEvent() {
		return expansionChangeEvent;
	}

	public void setShowHidden(boolean b) {
		pathTreeViewModel.setShowHidden(b);
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
