package kr.jm.fx.path.currentpathhbox;

import static kr.jm.utils.helper.JMLambda.runIfTrue;
import static kr.jm.utils.helper.JMPredicate.getEquals;
import static kr.jm.utils.helper.JMPredicate.peekToRun;

import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import kr.jm.fx.JMFXCompositeComponentInterface;
import kr.jm.fx.helper.JMFXValueEvent;
import kr.jm.fx.path.JMFXPath;
import kr.jm.fx.path.currentpathcombobox.CurrentPathComboBox;
import kr.jm.utils.collections.JMLimitedList;

/**
 * The Class CurrentPathHBox.
 */
public class CurrentPathHBox extends HBox
		implements JMFXCompositeComponentInterface {

	@FXML
	protected Button backButton;
	@FXML
	protected Tooltip backTooltip;
	@FXML
	protected Button forwardButton;
	@FXML
	protected Tooltip forwardTooltip;
	@FXML
	protected Button goToParentButton;
	@FXML
	protected Tooltip goToParentTooltip;
	@FXML
	protected CurrentPathComboBox currentPathComboBox;

	public JMFXValueEvent<JMFXPath> getCurrentPathChangeEvent() {
		return currentPathComboBox.getCurrentPathChangeEvent();
	}

	@FXML
	protected void onActionOfBackButton(ActionEvent event) {
		limitedHistoryList.getPrevious().ifPresent(this::changeCurrentPath);
	}

	@FXML
	protected void onActionOfForwardButton(ActionEvent event) {
		limitedHistoryList.getNext().ifPresent(this::changeCurrentPath);
	}

	@FXML
	protected void onActionOfUpButton(ActionEvent event) {
		Optional.ofNullable(currentPathComboBox.getValue())
				.flatMap(JMFXPath::getParent)
				.ifPresent(this::changeCurrentPath);
	}

	protected JMLimitedList<JMFXPath> limitedHistoryList;

	/**
	 * Instantiates a new current path H box.
	 */
	public CurrentPathHBox() {
		this(DefaultJMFXComponentI18nResourceBundle);
	}

	/**
	 * Instantiates a new current path H box.
	 *
	 * @param i18nResourceBundle
	 *            the i 18 n resource bundle
	 */
	public CurrentPathHBox(ResourceBundle i18nResourceBundle) {
		initJMFXComponent(i18nResourceBundle);
	}

	public void setHistoryLimits(int size) {
		synchronized (limitedHistoryList) {
			JMLimitedList<JMFXPath> old = limitedHistoryList;
			limitedHistoryList = new JMLimitedList<>(size);
			limitedHistoryList.addAll(old);
		}
	}

	/**
	 * Change current path.
	 *
	 * @param jmfxPath
	 *            the jmfx path
	 */
	public void changeCurrentPath(JMFXPath jmfxPath) {
		manageStatusOfBottons(jmfxPath);
		if (limitedHistoryList.size() == 0 || limitedHistoryList.getCurrent()
				.filter(getEquals(jmfxPath).negate()).isPresent()) {
			limitedHistoryList.add(jmfxPath);
			runIfTrue(
					limitedHistoryList.size()
							- limitedHistoryList.getCurrentIndex() != 1,
					this::leaveTheLastHistory);
		}
		currentPathComboBox.changeComboBoxValue(jmfxPath);
	}

	private void leaveTheLastHistory() {
		limitedHistoryList.getCurrent()
				.filter(peekToRun(limitedHistoryList::clear))
				.ifPresent(limitedHistoryList::add);
	}

	private void manageStatusOfBottons(JMFXPath jmfxPath) {
		backButton.setDisable(limitedHistoryList.getCurrentIndex() == 0);
		forwardButton.setDisable(limitedHistoryList.size()
				- limitedHistoryList.getCurrentIndex() == 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.jm.fx.JMFXComponentInterface#initializeView()
	 */
	@Override
	public void initializeView() {
		this.limitedHistoryList = new JMLimitedList<>(10);
		forwardButton.setDisable(true);
		backButton.setDisable(true);
		setHgrow(currentPathComboBox, Priority.ALWAYS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.jm.fx.JMFXComponentInterface#initializeJMFXEvent()
	 */
	@Override
	public void initializeJMFXEvent() {
		getCurrentPathChangeEvent().addListener(this::changeCurrentPath);
	}

	public JMFXPath getCurrentPath() {
		return currentPathComboBox.getValue();
	}

}
