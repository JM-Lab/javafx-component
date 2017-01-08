package kr.jm.fx.dialog;

import static kr.jm.utils.helper.JMLambda.runIfTrue;
import static kr.jm.utils.helper.JMLambda.supplierIfTrue;

import java.util.Optional;
import java.util.function.Function;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import kr.jm.fx.dialog.pane.ProgressiveDialogPane;
import kr.jm.utils.JMProgressiveManager;
import kr.jm.utils.helper.JMOptional;

public class JMFXDialog {

	public static <R> Dialog<R> buildDialogWithOkResult(String title,
			String headerText, Node content, R result) {
		return buildDialog(title, headerText, content,
				buttonType -> buttonType.equals(ButtonType.OK) ? result : null,
				ButtonType.OK);
	}

	public static <R> Dialog<R> buildDialog(String title, String headerText,
			Node content, Function<ButtonType, R> resultFunction,
			ButtonType... optionalButtonTypes) {
		DialogPane dialogPane = new DialogPane();
		dialogPane.setContent(content);
		return buildDialog(title, headerText, dialogPane, resultFunction,
				optionalButtonTypes);
	}

	public static <R> Dialog<R> buildDialog(String title, String headerText,
			DialogPane dialogPane, Function<ButtonType, R> resultFunction,
			ButtonType... optionalButtonTypes) {
		Dialog<R> dialog = new Dialog<>();
		dialog.setDialogPane(dialogPane);
		JMOptional.getOptional(title).ifPresent(dialog::setTitle);
		JMOptional.getOptional(headerText).ifPresent(dialog::setHeaderText);
		dialog.setResizable(true);
		ObservableList<ButtonType> dialogButtonTypeList =
				dialogPane.getButtonTypes();
		JMOptional.getOptional(optionalButtonTypes)
				.ifPresent(dialogButtonTypeList::addAll);
		Optional.ofNullable(resultFunction).ifPresent(f -> dialog
				.setResultConverter(buttonType -> f.apply(buttonType)));
		dialogButtonTypeList.add(ButtonType.CANCEL);
		return dialog;
	}

	public static Dialog<ProgressiveDialogPane> buildProgressiveDialog(
			JMProgressiveManager<?, ?> progressiveManager, String title,
			String headerText) {
		Dialog<ProgressiveDialogPane> progressiveDialog = buildDialog(title,
				headerText,
				new ProgressiveDialogPane()
						.setProgressiveManager(progressiveManager),
				buttonType -> supplierIfTrue(
						buttonType.equals(ButtonType.CANCEL),
						() -> cancelProgressiveDialog(progressiveManager))
								.orElse(null));
		progressiveManager.registerCompletedConsumer(
				o -> Platform.runLater(() -> completeProgressiveView(
						progressiveDialog.getDialogPane().getButtonTypes())));
		return progressiveDialog;
	}

	private static void
			completeProgressiveView(ObservableList<ButtonType> buttonTypes) {
		buttonTypes.remove(ButtonType.CANCEL);
		runIfTrue(!buttonTypes.contains(ButtonType.CLOSE),
				() -> buttonTypes.add(ButtonType.CLOSE));
	}

	private static ProgressiveDialogPane cancelProgressiveDialog(
			JMProgressiveManager<?, ?> progressiveManager) {
		progressiveManager.stopAsync();
		return null;
	}

}
