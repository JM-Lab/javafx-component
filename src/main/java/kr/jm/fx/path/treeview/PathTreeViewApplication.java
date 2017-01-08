package kr.jm.fx.path.treeview;

import kr.jm.fx.template.AbstractJMFXApplication;

public class PathTreeViewApplication
		extends AbstractJMFXApplication<PathTreeView> {

	public PathTreeViewApplication() {
		super(new PathTreeView());
	}

	public static void main(String[] args) {
		launch(args);
	}

}
