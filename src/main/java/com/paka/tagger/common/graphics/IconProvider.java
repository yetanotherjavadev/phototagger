package com.paka.tagger.common.graphics;

import static com.paka.tagger.common.constants.IconPaths.COMPUTER_ICON_PATH;
import static com.paka.tagger.common.constants.IconPaths.FILE_ICON_PATH;
import static com.paka.tagger.common.constants.IconPaths.FOLDER_COLLAPSE_ICON_PATH;
import static com.paka.tagger.common.constants.IconPaths.FOLDER_EXPAND_ICON_PATH;
import static com.paka.tagger.utils.FileUtils.getFileExt;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

public class IconProvider {

    private static Map<String, Image> cachedIcons = new HashMap<>();

    private static String[] paths = {
            COMPUTER_ICON_PATH,
            FILE_ICON_PATH,
            FOLDER_COLLAPSE_ICON_PATH,
            FOLDER_EXPAND_ICON_PATH
    };

    static {
        init();
    }

    private static void init() {
        for (String path : paths) {
            cachedIcons.put(path, getImageFromPath(path));
        }
    }

    private static Image getImageFromPath(String path) {
        return new Image(ClassLoader.getSystemClassLoader().getResourceAsStream(path));
    }

    public static Image getImage(String key) {
        if (cachedIcons.containsKey(key))
            return cachedIcons.get(key);
        return getFileIcon(key);
    }


    private static Image getFileIcon(String filename) {
        final String ext = getFileExt(filename);

        Image fileIcon = cachedIcons.get(ext);
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
                cachedIcons.put(ext, fileIcon);
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
