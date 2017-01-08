package kr.jm.fx.helper;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class JMFXMouseEvent {

	public static void fireLeftDoublePressed(MouseEvent event,
			Runnable runnable) {
		fire(event, MouseButton.PRIMARY, 2, runnable);
	}

	public static void fireLeftPressed(MouseEvent event, Runnable runnable) {
		fire(event, MouseButton.PRIMARY, 1, runnable);
	}

	public static void fire(MouseEvent event, MouseButton clickedButton,
			int clickedCount, Runnable runnable) {
		if (event.getButton().equals(clickedButton)
				&& event.getClickCount() == clickedCount) {
			runnable.run();
			event.consume();
		}
	}

}
