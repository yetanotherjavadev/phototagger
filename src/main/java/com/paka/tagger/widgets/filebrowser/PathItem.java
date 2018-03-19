package com.paka.tagger.widgets.filebrowser;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@AllArgsConstructor
public class PathItem {

	private String displayPath;
	private Path fullPath;

	public PathItem(String displayPath, String fullPath) {
		this.displayPath = displayPath;
		this.fullPath = Paths.get(fullPath);
	}

	@Override
	public String toString() {
		return displayPath;
	}
}
