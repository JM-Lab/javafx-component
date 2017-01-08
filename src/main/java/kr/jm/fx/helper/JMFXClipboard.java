package kr.jm.fx.helper;

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.util.Collections;
import java.util.List;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import kr.jm.fx.path.JMFXPath;
import kr.jm.utils.helper.JMOptional;

public class JMFXClipboard {
	private static List<File> lastClipboardFileList;
	private static Clipboard clipboard = Clipboard.getSystemClipboard();

	public static void putPathList(List<JMFXPath> jmfxPathList) {
		JMOptional.getOptional(jmfxPathList)
				.map(list -> list.stream().map(JMFXPath::getAbsolutePathName)
						.collect(toList()))
				.ifPresent(JMFXClipboard::setFilePathListInClipboard);
	}

	synchronized private static void
			setFilePathListInClipboard(List<String> list) {
		ClipboardContent content = new ClipboardContent();
		content.putFilesByPath(list);
		clipboard.setContent(content);
		lastClipboardFileList = content.getFiles();
	}

	synchronized public static void clear() {
		lastClipboardFileList = null;
		clipboard.clear();
	}

	public static List<JMFXPath> getLastClipboardJMFXPathList() {
		return JMOptional.getOptional(lastClipboardFileList)
				.map(List::stream).map(s -> s.map(File::toPath)
						.map(JMFXPath::getInstance).collect(toList()))
				.orElseGet(Collections::emptyList);
	}
}
