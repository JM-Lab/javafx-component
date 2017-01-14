package kr.jm.fx.path.treetableview;

import kr.jm.fx.path.JMFXPath;
import kr.jm.fx.template.AbstractJMFXApplication;

/**
 * The Class PathTreeTableViewApplication.
 */
public class PathTreeTableViewApplication
		extends AbstractJMFXApplication<PathTreeTableView> {

	/**
	 * Instantiates a new path tree table view application.
	 */
	public PathTreeTableViewApplication() {
		super(new PathTreeTableView());
		jmfxComponent.setRootPathList(
				JMFXPath.getRootPath().getObservableChildrenList());
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
