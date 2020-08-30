/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructure.Heap;

import DataStructure.BinaryTree.BinaryTreeNode;

/**
 *
 * @author Rebeca
 */
public class HeapNode<T> extends BinaryTreeNode<T> {

    protected HeapNode<T> parent;

    public HeapNode(T obj) {
        super(obj);
        parent = null;
    }

    public HeapNode<T> getParent() {
        return parent;
    }

    public void setParent(HeapNode<T> parent) {
        this.parent = parent;
    }
    
    
    
}
