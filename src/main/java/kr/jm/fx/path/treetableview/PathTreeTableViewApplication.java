package kr.jm.fx.path.treetableview;

import kr.jm.fx.path.JMFXPath;
import kr.jm.fx.template.AbstractJMFXApplication;

public class PathTreeTableViewApplication
		extends AbstractJMFXApplication<PathTreeTableView> {

	public PathTreeTableViewApplication() {
		super(new PathTreeTableView());
		jmfxComponent.setRootPathList(
				JMFXPath.getRootPath().getObservableChildrenList());
	}

	public static void main(String[] args) {
		launch(args);
	}

}
