package io.kitejencien.asciiengine.tree;

import io.kitejencien.asciiengine.database.CharData;

public class TreeNode {

    public TreeNode parent;
    public TreeNode left, right;
    public CharData content;

    public TreeNode(CharData content, TreeNode left, TreeNode right){
        this.content = content;
        this.left = left;
        this.right = right;
    }

    public TreeNode getLeft() {
        return left;
    }

    public TreeNode getRight() {
        return right;
    }

    public TreeNode getParent() {
        return parent;
    }
}
