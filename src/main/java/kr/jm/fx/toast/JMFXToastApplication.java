package kr.jm.fx.toast;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class JMFXToastApplication extends Application {

	@Override
	public void start(Stage stage) {
		Button progressiveDialogButton = new Button("Show Toast Message");
		progressiveDialogButton
				.setOnAction(e -> JMFXToast.getInstance(progressiveDialogButton)
						.show("Toast Message !!!"));
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 300, 250);
		stage.setScene(scene);
		stage.setTitle("Toast Test");
		root.setTop(progressiveDialogButton);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
