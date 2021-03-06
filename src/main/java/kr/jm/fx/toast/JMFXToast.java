package kr.jm.fx.toast;

import java.util.List;
import java.util.Optional;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import kr.jm.fx.helper.JMFXIconFactory;
import kr.jm.fx.path.JMFXPathString;

/**
 * The Class JMFXToast.
 */
public class JMFXToast {
	private static Stage mainStage;
	private static JMFXToast jmfxToast;
	private List<Node> stackedNodeList;
	private String labelStyle;
	private double endOpaque = 0.0;
	private double startOpaque = 0.97;
	private long defaultDuration = 3;
	private Pos defaultPos = Pos.CENTER;

	public double getEndOpaque() {
		return endOpaque;
	}

	public void setEndOpaque(double endOpaque) {
		this.endOpaque = endOpaque;
	}

	public double getStartOpaque() {
		return startOpaque;
	}

	public void setStartOpaque(double startOpaque) {
		this.startOpaque = startOpaque;
	}

	public long getDefaultDuration() {
		return defaultDuration;
	}

	public void setDefaultDuration(long defaultDuration) {
		this.defaultDuration = defaultDuration;
	}

	public Pos getDefaultPos() {
		return defaultPos;
	}

	public void setDefaultPos(Pos defaultPos) {
		this.defaultPos = defaultPos;
	}

	public String getLabelStyle() {
		return labelStyle;
	}

	public void setLabelStyle(String labelStyle) {
		this.labelStyle = labelStyle;
	}

	private JMFXToast(Stage rootStage) {
		Scene scene = rootStage.getScene();
		StackPane toastRoot = new StackPane(scene.getRoot());
		scene.setRoot(toastRoot);
		this.stackedNodeList = toastRoot.getChildren();
		this.setLabelStyle(
				"-fx-background-color: gray; -fx-text-fill: white; -fx-border-radius: 3 3 3 3; -fx-background-radius: 3 3 3 3;");
		rootStage.show();
	}

	/**
	 * Gets the single instance of JMFXToast.
	 *
	 * @param node
	 *            the node
	 * @return single instance of JMFXToast
	 */
	public static JMFXToast getInstance(Node node) {
		return Optional.ofNullable(node).map(Node::getScene)
				.map(Scene::getWindow).map(JMFXToast::getInstance).get();
	}

	/**
	 * Gets the single instance of JMFXToast.
	 *
	 * @param window
	 *            the window
	 * @return single instance of JMFXToast
	 */
	synchronized public static JMFXToast getInstance(Window window) {
		return jmfxToast == null
				? Optional.ofNullable(window).filter(w -> w != mainStage)
						.map(w -> (Stage) w).map(JMFXToast::new)
						.map(jmfxToast -> JMFXToast.jmfxToast = jmfxToast)
						.orElseThrow(() -> new RuntimeException(
								"Can't Get Stage From Window !!! = " + window))
				: jmfxToast;
	}

	/**
	 * Show.
	 *
	 * @param node
	 *            the node
	 */
	public void show(Node node) {
		show(node, getDefaultDuration());
	}

	/**
	 * Show.
	 *
	 * @param node
	 *            the node
	 * @param durationInSec
	 *            the duration in sec
	 */
	public void show(Node node, long durationInSec) {
		stackedNodeList.add(node);
		FadeTransition fadeTransition =
				new FadeTransition(Duration.seconds(durationInSec), node);
		fadeTransition.setFromValue(startOpaque);
		fadeTransition.setToValue(endOpaque);
		fadeTransition.playFromStart();
		fadeTransition.setOnFinished(value -> stackedNodeList.remove(node));
	}

	/**
	 * Show.
	 *
	 * @param node
	 *            the node
	 * @param pos
	 *            the pos
	 */
	public void show(Node node, Pos pos) {
		show(node, pos, getDefaultDuration());
	}

	/**
	 * Show.
	 *
	 * @param node
	 *            the node
	 * @param pos
	 *            the pos
	 * @param durationInSec
	 *            the duration in sec
	 */
	public void show(Node node, Pos pos, long durationInSec) {
		BorderPane borderPane = new BorderPane(node);
		borderPane.setMouseTransparent(true);
		BorderPane.setAlignment(node, pos);
		show(borderPane, durationInSec);
	}

