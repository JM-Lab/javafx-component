package kr.jm.fx;

import java.util.ResourceBundle;

import javafx.scene.Node;

/**
 * The Interface JMFXComponentInterface.
 */
public interface JMFXComponentInterface extends JMFXInterface {

	static ResourceBundle DefaultJMFXComponentI18nResourceBundle =
			getI18nResourceBundle("JMFXComponent");

	static ResourceBundle getI18nResourceBundle(String i18nResourceBaseName) {
		return ResourceBundle.getBundle("i18n." + i18nResourceBaseName);
	}

	default void initJMFXComponent() {
		initJMFXComponent(null);
	}

	default void initJMFXComponent(ResourceBundle i18nResourceBundle) {
		loadFXML(i18nResourceBundle);
		initializeView();
		bindModelToView();
		initializeJMFXEvent();
	}

	default Node getView() {
		return (Node) this;
	}

	void bindModelToView();

	void initializeView();

	void initializeJMFXEvent();

}
