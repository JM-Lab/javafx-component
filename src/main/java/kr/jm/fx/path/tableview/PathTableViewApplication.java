package kr.jm.fx.path.tableview;

import kr.jm.fx.template.AbstractJMFXApplication;

public class PathTableViewApplication
		extends AbstractJMFXApplication<PathTableView> {

	public PathTableViewApplication() {
		super(new PathTableView());
	}

	public static void main(String[] args) {
		launch(args);
	}

}
