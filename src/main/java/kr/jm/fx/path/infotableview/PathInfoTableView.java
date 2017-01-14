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

/**
 * The Class PathInfoTableView.
 */
public class PathInfoTableView extends BorderPane
		implements JMFXCompositeComponentInterface {

	@FXML
	protected CurrentPathHBox currentPathHBox;
	@FXML
	protected PathTableView pathTableView;

	/**
	 * Instantiates a new path info table view.
	 */
	public PathInfoTableView() {
		this(DefaultJMFXComponentI18nResourceBundle);
	}

	/**
	 * Instantiates a new path info table view.
	 *
	 * @param i18nResourceBundle
	 *            the i 18 n resource bundle
	 */
	public PathInfoTableView(ResourceBundle i18nResourceBundle) {
		initJMFXComponent(i18nResourceBundle);
	}

	public JMFXValueEvent<JMFXPath> getCurrentPathChangeEvent() {
		return currentPathHBox.getCurrentPathChangeEvent();
	}

	public JMFXValueEvent<List<JMFXPath>> getSelectJMFXPathListEvent() {
		return pathTableView.getSelectedJMFXPathListEvent();
	}

	/**
	 * Change path info.
	 *
	 * @param jmfxPath
	 *            the jmfx path
	 */
	public void changePathInfo(JMFXPath jmfxPath) {
		currentPathHBox.changeCurrentPath(jmfxPath);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.jm.fx.JMFXComponentInterface#initializeJMFXEvent()
	 */
	@Override
	public void initializeJMFXEvent() {
		getCurrentPathChangeEvent()
				.addListener(pathTableView::changeRowsIntoChildrenInfo);
		pathTableView.getOpenDirectoryEvent()
				.addListener(currentPathHBox::changeCurrentPath);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.jm.fx.JMFXComponentInterface#initializeView()
	 */
	@Override
	public void initializeView() {
		JMFXPath rootPath = JMFXPath.getRootPath();
		currentPathHBox.changeCurrentPath(rootPath);
		pathTableView.changeRowsIntoChildrenInfo(rootPath);
	}

}
