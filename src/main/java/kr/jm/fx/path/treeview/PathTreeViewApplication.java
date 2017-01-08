package kr.jm.fx.path.treeview;

import kr.jm.fx.path.JMFXPath;
import kr.jm.fx.template.AbstractJMFXApplication;

public class PathTreeViewApplication
		extends AbstractJMFXApplication<PathTreeView> {

	public PathTreeViewApplication() {
		super(new PathTreeView());
		jmfxComponent.setRootPathList(
				JMFXPath.getRootPath().getObservableChildrenList());
	}

	public static void main(String[] args) {
		launch(args);
	}

}
