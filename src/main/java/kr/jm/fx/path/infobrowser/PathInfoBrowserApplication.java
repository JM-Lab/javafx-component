package kr.jm.fx.path.infobrowser;

import kr.jm.fx.template.AbstractJMFXApplication;

/**
 * The Class PathInfoBrowserApplication.
 */
public class PathInfoBrowserApplication
		extends AbstractJMFXApplication<PathInfoBrowser> {

	/**
	 * Instantiates a new path info browser application.
	 */
	public PathInfoBrowserApplication() {
		super(new PathInfoBrowser());
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
