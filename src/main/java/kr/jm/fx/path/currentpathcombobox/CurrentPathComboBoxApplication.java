package kr.jm.fx.path.currentpathcombobox;

import kr.jm.fx.template.AbstractJMFXApplication;

public class CurrentPathComboBoxApplication
		extends AbstractJMFXApplication<CurrentPathComboBox> {

	public CurrentPathComboBoxApplication() {
		super(new CurrentPathComboBox());
	}

	public static void main(String[] args) {
		launch(args);
	}

}
