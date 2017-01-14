package kr.jm.fx.dialog.pane;

import static kr.jm.utils.helper.JMLambda.runIfTrue;

import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import kr.jm.fx.JMFXCompositeComponentInterface;
import kr.jm.fx.path.JMFXPathString;
import kr.jm.utils.JMProgressiveManager;
import kr.jm.utils.helper.JMString;

/**
 * The Class ProgressiveDialogPane.
 */
public class ProgressiveDialogPane extends DialogPane
		implements JMFXCompositeComponentInterface {

	private static final String FAILURE = "[" + JMFXPathString.FAILURE + "] ";
	private static final String COMPLETE = "[" + JMFXPathString.COMPLETE + "] ";
	private static final String TARGET_PATH_COLON_SPACE =
			JMFXPathString.TARGET_NAME + JMFXPathString.COLON_SPACE;
	@FXML
	ProgressBar progressBar;
	@FXML
	ProgressIndicator infinityIndicator;
	@FXML
	Label currentInfoLabel;
	@FXML
	Label currentTotalLabel;
	@FXML
	Label percentLabel;

	/**
	 * Instantiates a new progressive dialog pane.
	 */
	public ProgressiveDialogPane() {
		this(DefaultJMFXComponentI18nResourceBundle);
	}

	/**
	 * Instantiates a new progressive dialog pane.
	 *
	 * @param i18nResourceBundle
	 *            the i 18 n resource bundle
	 */
	public ProgressiveDialogPane(ResourceBundle i18nResourceBundle) {
		initJMFXComponent(i18nResourceBundle);
	}

	/**
	 * Sets the progressive manager.
	 *
	 * @param progressiveManager
	 *            the progressive manager
	 * @return the progressive dialog pane
	 */
	public ProgressiveDialogPane setProgressiveManager(
			JMProgressiveManager<?, ?> progressiveManager) {
		return setJMProgressiveManager(progressiveManager, true);
	}

	/**
	 * Sets the JM progressive manager.
	 *
	 * @param progressiveManager
	 *            the progressive manager
	 * @param hasDefaultExpandableContent
	 *            the has default expandable content
	 * @return the progressive dialog pane
	 */
	public ProgressiveDialogPane setJMProgressiveManager(
			JMProgressiveManager<?, ?> progressiveManager,
			boolean hasDefaultExpandableContent) {
		String totalCount = String.valueOf(progressiveManager.getTotalCount());
		changeCurrentTotalLabel(progressiveManager.getProgressiveCount(),
				totalCount);
		changePercentLabel(progressiveManager.getProgressivePercent());
		changeProgress(progressiveManager.getProgressivePercent());
		Optional.ofNullable(progressiveManager.getCurrentTarget())
				.ifPresent(this::changeCurrentInfoLabel);
		progressiveManager.registerCountChangeListener(
				count -> changeCurrentTotalLabel(count, totalCount));
		progressiveManager
				.registerPercentChangeListener(this::changePercentLabel);
		progressiveManager.registerPercentChangeListener(this::changeProgress);
		progressiveManager.registerTargetChangeListener(
				target -> changeCurrentInfoLabel(target));
		progressiveManager.registerCompletedConsumer(pm -> Platform
				.runLater(() -> infinityIndicator.setProgress(1.0f)));
		runIfTrue(hasDefaultExpandableContent,
				() -> addDefaultExpandableContent(progressiveManager));
		return this;
	}

	private void addDefaultExpandableContent(
			JMProgressiveManager<?, ?> progressiveManager) {
		TextArea textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setPrefWidth(this.getWidth() - 50d);
		textArea.textProperty()
				.addListener((ob, o, n) -> runIfTrue(isExpanded(),
						() -> moveTextAreaScrollToBottomLeft(textArea)));
		setExpandableContent(textArea);
		expandedProperty().addListener(
				(ob, o, n) -> moveTextAreaScrollToBottomLeft(textArea));
		progressiveManager.registerLastResultChangeListener(
				opt -> opt.map(t -> COMPLETE + t)
						.ifPresent(line -> appendLine(textArea, line)));
		progressiveManager.registerLastFailureChangeListener(
				e -> Platform.runLater(() -> makeEffectOnFailure(
						progressiveManager.getCurrentTarget().toString(),
						textArea, e)));
		progressiveManager.registerCompletedConsumer(
				pm -> moveTextAreaScrollToBottomLeft(textArea));
	}

	private void moveTextAreaScrollToBottomLeft(TextArea textArea) {
		Platform.runLater(() -> {
			textArea.setScrollLeft(Double.MIN_VALUE);
			textArea.setScrollTop(Double.MAX_VALUE);
		});
	}

	private void appendLine(TextArea textArea, String line) {
		textArea.appendText(line + JMString.LINE_SEPERATOR);
	}

	private void makeEffectOnFailure(String TargetString, TextArea textArea,
			Exception e) {
		setExpanded(true);
		appendLine(textArea,
				buildAppendingFailureMessage(e, FAILURE + TargetString));
	}

	private String buildAppendingFailureMessage(Exception e,
			String appendingProgressiveMessage) {
		return appendingProgressiveMessage + JMFXPathString.COLON_SPACE
				+ e.toString();
	}

	private void changeCurrentInfoLabel(Object target) {
		Platform.runLater(() -> currentInfoLabel
				.setText(TARGET_PATH_COLON_SPACE + target.toString()));
	}

	private void changeProgress(Number percent) {
		progressBar.setProgress(percent.doubleValue() / 100);
	}

	private void changePercentLabel(Number percentNum) {
		Platform.runLater(() -> percentLabel.setText(percentNum + " %"));
	}

	private void changeCurrentTotalLabel(Number count, String totalCount) {
		Platform.runLater(
				() -> currentTotalLabel.setText(count + " / " + totalCount));
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

	}

}
