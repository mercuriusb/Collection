package org.mercuriusb.collectio.utils;

import java.util.List;

public interface TreeNode<T extends TreeNode<T>> {
  // Method to return a list of children
  List<T> getChildren();
  // Method to add an instance of the class itself
  void addChild(T child);
  // returns an ltree conform path, where path elements are separated by a dot
  String getPath();
}
