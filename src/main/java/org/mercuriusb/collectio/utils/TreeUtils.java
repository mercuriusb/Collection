package org.mercuriusb.collectio.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeUtils {

  public static  <T extends TreeNode<T>> T buildTree(List<T> nodes, String rootPath) {
    Map<String, T> pathMap = new HashMap<>();
    T root = null;

    // Map nodes by their paths
    for (T node : nodes) {
      pathMap.put(node.getPath(), node);
      if (node.getPath().equals(rootPath)) {
        root = node;
      }
    }

    // Link children to their parents
    for (T node : nodes) {
      String parentPath = getParentPath(node.getPath());
      T parent = pathMap.get(parentPath);
      if (parent != null) {
        parent.addChild(node);
      }
    }

    return root;
  }

  public static String getParentPath(String path) {
    int lastDot = path.lastIndexOf('.');
    return (lastDot == -1) ? null : path.substring(0, lastDot);
  }
}
