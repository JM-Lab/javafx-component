package kr.jm.fx.path.treetableview;

import kr.jm.fx.template.AbstractJMFXApplication;

/**
 * The Class PathListTreeTableViewApplication.
 */
public class PathListTreeTableViewApplication
		extends AbstractJMFXApplication<PathListTreeTableView> {

	/**
	 * Instantiates a new path list tree table view application.
	 */
	public PathListTreeTableViewApplication() {
		super(new PathListTreeTableView());
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
