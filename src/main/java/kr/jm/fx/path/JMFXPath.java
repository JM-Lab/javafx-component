package kr.jm.fx.path;

import static java.util.stream.Collectors.toList;
import static kr.jm.utils.helper.JMLambda.supplierByBoolean;
import static kr.jm.utils.helper.JMPredicate.negate;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import kr.jm.fx.helper.JMFXIconFactory;
import kr.jm.utils.FileSize;
import kr.jm.utils.datastructure.JMMap;
import kr.jm.utils.enums.OS;
import kr.jm.utils.helper.JMPath;
import kr.jm.utils.time.JMTimeUtil;

/**
 * The Class JMFXPath.
 */
public class JMFXPath {

	private static final String ROOT_PATH = "";
	private static final JMFXIconFactory jmfxIconFactory =
			new JMFXIconFactory();
	private static final String TimeFormat = "yyyy-MM-dd HH:mm";
	private static final Map<String, JMFXPath> jmfxPathCache =
			new ConcurrentHashMap<>();
	private static final JMFXPath rootPath =
			JMMap.putGetNew(jmfxPathCache, ROOT_PATH,
					new JMFXPath(ROOT_PATH, OS.getOS().getRootFileList()
							.stream().map(File::toPath)
							.map(JMFXPath::getInstance).collect(toList())));

	private Path path;
	private String absolutePathName;
	private long lastTimestamp;
	private boolean isDirectory;
	private boolean isHidden;
	private boolean isSymbolicLink;
	private ObservableList<JMFXPath> observableChildrenList;
	private String name;

	private ObjectProperty<JMFXPath> jmfxPath;
	private StringProperty dateModified;
	private StringProperty type;
	private ObjectProperty<FileSize> fileSize;

	/**
	 * Gets the single instance of JMFXPath.
	 *
	 * @param path
	 *            the path
	 * @return single instance of JMFXPath
	 */
	public static JMFXPath getInstance(Path path) {
		return Optional.ofNullable(path).filter(negate(ROOT_PATH::equals))
				.map(Path::toAbsolutePath).map(Path::toString)
				.map(pathString -> JMMap.getOrPutGetNew(jmfxPathCache,
						pathString, getNewJMFXPathSupplier(pathString)))
				.orElse(rootPath);
	}

	/**
	 * Gets the single instance of JMFXPath.
	 *
	 * @param pathString
	 *            the path string
	 * @return single instance of JMFXPath
	 */
	public static JMFXPath getInstance(String pathString) {
		return Optional.ofNullable(pathString).filter(negate(ROOT_PATH::equals))
				.map(JMPath::getPath).map(JMFXPath::getInstance)
				.orElse(rootPath);
	}

	private static Supplier<JMFXPath>
			getNewJMFXPathSupplier(String pathString) {
		return () -> new JMFXPath(pathString);
	}

	public static JMFXPath getRootPath() {
		return rootPath;
	}

	public static JMFXPath getHome() {
		return getInstance(OS.getUserHomeDir());
	}

	private JMFXPath(String pathString, List<JMFXPath> childrenList) {
		this(pathString);
		this.isDirectory = true;
		this.observableChildrenList.addAll(childrenList);
	}

	private JMFXPath(String pathString) {
		this.path = JMPath.getPath(pathString);
		this.absolutePathName =
				ROOT_PATH.equals(pathString) ? ROOT_PATH : path.toString();
		this.isDirectory = JMPath.isDirectory(path);
		if (isDirectory)
			this.observableChildrenList = FXCollections.observableArrayList();
		this.isSymbolicLink = JMPath.isSymbolicLink(path);
		this.isHidden = JMPath.isHidden(path);
		this.name = ROOT_PATH.equals(pathString) ? ROOT_PATH
				: JMPath.getPathNameInOS(path);

		this.jmfxPath = new SimpleObjectProperty<>(this);
		this.dateModified = new SimpleStringProperty(
				JMTimeUtil.getTime(JMPath.getLastModified(path), TimeFormat));
		this.type =
				new SimpleStringProperty(JMPath.getPathTypeDescription(path));
		this.fileSize = supplierByBoolean(JMPath.isRegularFile(path),
				() -> new SimpleObjectProperty<>(new FileSize(path)),
				() -> new SimpleObjectProperty<>(new FileSize(0)));

		setLastTimestamp();
	}

	private void setLastTimestamp() {
		this.lastTimestamp = System.currentTimeMillis();
	}

	public Path getPath() {
		setLastTimestamp();
		return this.path;
	}

	public String getAbsolutePathName() {
		return this.absolutePathName;
	}

	public long getLastTimestamp() {
		return this.lastTimestamp;
	}

	public boolean isDirectory() {
		setLastTimestamp();
		return this.isDirectory;
	}

	public boolean isHidden() {
		setLastTimestamp();
		return this.isHidden;
	}

	public String getName() {
		return this.name;
	}

	public Stream<JMFXPath> getAncestorDirectoryJMFXPathStream() {
		return JMPath.getAncestorPathList(getPath()).stream()
				.map(JMFXPath::getInstance);
	}

	public Stream<JMFXPath> getChildrenDirctoryJMFXStream() {
		return getObservableChildrenList().stream()
				.filter(JMFXPath::isDirectory);
	}

	public Stream<JMFXPath> getChildrenJMFXPathStream() {
		return this == rootPath ? this.observableChildrenList.stream()
				: JMPath.getChildrenPathStream(path)
						.sorted(JMPath.directoryFirstComparator)
						.map(JMFXPath::getInstance);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		setLastTimestamp();
		return name;
	}

	public ObservableList<JMFXPath> getObservableChildrenList() {
		return isDirectory ? setAndGetObservableChildrenList()
				: FXCollections.emptyObservableList();
	}

	synchronized private ObservableList<JMFXPath>
			setAndGetObservableChildrenList() {
		List<JMFXPath> childrenList =
				getChildrenJMFXPathStream().collect(toList());
		observableChildrenList.clear();
		observableChildrenList.addAll(childrenList);
		return observableChildrenList;
	}

	public ImageView getIcon() {
		return jmfxIconFactory.getImageViewOfIconInOS(path);
	}

	public Optional<JMFXPath> getParent() {
		return Optional.ofNullable(path.getParent()).map(JMFXPath::getInstance);
	}

	/**
	 * Jmfx path property.
	 *
	 * @return the object property
	 */
	public ObjectProperty<JMFXPath> jmfxPathProperty() {
		return this.jmfxPath;
	}

	public JMFXPath getJmfxPath() {
		return this.jmfxPathProperty().get();
	}

	/**
	 * Date modified property.
	 *
	 * @return the string property
	 */
	public StringProperty dateModifiedProperty() {
		return this.dateModified;
	}

	public String getDateModified() {
		return this.dateModifiedProperty().get();
	}

	/**
	 * Type property.
	 *
	 * @return the string property
	 */
	public StringProperty typeProperty() {
		return this.type;
	}

	public String getType() {
		return this.typeProperty().get();
	}

	/**
	 * File size property.
	 *
	 * @return the object property
	 */
	public ObjectProperty<FileSize> fileSizeProperty() {
		return this.fileSize;
	}

	public FileSize getFileSize() {
		return this.fileSizeProperty().get();
	}

	public boolean isSymbolicLink() {
		return isSymbolicLink;
	}

}
