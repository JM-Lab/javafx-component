package kr.jm.fx.path.infocomplexview;

import kr.jm.fx.template.AbstractJMFXApplication;

public class PathInfoComplexViewApplication
		extends AbstractJMFXApplication<PathInfoComplexView> {

	public PathInfoComplexViewApplication() {
		super(new PathInfoComplexView());
	}

	public static void main(String[] args) {
		launch(args);
	}

}