	/**
	 * Show.
	 *
	 * @param imageNameInClasspass
	 *            the image name in classpass
	 * @param text
	 *            the text
	 * @param pos
	 *            the pos
	 * @param durationInSec
	 *            the duration in sec
	 */
	public void show(String imageNameInClasspass, String text, Pos pos,
			long durationInSec) {
		show(JMFXIconFactory.buildImageView(imageNameInClasspass), text, pos,
				durationInSec);
	}

	/**
	 * Show.
	 *
	 * @param imageNameInClasspass
	 *            the image name in classpass
	 * @param text
	 *            the text
	 * @param durationInSec
	 *            the duration in sec
	 */
	public void show(String imageNameInClasspass, String text,
			long durationInSec) {
		show(imageNameInClasspass, text, defaultPos, durationInSec);
	}

	/**
	 * Show.
	 *
	 * @param imageNameInClasspass
	 *            the image name in classpass
	 * @param text
	 *            the text
	 * @param pos
	 *            the pos
	 */
	public void show(String imageNameInClasspass, String text, Pos pos) {
		show(imageNameInClasspass, text, pos, getDefaultDuration());
	}

	/**
	 * Show.
	 *
	 * @param graphic
	 *            the graphic
	 * @param text
	 *            the text
	 * @param pos
	 *            the pos
	 * @param durationInSec
	 *            the duration in sec
	 */
	public void show(Node graphic, String text, Pos pos, long durationInSec) {
		show(buildToastLabel(graphic, text), pos, durationInSec);
	}

	/**
	 * Show.
	 *
	 * @param graphic
	 *            the graphic
	 * @param text
	 *            the text
	 * @param durationInSec
	 *            the duration in sec
	 */
	public void show(Node graphic, String text, long durationInSec) {
		show(graphic, text, defaultPos, durationInSec);
	}

	/**
	 * Show.
	 *
	 * @param graphic
	 *            the graphic
	 * @param text
	 *            the text
	 * @param pos
	 *            the pos
	 */
	public void show(Node graphic, String text, Pos pos) {
		show(graphic, text, pos, getDefaultDuration());
	}

	/**
	 * Show.
	 *
	 * @param text
	 *            the text
	 * @param pos
	 *            the pos
	 * @param durationInSec
	 *            the duration in sec
	 */
	public void show(String text, Pos pos, long durationInSec) {
		show(buildToastLabel(null, text), pos, durationInSec);
	}

	private Label buildToastLabel(Node graphic, String text) {
		Label label = new Label(text, graphic);
		label.setStyle(labelStyle);
		label.autosize();
		return label;
	}

	/**
	 * Show.
	 *
	 * @param text
	 *            the text
	 * @param pos
	 *            the pos
	 */
	public void show(String text, Pos pos) {
		show(text, pos, getDefaultDuration());
	}

	/**
	 * Show.
	 *
	 * @param text
	 *            the text
	 * @param durationInSec
	 *            the duration in sec
	 */
	public void show(String text, long durationInSec) {
		show(text, defaultPos, durationInSec);
	}

	/**
	 * Show.
	 *
	 * @param text
	 *            the text
	 */
	public void show(String text) {
		show(text, getDefaultDuration());
	}

	/**
	 * Show error.
	 *
	 * @param text
	 *            the text
	 * @param pos
	 *            the pos
	 */
	public void showError(String text, Pos pos) {
		show(JMFXPathString.ERROR + JMFXPathString.COLON_SPACE + text, pos,
				getDefaultDuration());
	}

	/**
	 * Show error.
	 *
	 * @param text
	 *            the text
	 */
	public void showError(String text) {
		showError(text, defaultPos);
	}

	/**
	 * Show or error.
	 *
	 * @param succeed
	 *            the succeed
	 * @param text
	 *            the text
	 */
	public void showOrError(Boolean succeed, String text) {
		if (succeed)
			show(text);
		else
			showError(text);
	}

}
