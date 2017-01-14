package kr.jm.fx.path.infocomplexview;

import kr.jm.fx.template.AbstractJMFXApplication;

/**
 * The Class PathInfoComplexViewApplication.
 */
public class PathInfoComplexViewApplication
		extends AbstractJMFXApplication<PathInfoComplexView> {

	/**
	 * Instantiates a new path info complex view application.
	 */
	public PathInfoComplexViewApplication() {
		super(new PathInfoComplexView());
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
