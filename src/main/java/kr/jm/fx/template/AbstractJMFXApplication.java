package kr.jm.fx.template;

import static java.util.stream.Collectors.toList;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import kr.jm.fx.JMFXComponentInterface;
import kr.jm.utils.helper.JMLog;
import kr.jm.utils.helper.JMOptional;
import kr.jm.utils.helper.JMResources;

/**
 * The Class AbstractJMFXApplication.
 *
 * @param <C>
 *            the generic type
 */
public abstract class AbstractJMFXApplication<C extends JMFXComponentInterface>
		extends Application {

	protected final org.slf4j.Logger log =
			org.slf4j.LoggerFactory.getLogger(getClass());

	protected C jmfxComponent;

	protected double width, height;

	protected List<String> cssPathStringList;

	private String title;

	/**
	 * Instantiates a new abstract JMFX application.
	 *
	 * @param jmfxComponent
	 *            the jmfx component
	 */
	public AbstractJMFXApplication(C jmfxComponent) {
		this(jmfxComponent, 800, 600);
	}

	/**
	 * Instantiates a new abstract JMFX application.
	 *
	 * @param jmfxComponent
	 *            the jmfx component
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 */
	public AbstractJMFXApplication(C jmfxComponent, double width,
			double height) {
		this(jmfxComponent, width, height, "css/JMFXApplication.css");
	}

	/**
	 * Instantiates a new abstract JMFX application.
	 *
	 * @param jmfxComponent
	 *            the jmfx component
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @param cssClasspathOrFilePaths
	 *            the css classpath or file paths
	 */
	public AbstractJMFXApplication(C jmfxComponent, double width, double height,
			String... cssClasspathOrFilePaths) {
		this.jmfxComponent = jmfxComponent;
		this.width = width;
		this.height = height;
		this.cssPathStringList = Arrays.asList(cssClasspathOrFilePaths).stream()
				.map(JMResources::getURL).map(URL::toExternalForm)
				.collect(toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			Node rootView = getRootView();
			Scene scene = new Scene(new AnchorPane(rootView), width, height);
			AnchorPane.setTopAnchor(rootView, 0.0);
			AnchorPane.setRightAnchor(rootView, 0.0);
			AnchorPane.setLeftAnchor(rootView, 0.0);
			AnchorPane.setBottomAnchor(rootView, 0.0);
			scene.getStylesheets().addAll(cssPathStringList);
			JMOptional.ifNotNull(title, primaryStage::setTitle);
			primaryStage.setScene(scene);
			primaryStage.sizeToScene();
			primaryStage.show();
		} catch (Exception e) {
			JMLog.errorForException(log, e, "start");
		}
	}

	protected Node getRootView() {
		return jmfxComponent.getView();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
