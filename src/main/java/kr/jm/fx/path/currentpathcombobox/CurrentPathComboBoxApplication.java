package kr.jm.fx.path.currentpathcombobox;

import kr.jm.fx.template.AbstractJMFXApplication;

/**
 * The Class CurrentPathComboBoxApplication.
 */
public class CurrentPathComboBoxApplication
		extends AbstractJMFXApplication<CurrentPathComboBox> {

	/**
	 * Instantiates a new current path combo box application.
	 */
	public CurrentPathComboBoxApplication() {
		super(new CurrentPathComboBox());
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
