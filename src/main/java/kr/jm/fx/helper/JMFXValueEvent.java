package kr.jm.fx.helper;

import java.util.Optional;
import java.util.function.Consumer;

import javafx.beans.property.SimpleObjectProperty;

public class JMFXValueEvent<T> {

	private SimpleObjectProperty<T> eventObject = new SimpleObjectProperty<>();

	public T getLastValue() {
		return eventObject.getValue();
	}

	public void changeValue(T v) {
		Optional.ofNullable(v).ifPresent(eventObject::set);
	}

	public void addListener(Consumer<T> listener) {
		eventObject.addListener((op, o, n) -> listener.accept(n));
	}

}
