package kr.jm.fx.helper;

import java.util.Optional;
import java.util.function.Consumer;

import javafx.beans.property.SimpleObjectProperty;

/**
 * The Class JMFXValueEvent.
 *
 * @param <T>
 *            the generic type
 */
public class JMFXValueEvent<T> {

	private SimpleObjectProperty<T> eventObject = new SimpleObjectProperty<>();

	public T getLastValue() {
		return eventObject.getValue();
	}

	/**
	 * Change value.
	 *
	 * @param v
	 *            the v
	 */
	public void changeValue(T v) {
		Optional.ofNullable(v).ifPresent(eventObject::set);
	}

	/**
	 * Adds the listener.
	 *
	 * @param listener
	 *            the listener
	 */
	public void addListener(Consumer<T> listener) {
		eventObject.addListener((op, o, n) -> listener.accept(n));
	}

}
