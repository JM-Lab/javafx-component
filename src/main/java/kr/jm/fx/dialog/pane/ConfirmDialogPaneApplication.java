package kr.jm.fx.dialog.pane;

import static java.util.stream.Collectors.toList;

import kr.jm.fx.path.JMFXPath;
import kr.jm.fx.template.AbstractJMFXApplication;

/**
 * The Class ConfirmDialogPaneApplication.
 */
public class ConfirmDialogPaneApplication
		extends AbstractJMFXApplication<ConfirmDialogPane> {

	/**
	 * Instantiates a new confirm dialog pane application.
	 */
	public ConfirmDialogPaneApplication() {
		super(new ConfirmDialogPane());
		jmfxComponent.setCurrentList(
				JMFXPath.getRootPath().getObservableChildrenList().stream()
						.flatMap(jp -> jp.getChildrenJMFXPathStream())
						.collect(toList()));
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
