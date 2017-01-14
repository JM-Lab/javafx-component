package kr.jm.fx.helper;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * The Class JMFXMouseEvent.
 */
public class JMFXMouseEvent {

	/**
	 * Fire left double pressed.
	 *
	 * @param event
	 *            the event
	 * @param runnable
	 *            the runnable
	 */
	public static void fireLeftDoublePressed(MouseEvent event,
			Runnable runnable) {
		fire(event, MouseButton.PRIMARY, 2, runnable);
	}

	/**
	 * Fire left pressed.
	 *
	 * @param event
	 *            the event
	 * @param runnable
	 *            the runnable
	 */
	public static void fireLeftPressed(MouseEvent event, Runnable runnable) {
		fire(event, MouseButton.PRIMARY, 1, runnable);
	}

	/**
	 * Fire.
	 *
	 * @param event
	 *            the event
	 * @param clickedButton
	 *            the clicked button
	 * @param clickedCount
	 *            the clicked count
	 * @param runnable
	 *            the runnable
	 */
	public static void fire(MouseEvent event, MouseButton clickedButton,
			int clickedCount, Runnable runnable) {
		if (event.getButton().equals(clickedButton)
				&& event.getClickCount() == clickedCount) {
			runnable.run();
			event.consume();
		}
	}

}
