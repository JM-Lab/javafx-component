package kr.jm.fx.path.treetableview;

import kr.jm.fx.template.AbstractJMFXApplication;

public class PathListTreeTableViewApplication
		extends AbstractJMFXApplication<PathListTreeTableView> {

	public PathListTreeTableViewApplication() {
		super(new PathListTreeTableView());
	}

	public static void main(String[] args) {
		launch(args);
	}

}
