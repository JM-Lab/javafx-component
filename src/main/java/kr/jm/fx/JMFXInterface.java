package kr.jm.fx;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import kr.jm.utils.enums.OS;
import kr.jm.utils.helper.JMString;

/**
 * The Interface JMFXInterface.
 */
public interface JMFXInterface {
	static final String FXML = "fxml";

	default void loadFXML(ResourceBundle i18nResourceBundle) {
		String fxmlFileName = getClass().getSimpleName() + JMString.DOT + FXML;
		ClassLoader componentClassLoader = JMFXInterface.class.getClassLoader();
		URL resourceURL = Optional
				.ofNullable(componentClassLoader.getResource(
						FXML + OS.getFileSeparator() + fxmlFileName))
				.orElseGet(() -> getClass().getResource(fxmlFileName));
		FXMLLoader fxmlLoader = Optional.ofNullable(i18nResourceBundle)
				.map(rb -> new FXMLLoader(resourceURL, rb))
				.orElseGet(() -> new FXMLLoader(resourceURL));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		// for composite custom component
		fxmlLoader.setClassLoader(componentClassLoader);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

}
