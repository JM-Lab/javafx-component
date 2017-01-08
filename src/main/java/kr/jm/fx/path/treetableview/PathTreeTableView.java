package kr.jm.fx.path.treetableview;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableView;
import kr.jm.fx.JMFXComponentInterface;
import kr.jm.fx.helper.JMFXValueEvent;
import kr.jm.fx.path.JMFXPath;
import kr.jm.fx.path.treeview.PathTreeViewModel;
import kr.jm.utils.FileSize;
import kr.jm.utils.helper.JMOptional;

public class PathTreeTableView extends TreeTableView<JMFXPath>
		implements JMFXComponentInterface {

	@FXML
	protected TreeTableColumn<JMFXPath, JMFXPath> nameColumn;
	@FXML
	protected TreeTableColumn<JMFXPath, String> dateModifiedColumn;
	@FXML
	protected TreeTableColumn<JMFXPath, String> typeColumn;
	@FXML
	protected TreeTableColumn<JMFXPath, FileSize> sizeColumn;

	protected PathTreeViewModel pathTreeViewModel;
	protected JMFXValueEvent<List<JMFXPath>> selectedJMFXPathListEvent =
			new JMFXValueEvent<>();
	protected JMFXValueEvent<TreeItem<JMFXPath>> expansionChangeEvent =
			new JMFXValueEvent<>();

	public PathTreeTableView() {
		this(DefaultJMFXComponentI18nResourceBundle);
	}

	public PathTreeTableView(ResourceBundle i18nResourceBundle) {
		initJMFXComponent(i18nResourceBundle);
	}

	private ObservableValue<JMFXPath> buildNameCellProperty(
			CellDataFeatures<JMFXPath, JMFXPath> cellData) {
		return cellData.getValue().getValue().jmfxPathProperty();
	}

	@Override
	public void bindModelToView() {
		this.pathTreeViewModel = new PathTreeViewModel(JMFXPath.getRootPath());
		this.setRoot(pathTreeViewModel.getModel());
	}

	@Override
	public void initializeView() {
		this.setPlaceholder(new Label());
		this.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		this.nameColumn.setCellValueFactory(this::buildNameCellProperty);
		this.dateModifiedColumn.setStyle("-fx-alignment: CENTER;");
		this.dateModifiedColumn.setCellValueFactory(cellData -> cellData
				.getValue().getValue().dateModifiedProperty());
		this.sizeColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		this.sizeColumn.setCellValueFactory(
				cellData -> cellData.getValue().getValue().fileSizeProperty());
		this.typeColumn.setStyle("-fx-alignment: CENTER;");
		this.typeColumn.setCellValueFactory(
				cellData -> cellData.getValue().getValue().typeProperty());
	}

	@Override
	public void initializeJMFXEvent() {
		getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue,
						newValue) -> getCurrentSelectionListAsOpt().ifPresent(
								selectedJMFXPathListEvent::changeValue));
		pathTreeViewModel
				.setExpansionChangeHook(expansionChangeEvent::changeValue);
	}

	public void openDirectoryPath(JMFXPath jmfxPath) {
		pathTreeViewModel.openDirectoryTreeItem(jmfxPath)
				.ifPresent(treeItem -> scrollTo(getRow(treeItem)));
	}

	public Optional<List<JMFXPath>> getCurrentSelectionListAsOpt() {
		return JMOptional.getOptional(getSelectionModel().getSelectedItems())
				.map(list -> list.stream().map(TreeItem::getValue)
						.collect(toList()));
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

	public void setRootPathList(List<JMFXPath> rootPathList) {
		pathTreeViewModel.setChildrenInRoot(rootPathList);
	}

	public void addRootPath(JMFXPath jmfxPath) {
		pathTreeViewModel.addChildInRoot(jmfxPath);
	}

}
