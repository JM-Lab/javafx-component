package kr.jm.fx.dialog.pane;

import static java.util.stream.Collectors.toList;

import kr.jm.fx.path.JMFXPath;
import kr.jm.fx.template.AbstractJMFXApplication;

public class ConfirmDialogPaneApplication
		extends AbstractJMFXApplication<ConfirmDialogPane> {
	public ConfirmDialogPaneApplication() {
		super(new ConfirmDialogPane());
		jmfxComponent.setCurrentList(
				JMFXPath.getRootPath().getObservableChildrenList().stream()
						.flatMap(jp -> jp.getChildrenJMFXPathStream())
						.collect(toList()));
	}

	public static void main(String[] args) {
		launch(args);
	}

}
