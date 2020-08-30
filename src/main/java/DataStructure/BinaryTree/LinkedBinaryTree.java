/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructure.BinaryTree;

import DataStructure.Exceptions.ElementNotFoundException;
import DataStructure.Exceptions.EmptyCollectionException;
import java.util.Iterator;

import DataStructure.list.UnorderedList.ArrayUnorderedList;
import DataStructure.queue.ArrayQueue;
import DataStructure.queue.QueueADT;

/**
 *
 * @author Rebeca
 */
public class LinkedBinaryTree<T> implements BinaryTreeADT<T> {

    protected int count;
    protected BinaryTreeNode<T> root;

    public LinkedBinaryTree() {
        this.count = 0;
        this.root = null;
    }

    public LinkedBinaryTree(T element) {
        this.count = 1;
        this.root = new BinaryTreeNode<T>(element);
    }

    @Override
    public T getRoot() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        return this.root.getElement();
    }

    @Override
    public boolean isEmpty() {
        return this.count == 0;
    }

    @Override
    public int size() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        return this.count;
    }

    @Override
    public boolean contains(T targetElement) {
        boolean found = false;
        try {
            this.find(targetElement);
            found = true;
        } catch (ElementNotFoundException ex) {
        } catch (EmptyCollectionException ex) {
        }
        return found;
    }

    @Override
    public T find(T targetElement) throws ElementNotFoundException, EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        BinaryTreeNode<T> current = findAgain(targetElement, this.root);

        if (current == null) {
            throw new ElementNotFoundException("binary tree");
        }

        return (current.getElement());
    }

    /**
     * Returns a reference to the specified target element if it is found in
     * this binary tree.
     *
     * @param targetElement the element being sought in this tree
     * @param next the element to begin searching from
     */
    private BinaryTreeNode<T> findAgain(T targetElement,
            BinaryTreeNode<T> next) {
        if (next == null) {
            return null;
        }

        if (next.getElement().equals(targetElement)) {
            return next;
        }

        BinaryTreeNode<T> temp = findAgain(targetElement, next.getLeft());

        if (temp == null) {
            temp = findAgain(targetElement, next.getRight());
        }

        return temp;
    }

    @Override
    public Iterator<T> iteratorInOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        inorder(this.root, tempList);

        return tempList.iterator();
    }

    protected void inorder(BinaryTreeNode<T> node, ArrayUnorderedList<T> tempList) {
        if (node != null) {
            inorder(node.getLeft(), tempList);
            tempList.addToRear(node.getElement());
            inorder(node.getRight(), tempList);
        }
    }

    @Override
    public Iterator<T> iteratorPreOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        preOrder(this.root, tempList);
        return tempList.iterator();
    }

    protected void preOrder(BinaryTreeNode<T> node, ArrayUnorderedList<T> tempList) {
        if (node != null) {
            tempList.addToRear(node.getElement());
            preOrder(node.getLeft(), tempList);
            preOrder(node.getRight(), tempList);
        }
    }

    @Override
    public Iterator<T> iteratorPostOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        postOrder(this.root, tempList);
        return tempList.iterator();
    }

    protected void postOrder(BinaryTreeNode<T> node, ArrayUnorderedList<T> tempList) {
        if (node != null) {
            postOrder(node.getLeft(), tempList);
            postOrder(node.getRight(), tempList);
            tempList.addToRear(node.getElement());
        }
    }

    @Override
    public Iterator<T> iteratorLevelOrder() throws EmptyCollectionException {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();

        levelOrder(this.root, tempList);
        return tempList.iterator();
    }

    protected void levelOrder(BinaryTreeNode<T> node, ArrayUnorderedList<T> tempList) throws EmptyCollectionException {
        QueueADT<BinaryTreeNode<T>> nodes = new ArrayQueue();
        if (node != null) {
            nodes.enqueue(node);
        }
        while (!nodes.isEmpty()) {
            BinaryTreeNode<T> element = nodes.dequeue();
            if (element != null) {
                tempList.addToRear(element.getElement());
                if (element.getLeft() != null) {
                    nodes.enqueue(element.getLeft());
                }

                if (element.getRight() != null) {
                    nodes.enqueue(element.getRight());
                }
            } else {
                tempList.addToRear(null);
            }
        }
    }
}
