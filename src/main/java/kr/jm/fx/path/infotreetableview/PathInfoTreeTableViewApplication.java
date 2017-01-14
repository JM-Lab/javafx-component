package kr.jm.fx.path.infotreetableview;

import kr.jm.fx.template.AbstractJMFXApplication;

/**
 * The Class PathInfoTreeTableViewApplication.
 */
public class PathInfoTreeTableViewApplication
		extends AbstractJMFXApplication<PathInfoTreeTableView> {

	/**
	 * Instantiates a new path info tree table view application.
	 */
	public PathInfoTreeTableViewApplication() {
		super(new PathInfoTreeTableView());
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
