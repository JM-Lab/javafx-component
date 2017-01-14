package kr.jm.fx.dialog.pane;

import static kr.jm.utils.helper.JMPredicate.peek;
import static kr.jm.utils.helper.JMPredicate.peekToRun;

import java.util.Optional;

import kr.jm.fx.template.AbstractJMFXApplication;
import kr.jm.utils.JMProgressiveManager;
import kr.jm.utils.helper.JMPath;
import kr.jm.utils.helper.JMThread;

/**
 * The Class ProgresiveDialogPaneApplication.
 */
public class ProgresiveDialogPaneApplication
		extends AbstractJMFXApplication<ProgressiveDialogPane> {

	/**
	 * Instantiates a new progresive dialog pane application.
	 */
	public ProgresiveDialogPaneApplication() {
		super(new ProgressiveDialogPane());
		JMProgressiveManager<?, ?> progressiveManager =
				new JMProgressiveManager<>(
						JMPath.getSubPathList(JMPath.getCurrentPath()),
						path -> Optional.ofNullable(path)
								.filter(peekToRun(() -> JMThread.sleep(10)))
								.filter(peek(System.out::println)));
		jmfxComponent.setProgressiveManager(progressiveManager);
		JMThread.runAsync(() -> {
			JMThread.sleep(1000);
			progressiveManager.start();
		});

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
