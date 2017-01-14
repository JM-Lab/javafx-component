package kr.jm.fx.path.treeview;

import kr.jm.fx.path.JMFXPath;
import kr.jm.fx.template.AbstractJMFXApplication;

/**
 * The Class PathTreeViewApplication.
 */
public class PathTreeViewApplication
		extends AbstractJMFXApplication<PathTreeView> {

	/**
	 * Instantiates a new path tree view application.
	 */
	public PathTreeViewApplication() {
		super(new PathTreeView());
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
