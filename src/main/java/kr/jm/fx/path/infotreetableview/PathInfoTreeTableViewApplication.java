package kr.jm.fx.path.infotreetableview;

import kr.jm.fx.template.AbstractJMFXApplication;

public class PathInfoTreeTableViewApplication
		extends AbstractJMFXApplication<PathInfoTreeTableView> {

	public PathInfoTreeTableViewApplication() {
		super(new PathInfoTreeTableView());
	}

	public static void main(String[] args) {
		launch(args);
	}

}
