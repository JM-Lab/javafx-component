package kr.jm.fx.path.infotableview;

import kr.jm.fx.template.AbstractJMFXApplication;

public class PathInfoTableViewApplication
		extends AbstractJMFXApplication<PathInfoTableView> {

	public PathInfoTableViewApplication() {
		super(new PathInfoTableView());
	}

	public static void main(String[] args) {
		launch(args);
	}

}
