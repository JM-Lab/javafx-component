package kr.jm.fx.helper;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class JMFXKeyEvent {

	public static void fireEnter(KeyEvent event, Runnable runnable) {
		fire(event, KeyCode.ENTER, runnable);
	}

	public static void fire(KeyEvent event, KeyCode keyCode,
			Runnable runnable) {
		if (keyCode.equals(event.getCode())) {
			runnable.run();
			event.consume();
		}
	}
}
