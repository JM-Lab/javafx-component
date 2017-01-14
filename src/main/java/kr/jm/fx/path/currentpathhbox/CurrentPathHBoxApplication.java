package kr.jm.fx.path.currentpathhbox;

import kr.jm.fx.template.AbstractJMFXApplication;

/**
 * The Class CurrentPathHBoxApplication.
 */
public class CurrentPathHBoxApplication
		extends AbstractJMFXApplication<CurrentPathHBox> {

	/**
	 * Instantiates a new current path H box application.
	 */
	public CurrentPathHBoxApplication() {
		super(new CurrentPathHBox());
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
