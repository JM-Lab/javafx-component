package kr.jm.fx.helper;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import kr.jm.utils.JMIconFactory;
import kr.jm.utils.datastructure.JMMap;
import kr.jm.utils.helper.JMOptional;
import kr.jm.utils.helper.JMPath;

/**
 * A factory for creating JMFXIcon objects.
 */
public class JMFXIconFactory {

	private JMIconFactory jmIconFactory;
	private String unknownFileName;
	private Map<String, Image> fxImageCache;

	/**
	 * Instantiates a new JMFX icon factory.
	 */
	public JMFXIconFactory() {
		this.jmIconFactory = new JMIconFactory();
		this.unknownFileName = "?";
		this.fxImageCache = new ConcurrentHashMap<>();
		this.fxImageCache.put(unknownFileName,
				buildFxImage(jmIconFactory.buildBufferedImageOfIconInOS(
						JMPath.getPath(unknownFileName))));
	}

	private Optional<String> getSpecialPathAsOpt(Path path) {
		return JMOptional
				.getNullableAndFilteredOptional(path,
						JMPath.DirectoryFilter.or(JMPath.SymbolicLinkFilter)
								.or(JMPath.HiddenFilter))
				.map(Path::toAbsolutePath).map(Path::toString);
	}

	/**
	 * Gets the fx image of icon in OS.
	 *
	 * @param path
	 *            the path
	 * @return the fx image of icon in OS
	 */
	public Image getFxImageOfIconInOS(Path path) {
		return getSpecialPathAsOpt(path).map(getCachedFxImageFunction(path))
				.orElseGet(() -> buildCachedFxImageOfFileIconInOS(path));
	}

	private Optional<String> getFilePathExtensionKeyAsOpt(Path path) {
		return JMPath.getFilePathExtensionAsOpt(path)
				.map(this::buildFileExtensionKey);
	}

	private String buildFileExtensionKey(String extention) {
		return unknownFileName + extention;
	}

	private Image buildCachedFxImageOfFileIconInOS(Path path) {
		return getFilePathExtensionKeyAsOpt(path)
				.map(getCachedFxImageFunction(path))
				.orElseGet(() -> fxImageCache.get(unknownFileName));
	}

	private Function<String, Image> getCachedFxImageFunction(Path path) {
		Supplier<Image> newValueSupplier = () -> buildFxImage(
				jmIconFactory.getCachedBufferedImageOfIconInOS(path));
		return key -> JMMap.getOrPutGetNew(fxImageCache, key, newValueSupplier);
	}

	/**
	 * Builds the fx image.
	 *
	 * @param bufferedImage
	 *            the buffered image
	 * @return the image
	 */
	public static Image buildFxImage(BufferedImage bufferedImage) {
		return SwingFXUtils.toFXImage(bufferedImage, null);
	}

	/**
	 * Builds the image.
	 *
	 * @param nameInClasspath
	 *            the name in classpath
	 * @param requestedWidth
	 *            the requested width
	 * @param requestedHeight
	 *            the requested height
	 * @return the image
	 */
	public static Image buildImage(String nameInClasspath,
			double requestedWidth, double requestedHeight) {
		return new Image(getResourceAsStream(nameInClasspath), requestedWidth,
				requestedHeight, false, true);
	}

	/**
	 * Builds the image.
	 *
	 * @param nameInClasspath
	 *            the name in classpath
	 * @return the image
	 */
	public static Image buildImage(String nameInClasspath) {
		return new Image(getResourceAsStream(nameInClasspath));
	}

	/**
	 * Builds the image view.
	 *
	 * @param nameInClasspath
	 *            the name in classpath
	 * @return the image view
	 */
	public static ImageView buildImageView(String nameInClasspath) {
		return new ImageView(buildImage(nameInClasspath));
	}

	/**
	 * Builds the image view.
	 *
	 * @param nameInClasspath
	 *            the name in classpath
	 * @param requestedWidth
	 *            the requested width
	 * @param requestedHeight
	 *            the requested height
	 * @return the image view
	 */
	public static ImageView buildImageView(String nameInClasspath,
			double requestedWidth, double requestedHeight) {
		return buildImageView(
				buildImage(nameInClasspath, requestedWidth, requestedHeight));
	}

	/**
	 * Builds the image view.
	 *
	 * @param fxImage
	 *            the fx image
	 * @return the image view
	 */
	public static ImageView buildImageView(Image fxImage) {
		return new ImageView(fxImage);
	}

	/**
	 * Builds the image view.
	 *
	 * @param bufferedImage
	 *            the buffered image
	 * @return the image view
	 */
	public static ImageView buildImageView(BufferedImage bufferedImage) {
		return buildImageView(buildFxImage(bufferedImage));
	}

	/**
	 * Gets the image view of icon in OS.
	 *
	 * @param path
	 *            the path
	 * @return the image view of icon in OS
	 */
	public ImageView getImageViewOfIconInOS(Path path) {
		return JMOptional
				.getNullableAndFilteredOptional(path, JMPath.HiddenFilter)
				.map(this::buildImageViewAppliedDefaultOpacity)
				.orElseGet(() -> buildImageViewOfIconInOS(path));
	}

	/**
	 * Builds the image view applied default opacity.
	 *
	 * @param path
	 *            the path
	 * @param opacityValue
	 *            the opacity value
	 * @return the image view
	 */
	public ImageView buildImageViewAppliedDefaultOpacity(Path path,
			double opacityValue) {
		return applyOpacity(buildImageViewOfIconInOS(path), opacityValue);
	}

	/**
	 * Builds the image view applied default opacity.
	 *
	 * @param path
	 *            the path
	 * @return the image view
	 */
	public ImageView buildImageViewAppliedDefaultOpacity(Path path) {
		return buildImageViewAppliedDefaultOpacity(path, 0.5);
	}

	/**
	 * Builds the image view of icon in OS.
	 *
	 * @param path
	 *            the path
	 * @return the image view
	 */
	public ImageView buildImageViewOfIconInOS(Path path) {
		return buildImageView(getFxImageOfIconInOS(path));
	}

	/**
	 * Apply opacity.
	 *
	 * @param imageView
	 *            the image view
	 * @param opacityValue
	 *            the opacity value
	 * @return the image view
	 */
	public ImageView applyOpacity(ImageView imageView, double opacityValue) {
		imageView.setOpacity(opacityValue);
		return imageView;
	}

	private static InputStream getResourceAsStream(String nameInClasspath) {
		return JMFXIconFactory.class.getClassLoader()
				.getResourceAsStream(nameInClasspath);
	}
}
