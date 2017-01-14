package kr.jm.fx.path.infobrowser;

import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import kr.jm.fx.JMFXCompositeComponentInterface;
import kr.jm.fx.helper.JMFXValueEvent;
import kr.jm.fx.path.JMFXPath;
import kr.jm.fx.path.infocomplexview.PathInfoComplexView;
import kr.jm.fx.path.treeview.PathTreeView;

/**
 * The Class PathInfoBrowser.
 */
public class PathInfoBrowser extends SplitPane
		implements JMFXCompositeComponentInterface {

	@FXML
	protected PathTreeView pathTreeView;
	@FXML
	protected PathInfoComplexView pathInfoComplexView;

	/**
	 * Instantiates a new path info browser.
	 */
	public PathInfoBrowser() {
		this(DefaultJMFXComponentI18nResourceBundle);
	}

	/**
	 * Instantiates a new path info browser.
	 *
	 * @param i18nResourceBundle
	 *            the i 18 n resource bundle
	 */
	public PathInfoBrowser(ResourceBundle i18nResourceBundle) {
		initJMFXComponent(i18nResourceBundle);
	}

	/**
	 * Register current path change hook.
	 *
	 * @return the JMFX value event
	 */
	public JMFXValueEvent<JMFXPath> registerCurrentPathChangeHook() {
		return pathInfoComplexView.getCurrentPathChangeEvent();
	}

	/**
	 * Register select items hook.
	 *
	 * @return the JMFX value event
	 */
	public JMFXValueEvent<List<JMFXPath>> registerSelectItemsHook() {
		return pathInfoComplexView.getSelectedJMFXPathListEvent();
	}

	/**
	 * Change path info.
	 *
	 * @param jmfxPath
	 *            the jmfx path
	 */
	public void changePathInfo(JMFXPath jmfxPath) {
		pathInfoComplexView.changePathInfo(jmfxPath);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.jm.fx.JMFXComponentInterface#initializeJMFXEvent()
	 */
	@Override
	public void initializeJMFXEvent() {
		pathTreeView.getSelectTreePathEvent().addListener(this::changePathInfo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.jm.fx.JMFXComponentInterface#initializeView()
	 */
	@Override
	public void initializeView() {
		pathTreeView.setDirectoriesOnly(true);
		pathTreeView.setRootPathList(
				JMFXPath.getRootPath().getObservableChildrenList());
	}

}
