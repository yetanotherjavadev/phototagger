package com.paka.tagger.widgets;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AttachmentListCell extends ListCell<String> {

	private static Map<String, Image> mapOfFileExtToSmallIcon = new HashMap<>();

	@Override
	public void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);
		if (empty) {
			setGraphic(null);
			setText(null);
		} else {
			Image fxImage = getFileIcon(item);
			ImageView imageView = new ImageView(fxImage);
			setGraphic(imageView);
			setText(item);
		}
	}

	private static String getFileExt(String filename) {
		String ext = ".";
		int p = filename.lastIndexOf('.');
		if (p >= 0) {
			ext = filename.substring(p);
		}
		return ext.toLowerCase();
	}


	private static Image getFileIcon(String filename) {
		final String ext = getFileExt(filename);

		Image fileIcon = mapOfFileExtToSmallIcon.get(ext);
		if (fileIcon == null) {
			Icon jswingIcon = null;

			File file = new File(filename);
			if (file.exists()) {
				jswingIcon = getJSwingIconFromFileSystem(file);
			} else {
				File tempFile = null;
				try {
					tempFile = File.createTempFile("icon", ext);
					jswingIcon = getJSwingIconFromFileSystem(tempFile);
				} catch (IOException ignored) {
					System.out.println("Cannot create temporary file: icon." + ext);
				} finally {
					if (tempFile != null) tempFile.delete();
				}
			}

			if (jswingIcon != null) {
				fileIcon = jswingIconToImage(jswingIcon);
				mapOfFileExtToSmallIcon.put(ext, fileIcon);
			}
		}

		return fileIcon;
	}

	private static Image jswingIconToImage(Icon jswingIcon) {
		BufferedImage bufferedImage = new BufferedImage(jswingIcon.getIconWidth(), jswingIcon.getIconHeight(),
				BufferedImage.TYPE_INT_ARGB);
		jswingIcon.paintIcon(null, bufferedImage.getGraphics(), 0, 0);
		return SwingFXUtils.toFXImage(bufferedImage, null);
	}

	/**
	 * Tries to get an icon associated to the given file type
	 */
	private static Icon getJSwingIconFromFileSystem(File file) {
		Icon icon;
		if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) { //we are on Windows
			FileSystemView view = FileSystemView.getFileSystemView();
			icon = view.getSystemIcon(file);
		} else { //e.g. OS X or whatever
			JFileChooser fc = new JFileChooser();
			icon = fc.getUI().getFileView(fc).getIcon(file);
		}
		return icon;
	}
}