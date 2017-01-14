package kr.jm.fx.path.infotableview;

import kr.jm.fx.template.AbstractJMFXApplication;

/**
 * The Class PathInfoTableViewApplication.
 */
public class PathInfoTableViewApplication
		extends AbstractJMFXApplication<PathInfoTableView> {

	/**
	 * Instantiates a new path info table view application.
	 */
	public PathInfoTableViewApplication() {
		super(new PathInfoTableView());
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
