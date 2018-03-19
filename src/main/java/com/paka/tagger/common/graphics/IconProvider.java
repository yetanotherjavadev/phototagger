package com.paka.tagger.common.graphics;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

import static com.paka.tagger.common.constants.Icons.*;

public class IconProvider {

	private Map<String, Image> cached;

	private String[] paths = {
			COMPUTER_ICON_PATH,
			FILE_ICON_PATH,
			FOLDER_COLLAPSE_ICON_PATH,
			FOLDER_EXPAND_ICON_PATH
	};

	private static IconProvider INSTANCE = new IconProvider();

	private IconProvider() {
		this.cached = new HashMap<>();
		init();
	}

	private void init() {
		for (String path : paths) {
			cached.put(path, getImageFromPath(path));
		}
	}

	private Image getImageFromPath(String path) {
		return new Image(ClassLoader.getSystemClassLoader().getResourceAsStream(path));
	}

	public static Image getImage(String key) {
		return INSTANCE.cached.get(key);
	}
}
