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

public class PathInfoBrowser extends SplitPane
		implements JMFXCompositeComponentInterface {

	@FXML
	protected PathTreeView pathTreeView;
	@FXML
	protected PathInfoComplexView pathInfoComplexView;

	public PathInfoBrowser() {
		this(DefaultJMFXComponentI18nResourceBundle);
	}

	public PathInfoBrowser(ResourceBundle i18nResourceBundle) {
		initJMFXComponent(i18nResourceBundle);
	}

	public JMFXValueEvent<JMFXPath> registerCurrentPathChangeHook() {
		return pathInfoComplexView.getCurrentPathChangeEvent();
	}

	public JMFXValueEvent<List<JMFXPath>> registerSelectItemsHook() {
		return pathInfoComplexView.getSelectedJMFXPathListEvent();
	}

	public void changePathInfo(JMFXPath jmfxPath) {
		pathInfoComplexView.changePathInfo(jmfxPath);
	}

	@Override
	public void initializeJMFXEvent() {
		pathTreeView.getSelectDirectoryEvent()
				.addListener(this::changePathInfo);
	}

	@Override
	public void initializeView() {
		pathTreeView.setDirectoriesOnly(true);
	}

}
