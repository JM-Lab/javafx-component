package kr.jm.fx.path.infotableview;

import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import kr.jm.fx.JMFXCompositeComponentInterface;
import kr.jm.fx.helper.JMFXValueEvent;
import kr.jm.fx.path.JMFXPath;
import kr.jm.fx.path.currentpathhbox.CurrentPathHBox;
import kr.jm.fx.path.tableview.PathTableView;

public class PathInfoTableView extends BorderPane
		implements JMFXCompositeComponentInterface {

	@FXML
	protected CurrentPathHBox currentPathHBox;
	@FXML
	protected PathTableView pathTableView;

	public PathInfoTableView() {
		this(DefaultJMFXComponentI18nResourceBundle);
	}

	public PathInfoTableView(ResourceBundle i18nResourceBundle) {
		initJMFXComponent(i18nResourceBundle);
	}

	public JMFXValueEvent<JMFXPath> getCurrentPathChangeEvent() {
		return currentPathHBox.getCurrentPathChangeEvent();
	}

	public JMFXValueEvent<List<JMFXPath>> getSelectJMFXPathListEvent() {
		return pathTableView.getSelectedJMFXPathListEvent();
	}

	public void changePathInfo(JMFXPath jmfxPath) {
		currentPathHBox.changeCurrentPath(jmfxPath);
	}

	@Override
	public void initializeJMFXEvent() {
		getCurrentPathChangeEvent()
				.addListener(pathTableView::changeRowsIntoChildrenInfo);
		pathTableView.getOpenDirectoryEvent()
				.addListener(currentPathHBox::changeCurrentPath);
	}

	@Override
	public void initializeView() {
		JMFXPath rootPath = JMFXPath.getRootPath();
		currentPathHBox.changeCurrentPath(rootPath);
		pathTableView.changeRowsIntoChildrenInfo(rootPath);
	}

}
