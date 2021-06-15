package io.kitejencien.asciiengine.tree;

import io.kitejencien.asciiengine.database.CharData;
import io.kitejencien.asciiengine.database.DataGenerator;
import io.kitejencien.asciiengine.io.DataBaseUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class Tree {

    public ArrayList<CharData> initialSet;
    public TreeNode root;

    public TreeNode build(TreeNode current, ArrayList<CharData> restOfData){

        //System.out.println(restOfData.size());
        //build root
        if(current == null){
            restOfData.sort(Comparator.comparingDouble(CharData::getBrightness));
        }

        if(restOfData.size() < 3){
            if(restOfData.size() == 0){
                return null;
            }
            if(restOfData.size() == 1){
                return new TreeNode(restOfData.get(0),null, null);
            }
            TreeNode node = new TreeNode(restOfData.get(1),null, null);
            node.left = build(node, new ArrayList<>(restOfData.subList(0,1)));
            return node;
        }

        int midIndex = restOfData.size()/2;
        TreeNode node = new TreeNode(restOfData.get(midIndex), null, null);
        node.left = build(node, new ArrayList<>(restOfData.subList(0, midIndex)));
        node.right = build(node, new ArrayList<>(restOfData.subList(midIndex, restOfData.size())));
        node.parent = current;
        return node;
    }

    public CharData search(double brightness){
        //System.out.println(brightness);
        TreeNode current = this.root;
        CharData out = null;
        while(current != null){
            out = current.content;
            current = brightness < current.content.getBrightness() ? current.left : current.right;
        }
        return out;
    }
}
