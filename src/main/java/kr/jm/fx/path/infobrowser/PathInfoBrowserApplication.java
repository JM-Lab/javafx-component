package kr.jm.fx.path.infobrowser;

import kr.jm.fx.template.AbstractJMFXApplication;

public class PathInfoBrowserApplication
		extends AbstractJMFXApplication<PathInfoBrowser> {

	public PathInfoBrowserApplication() {
		super(new PathInfoBrowser());
	}

	public static void main(String[] args) {
		launch(args);
	}

}
