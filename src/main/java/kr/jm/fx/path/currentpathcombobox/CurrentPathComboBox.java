package kr.jm.fx.path.currentpathcombobox;

import static java.util.stream.Collectors.toList;
import static kr.jm.utils.helper.JMLambda.consumeByBoolean;
import static kr.jm.utils.helper.JMLambda.runByBoolean;
import static kr.jm.utils.helper.JMLambda.runIfTrue;
import static kr.jm.utils.helper.JMPredicate.peek;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;
import kr.jm.fx.JMFXComponentInterface;
import kr.jm.fx.helper.JMFXKeyEvent;
import kr.jm.fx.helper.JMFXValueEvent;
import kr.jm.fx.path.JMFXPath;
import kr.jm.utils.helper.JMString;

/**
 * The Class CurrentPathComboBox.
 */
public class CurrentPathComboBox extends ComboBox<JMFXPath>
		implements JMFXComponentInterface {

	protected CurrentPathComboBoxModel currentPathComboBoxModel;
	protected JMFXValueEvent<JMFXPath> currentPathChangeEvent =
			new JMFXValueEvent<>();

	public JMFXValueEvent<JMFXPath> getCurrentPathChangeEvent() {
		return currentPathChangeEvent;
	}

	@FXML
	void onActionOfCurrentPathComboBox(ActionEvent event) {
		setLastPositionCaret();
	}

	@FXML
	void onKeyReleasedOfCurrentPathComboBox(KeyEvent event) {
		JMFXKeyEvent.fire(event, KeyCode.DOWN, this::show);
	}

	/**
	 * Instantiates a new current path combo box.
	 */
	public CurrentPathComboBox() {
		this(DefaultJMFXComponentI18nResourceBundle);
	}

	/**
	 * Instantiates a new current path combo box.
	 *
	 * @param i18nResourceBundle
	 *            the i 18 n resource bundle
	 */
	public CurrentPathComboBox(ResourceBundle i18nResourceBundle) {
		initJMFXComponent(i18nResourceBundle);
	}

	/**
	 * Change combo box value.
	 *
	 * @param jmfxPath
	 *            the jmfx path
	 */
	public void changeComboBoxValue(JMFXPath jmfxPath) {
		setValue(jmfxPath);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.jm.fx.JMFXComponentInterface#initializeView()
	 */
	@Override
	public void initializeView() {
		StringConverter<JMFXPath> stringConverter =
				new StringConverter<JMFXPath>() {
					@Override
					public String toString(JMFXPath jmfxPath) {
						return Optional.ofNullable(jmfxPath)
								.map(JMFXPath::getAbsolutePathName)
								.orElse(JMString.EMPTY);
					}

					@Override
					public JMFXPath fromString(String promptString) {
						return JMFXPath.getInstance(promptString);
					}
				};
		setCellFactory(IconListCell::new);
		setConverter(stringConverter);
		valueProperty().addListener(
				(observable, oldValue, newValue) -> runIfTrue(!isShowing(),
						() -> currentPathChangeEvent.changeValue(newValue)));
		showingProperty().addListener(
				(observable, oldValue, newValue) -> runByBoolean(newValue,
						() -> Optional.ofNullable(getEditor().getText())
								.ifPresent(this::buildDirectoryListInPopup),
						() -> currentPathChangeEvent.changeValue(getValue())));
		setMaxWidth(Double.MAX_VALUE);
	}

	private void setLastPositionCaret() {
		TextField editor = getEditor();
		Optional.ofNullable(editor.getText()).map(String::length)
				.filter(peek(editor::selectPositionCaret))
				.ifPresent(editor::positionCaret);
	}

	private void buildDirectoryListInPopup(String editerText) {
		JMFXPath currentPath = JMFXPath.getInstance(editerText);
		List<JMFXPath> childernList =
				currentPath.getChildrenDirctoryJMFXStream().collect(toList());
		if (!JMFXPath.getRootPath().equals(currentPath))
			childernList.add(0, currentPath);
		runIfTrue(!currentPath.isDirectory(),
				() -> childernList.addAll(buildChildOfParentComboBoxList(
						currentPath, currentPath.toString())));
		currentPathComboBoxModel.changeAllRows(childernList);
		getSelectionModel().selectFirst();
	}

	private List<JMFXPath> buildChildOfParentComboBoxList(
			JMFXPath currentPromptPath, String containsString) {
		return currentPromptPath.getParent()
				.map(JMFXPath::getChildrenDirctoryJMFXStream)
				.map(childStream -> childStream.filter(
						child -> child.toString().contains(containsString))
						.collect(toList()))
				.orElseGet(Collections::emptyList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.jm.fx.JMFXComponentInterface#initializeJMFXEvent()
	 */
	@Override
	public void initializeJMFXEvent() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.jm.fx.JMFXComponentInterface#bindModelToView()
	 */
	@Override
	public void bindModelToView() {
		this.currentPathComboBoxModel = new CurrentPathComboBoxModel();
		setItems(currentPathComboBoxModel.getModel());
	}

	private class IconListCell extends ListCell<JMFXPath> {

		private ListView<JMFXPath> listView;

		public IconListCell(ListView<JMFXPath> listView) {
			this.listView = listView;
		}

		@Override
		protected void updateItem(JMFXPath item, boolean empty) {
			super.updateItem(item, empty);
			consumeByBoolean(item == null || empty, item, this::clearCell,
					this::renderCell);
		}

		private void clearCell(JMFXPath item) {
			setText(null);
			setGraphic(null);
		}

		private void renderCell(JMFXPath item) {
			setText(item.toString());
			setGraphic(item.getIcon());
			listView.refresh();
		}
	}

}
