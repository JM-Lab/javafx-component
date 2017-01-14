package kr.jm.fx;

import java.util.ResourceBundle;

/**
 * The Interface JMFXCompositeComponentInterface.
 */
public interface JMFXCompositeComponentInterface
		extends JMFXComponentInterface {

	@Override
	default void initJMFXComponent(ResourceBundle i18nResourceBundle) {
		loadFXML(i18nResourceBundle);
		initializeView();
		initializeJMFXEvent();
	}

	@Override
	default void bindModelToView() {
		throw new UnsupportedOperationException();
	}

}
