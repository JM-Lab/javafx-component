package kr.jm.fx.dialog.pane;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import kr.jm.fx.JMFXCompositeComponentInterface;
import kr.jm.fx.path.JMFXPath;
import kr.jm.fx.path.currentpathcombobox.CurrentPathComboBox;
import kr.jm.fx.path.treetableview.PathListTreeTableView;

/**
 * The Class ConfirmDialogPane.
 */
public class ConfirmDialogPane extends DialogPane
		implements JMFXCompositeComponentInterface {

	@FXML
	PathListTreeTableView pathListTreeTableView;
	@FXML
	Label infoLabel;
	@FXML
	Button changeButton;
	@FXML
	CurrentPathComboBox currentPathComboBox;

	/**
	 * Instantiates a new confirm dialog pane.
	 */
	public ConfirmDialogPane() {
		this(DefaultJMFXComponentI18nResourceBundle);
	}

	/**
	 * Instantiates a new confirm dialog pane.
	 *
	 * @param i18nResourceBundle
	 *            the i 18 n resource bundle
	 */
	public ConfirmDialogPane(ResourceBundle i18nResourceBundle) {
		initJMFXComponent(i18nResourceBundle);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.jm.fx.JMFXComponentInterface#initializeView()
	 */
	@Override
	public void initializeView() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.jm.fx.JMFXComponentInterface#initializeJMFXEvent()
	 */
	@Override
	public void initializeJMFXEvent() {
		changeButton.setOnAction(ae -> {
			DirectoryChooser directoryChooser = new DirectoryChooser();
			Optional.ofNullable(getSelectedDirectory()).map(JMFXPath::getPath)
					.map(Path::toFile)
					.ifPresent(directoryChooser::setInitialDirectory);
			Optional.ofNullable(directoryChooser.showDialog(null))
					.map(File::toPath).map(JMFXPath::getInstance)
					.ifPresent(this::setSelectedDirectory);
		});
	}

	/**
	 * Sets the current list.
	 *
	 * @param jmfxCurrentPathList
	 *            the jmfx current path list
	 * @return the confirm dialog pane
	 */
	public ConfirmDialogPane
			setCurrentList(List<JMFXPath> jmfxCurrentPathList) {
		pathListTreeTableView.setJMFXPathList(jmfxCurrentPathList);
		return this;
	}

	/**
	 * Sets the selected directory.
	 *
	 * @param selectedDirectory
	 *            the selected directory
	 * @return the confirm dialog pane
	 */
	public ConfirmDialogPane setSelectedDirectory(JMFXPath selectedDirectory) {
		currentPathComboBox.setValue(selectedDirectory);
		return this;
	}

	public JMFXPath getSelectedDirectory() {
		return currentPathComboBox.getValue();
	}

	public List<JMFXPath> getJMFXPathList() {
		return pathListTreeTableView.getJMFXPathList();
	}

}
