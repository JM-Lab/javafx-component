package kr.jm.fx.path.tableview;

import static kr.jm.utils.helper.JMConsumer.getConsumer;
import static kr.jm.utils.helper.JMLambda.consumeByBoolean;
import static kr.jm.utils.helper.JMPredicate.getTrue;
import static kr.jm.utils.helper.JMPredicate.negate;
import static kr.jm.utils.helper.JMPredicate.peek;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import kr.jm.fx.JMFXComponentInterface;
import kr.jm.fx.helper.JMFXKeyEvent;
import kr.jm.fx.helper.JMFXMouseEvent;
import kr.jm.fx.helper.JMFXValueEvent;
import kr.jm.fx.path.JMFXPath;
import kr.jm.utils.FileSize;
import kr.jm.utils.helper.JMOptional;

public class PathTableView extends TableView<JMFXPath>
		implements JMFXComponentInterface {

	@FXML
	protected TableColumn<JMFXPath, JMFXPath> nameColumn;
	@FXML
	protected TableColumn<JMFXPath, String> dateModifiedColumn;
	@FXML
	protected TableColumn<JMFXPath, String> typeColumn;
	@FXML
	protected TableColumn<JMFXPath, FileSize> sizeColumn;

	@FXML
	protected void onKeyPressedOfInfoTable(KeyEvent event) {
		Optional.ofNullable(getSelectionModel().getSelectedItem())
				.ifPresent(jmfxPath -> JMFXKeyEvent.fireEnter(event,
						openDirectoryFunction(jmfxPath)));
	}

	@FXML
	protected void onMousePressedOfInfoTable(MouseEvent event) {
		Optional.ofNullable(getSelectionModel().getSelectedItem())
				.filter(JMFXPath::isDirectory).ifPresent(
						jmfxPath -> JMFXMouseEvent.fireLeftDoublePressed(event,
								openDirectoryFunction(jmfxPath)));
	}

	private Runnable openDirectoryFunction(JMFXPath jmfxPath) {
		return () -> changeRowsIntoChildrenInfo(jmfxPath);
	}

	@FXML
	protected void onSortOfInfoTable() {
		refresh();
	}

	protected PathTableViewModel pathInfoTableModel;
	protected JMFXValueEvent<JMFXPath> openDirectoryEvent =
			new JMFXValueEvent<>();
	protected JMFXValueEvent<List<JMFXPath>> selectedJMFXPathListEvent =
			new JMFXValueEvent<>();
	protected boolean showHidden = false;

	public PathTableView() {
		this(DefaultJMFXComponentI18nResourceBundle);
	}

	public PathTableView(ResourceBundle i18nResourceBundle) {
		this.pathInfoTableModel = new PathTableViewModel();
		initJMFXComponent(i18nResourceBundle);
	}

	private ObservableValue<JMFXPath> buildNameCellProperty(
			CellDataFeatures<JMFXPath, JMFXPath> cellData) {
		return cellData.getValue().jmfxPathProperty();
	}

	public void changeRowsIntoChildrenInfo(JMFXPath jmfxPath) {
		Optional.ofNullable(jmfxPath).map(JMFXPath::getObservableChildrenList)
				.filter(peek(list -> pathInfoTableModel
						.setFilteredAndSortedModelAndBind(list,
								showHidden ? getTrue()
										: negate(JMFXPath::isHidden),
								comparatorProperty())))
				.filter(peek(getConsumer(this::bindModelToView)))
				.ifPresent(list -> openDirectoryEvent.changeValue(jmfxPath));
	}

	@Override
	public void bindModelToView() {
		setItems(pathInfoTableModel.getModel());
	}

	protected class PathNameTableCell
			extends TextFieldTableCell<JMFXPath, JMFXPath> {

		public PathNameTableCell() {
			super();
		}

		public PathNameTableCell(StringConverter<JMFXPath> stringConverter) {
			super(stringConverter);
		}

		@Override
		public void updateItem(JMFXPath item, boolean empty) {
			super.updateItem(item, empty);
			consumeByBoolean(item == null || empty, item, this::clearCell,
					this::renderCell);
		}

		private void clearCell(JMFXPath item) {
			setText(null);
			setGraphic(null);
		}

		private void renderCell(JMFXPath item) {
			setText(item.getName());
			setGraphic(item.getIcon());
		}
	}

	@Override
	public void initializeView() {
		this.setPlaceholder(new Label());
		this.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		this.nameColumn.setPrefWidth(270);
		this.nameColumn.setCellValueFactory(this::buildNameCellProperty);
		this.nameColumn.setCellFactory(t -> new PathNameTableCell());
		this.dateModifiedColumn.setStyle("-fx-alignment: CENTER;");
		this.dateModifiedColumn.setPrefWidth(120);
		this.dateModifiedColumn.setCellValueFactory(
				cellData -> cellData.getValue().dateModifiedProperty());
		this.sizeColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		this.sizeColumn.setCellValueFactory(
				cellData -> cellData.getValue().fileSizeProperty());
		this.typeColumn.setStyle("-fx-alignment: CENTER;");
		this.typeColumn.setCellValueFactory(
				cellData -> cellData.getValue().typeProperty());
	}

	@Override
	public void initializeJMFXEvent() {
		getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> selectedJMFXPathListEvent
						.changeValue(getSelectionModel().getSelectedItems()));
	}

	public JMFXValueEvent<JMFXPath> getOpenDirectoryEvent() {
		return openDirectoryEvent;
	}

	public JMFXValueEvent<List<JMFXPath>> getSelectedJMFXPathListEvent() {
		return selectedJMFXPathListEvent;
	}

	public void setShowHidden(boolean bool) {
		this.showHidden = bool;
	}

	public void refreshView() {
		JMOptional.getOptional(getItems()).map(l -> l.get(0))
				.flatMap(JMFXPath::getParent)
				.ifPresent(this::changeRowsIntoChildrenInfo);
	}

}
