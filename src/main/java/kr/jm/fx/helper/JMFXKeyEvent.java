package kr.jm.fx.helper;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * The Class JMFXKeyEvent.
 */
public class JMFXKeyEvent {

	/**
	 * Fire enter.
	 *
	 * @param event
	 *            the event
	 * @param runnable
	 *            the runnable
	 */
	public static void fireEnter(KeyEvent event, Runnable runnable) {
		fire(event, KeyCode.ENTER, runnable);
	}

	/**
	 * Fire.
	 *
	 * @param event
	 *            the event
	 * @param keyCode
	 *            the key code
	 * @param runnable
	 *            the runnable
	 */
	public static void fire(KeyEvent event, KeyCode keyCode,
			Runnable runnable) {
		if (keyCode.equals(event.getCode())) {
			runnable.run();
			event.consume();
		}
	}
}
