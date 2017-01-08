package kr.jm.fx.path.infocomplexview;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import kr.jm.fx.JMFXCompositeComponentInterface;
import kr.jm.fx.helper.JMFXIconFactory;
import kr.jm.fx.helper.JMFXValueEvent;
import kr.jm.fx.path.JMFXPath;
import kr.jm.fx.path.currentpathhbox.CurrentPathHBox;
import kr.jm.fx.path.tableview.PathTableView;
import kr.jm.fx.path.treetableview.PathTreeTableView;

public class PathInfoComplexView extends BorderPane
		implements JMFXCompositeComponentInterface {
	@FXML
	protected HBox headerHbox;
	@FXML
	protected CurrentPathHBox currentPathHBox;
	@FXML
	protected SplitMenuButton switchViewMenu;
	@FXML
	protected MenuItem tableViewSelectionMenu;
	@FXML
	protected MenuItem treeTableViewSelectionMenu;
	@FXML
	protected PathTreeTableView pathTreeTableView;
	@FXML
	protected PathTableView pathTableView;
	@FXML
	protected StackPane infoViewStackPane;

	protected Node currentView;
	protected Map<MenuItem, ImageView> menuItemImageViewMap;

	public PathInfoComplexView() {
		this(DefaultJMFXComponentI18nResourceBundle);
	}

	public PathInfoComplexView(ResourceBundle i18nResourceBundle) {
		initJMFXComponent(i18nResourceBundle);
	}

	private void setCurrentFrontViewAndSwitchViewMenuIcon(Node node,
			MenuItem menuItem) {
		this.currentView = node;
		this.currentView.toFront();
		switchViewMenu.setGraphic(menuItemImageViewMap.get(menuItem));
	}

	public JMFXValueEvent<JMFXPath> getCurrentPathChangeEvent() {
		return currentPathHBox.getCurrentPathChangeEvent();
	}

	public JMFXValueEvent<List<JMFXPath>> getSelectedJMFXPathListEvent() {
		return pathTreeTableView.getSelectedJMFXPathListEvent();
	}

	public void changePathInfo(JMFXPath jmfxPath) {
		currentPathHBox.changeCurrentPath(jmfxPath);
	}

	@Override
	public void initializeJMFXEvent() {
		getCurrentPathChangeEvent().addListener(jmfxPath -> pathTreeTableView
				.openDirectoryPath(Optional.ofNullable(jmfxPath)
						.filter(JMFXPath::isDirectory).orElseGet(
								() -> jmfxPath.getParent().orElse(jmfxPath))));
		getCurrentPathChangeEvent()
				.addListener(pathTableView::changeRowsIntoChildrenInfo);
		pathTreeTableView.getExpansionChangeEvent()
				.addListener(treeItem -> currentPathHBox
						.changeCurrentPath(treeItem.getValue()));
		pathTableView.getOpenDirectoryEvent()
				.addListener(currentPathHBox::changeCurrentPath);
	}

	@FXML
	protected void selectTableViewMenu() {
		setCurrentFrontViewAndSwitchViewMenuIcon(pathTableView,
				tableViewSelectionMenu);
	}

	@FXML
	protected void selectTreeTableViewMenu() {
		setCurrentFrontViewAndSwitchViewMenuIcon(pathTreeTableView,
				treeTableViewSelectionMenu);
	}

	@FXML
	protected void switchView() {
		if (currentView.equals(pathTableView))
			selectTreeTableViewMenu();
		else
			selectTableViewMenu();
	}

	@Override
	public void initializeView() {
		HBox.setHgrow(currentPathHBox, Priority.ALWAYS);
		String tableViewImageUrl = "icon/lines.png";
		String treeTableViewImageUrl = "icon/tree-table.png";

		tableViewSelectionMenu.setGraphic(
				JMFXIconFactory.buildImageView(tableViewImageUrl, 16, 16));
		treeTableViewSelectionMenu.setGraphic(
				JMFXIconFactory.buildImageView(treeTableViewImageUrl, 16, 16));
		menuItemImageViewMap = new HashMap<>(2);
		menuItemImageViewMap.put(tableViewSelectionMenu,
				JMFXIconFactory.buildImageView(tableViewImageUrl, 16, 16));
		menuItemImageViewMap.put(treeTableViewSelectionMenu,
				JMFXIconFactory.buildImageView(treeTableViewImageUrl, 16, 16));
		setCurrentFrontViewAndSwitchViewMenuIcon(pathTableView,
				tableViewSelectionMenu);
		pathTableView.changeRowsIntoChildrenInfo(JMFXPath.getRootPath());
	}

	public void setShowHidden(boolean b) {
		pathTableView.setShowHidden(b);
		pathTreeTableView.setShowHidden(b);
	}

}
