package com.paka.tagger.widgets.filebrowser.utils;

import com.paka.tagger.common.model.Tag;
import com.paka.tagger.config.MainAppConfig;
import com.paka.tagger.model.TreeEntity;
import com.paka.tagger.utils.FileUtils;
import com.paka.tagger.widgets.filebrowser.items.FilePathTreeItem;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import javafx.scene.control.TreeItem;

public class TreeUtils {

    private TreeUtils() {}

    private static Predicate<Path> pathPredicate;

    /**
     * Convenient method with default "path predicate"
     */
    public static List<FilePathTreeItem> getChildren(FilePathTreeItem item, int depth) {
        return getChildren(item, depth, getPathPredicate());
    }

    /**
     * Processes the tree hierarchy in TreeView
     *
     * @param item starting node
     * @param depth max depth (used to reduce waiting time, can be combined with lazy node opening)
     * @param predicate used for filtering out nodes that are not relevant
     * @return list of children with recursively updated nodes
     */
    public static List<FilePathTreeItem> getChildren(FilePathTreeItem item, int depth, Predicate<Path> predicate) {
        if (depth == 0) return new ArrayList<>();

        List<FilePathTreeItem> result = new ArrayList<>();

        Path fullPath = item.getValue().getPathItem().getFullPath();
        boolean isDirectory = fullPath.toFile().isDirectory();

        if (isDirectory) {
            DirectoryStream<Path> stream = null;
            try {
                System.out.println("Scanning dir: " + fullPath);
                stream = Files.newDirectoryStream(fullPath);
            } catch (Exception e) {
                System.out.println("Can't dig into directory: " + fullPath);
            }
            if (stream != null) {
                for (Path path : stream) {
                    if (predicate.test(path)) {
                        TreeEntity treeEntity = new TreeEntity(path);
                        if (!treeEntity.isDirectory()) {
                            treeEntity.addTag(new Tag(FileUtils.getFileExt(path)));
                        }
                        FilePathTreeItem treeNode = new FilePathTreeItem(treeEntity);
                        treeNode.getInternalChildren().addAll(getChildren(treeNode, depth - 1));
                        item.setScanned(true);
                        result.add(treeNode);
                    }
                }
            }
        }

        return result;
    }

    public static List<FilePathTreeItem> getChildrenUnlimitedDepth(FilePathTreeItem item) {
        return getChildren(item, Integer.MAX_VALUE, getPathPredicate());
    }

    //should get all children as a flat list
    public static List<FilePathTreeItem> getFlatChildrenList(FilePathTreeItem item) {
        List<FilePathTreeItem> result = new ArrayList<>();

        List<TreeItem> children = new ArrayList<>(item.getChildren());

//        for (TreeItem<TreeEntity> treeEntityTreeItem : item.getChildren()) {
//            result.addAll(getFlatChildrenList(treeEntityTreeItem));
//        }

        return result;
    }

    private static Predicate<Path> getPathPredicate() {
        if (pathPredicate == null) {
            pathPredicate = path -> path.toFile().isDirectory() || MainAppConfig.isPathSupported(path);
        }
        return pathPredicate;
    }

    //TODO: setPredicate()?
}
