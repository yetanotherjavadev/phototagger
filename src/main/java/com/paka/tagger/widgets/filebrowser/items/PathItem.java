package com.paka.tagger.widgets.filebrowser.items;

import com.paka.tagger.utils.FileUtils;
import java.nio.file.Path;
import lombok.Getter;

@Getter
public class PathItem { //TODO check the contents of this class

	private String displayPath;
	private Path fullPath;

	public PathItem(Path path) {
		this.fullPath = path;
		String fileName = FileUtils.getFileName(path);
		if (fileName == null || fileName.isEmpty()) {
			this.displayPath = path.toString();
		} else {
			this.displayPath = fileName;
		}
	}
}
