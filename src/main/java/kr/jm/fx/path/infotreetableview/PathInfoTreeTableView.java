package kr.jm.fx.path.infotreetableview;

import static kr.jm.utils.helper.JMLambda.runIfTrue;

import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import kr.jm.fx.JMFXCompositeComponentInterface;
import kr.jm.fx.helper.JMFXValueEvent;
import kr.jm.fx.path.JMFXPath;
import kr.jm.fx.path.currentpathhbox.CurrentPathHBox;
import kr.jm.fx.path.treetableview.PathTreeTableView;
import kr.jm.utils.helper.JMLambda;

public class PathInfoTreeTableView extends BorderPane
		implements JMFXCompositeComponentInterface {

	@FXML
	protected CurrentPathHBox currentPathHBox;
	@FXML
	protected PathTreeTableView pathTreeTableView;

	public PathInfoTreeTableView() {
		this(DefaultJMFXComponentI18nResourceBundle);
	}

	public PathInfoTreeTableView(ResourceBundle i18nResourceBundle) {
		initJMFXComponent(i18nResourceBundle);
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
		getCurrentPathChangeEvent().addListener(jmfxPath -> runIfTrue(
				jmfxPath != null,
				() -> JMLambda.consumeByBoolean(jmfxPath.isDirectory(),
						jmfxPath, pathTreeTableView::openDirectoryPath,
						jp -> jp.getParent().ifPresent(
								pathTreeTableView::openDirectoryPath))));
		pathTreeTableView.getExpansionChangeEvent()
				.addListener(treeItem -> currentPathHBox
						.changeCurrentPath(treeItem.getValue()));
	}

	@Override
	public void initializeView() {

	}

}
