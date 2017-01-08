package kr.jm.fx.dialog;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import kr.jm.utils.JMProgressiveManager;
import kr.jm.utils.helper.JMPath;
import kr.jm.utils.helper.JMThread;

public class JMFXDialogApplication extends Application {

	@Override
	public void start(Stage stage) {
		Button progressiveDialogButton = new Button("progressiveDialog");
		JMProgressiveManager<?, ?> progressiveManager =
				new JMProgressiveManager<>(
						JMPath.getSubPathList(JMPath.getCurrentPath()), p -> {
							JMThread.sleep(10);
							System.out.println(p);
							return null;
						});
		progressiveDialogButton.setOnAction(e -> {
			JMFXDialog.buildProgressiveDialog(progressiveManager, "test", null)
					.show();
			progressiveManager.start();
		});
		Scene scene = new Scene(progressiveDialogButton, 300, 250);
		stage.setScene(scene);
		stage.setTitle("Progress Controls");
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
