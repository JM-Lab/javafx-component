package kr.jm.fx.path.tableview;

import kr.jm.fx.template.AbstractJMFXApplication;

/**
 * The Class PathTableViewApplication.
 */
public class PathTableViewApplication
		extends AbstractJMFXApplication<PathTableView> {

	/**
	 * Instantiates a new path table view application.
	 */
	public PathTableViewApplication() {
		super(new PathTableView());
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
