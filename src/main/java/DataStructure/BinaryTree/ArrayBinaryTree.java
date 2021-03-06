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
 * @author Rebeca
 */
public class ArrayBinaryTree<T> implements BinaryTreeADT<T> {

    protected int count;
    protected T[] tree;
    private final int CAPACITY = 50;

    /**
     * Cria uma arvore binaria vazia
     */
    public ArrayBinaryTree() {
        this.count = 0;
        this.tree = (T[]) new Object[CAPACITY];
    }

    /**
     * cria uma arvore binaria com o elemento como raiz
     *
     * @param element vai ser a raiz da arvore
     */
    public ArrayBinaryTree(T element) {
        this.count = 1;
        this.tree = (T[]) new Object[this.CAPACITY];
        this.tree[0] = element;
    }

    @Override
    public T getRoot() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        return this.tree[0];
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
        T temp = null;
        boolean found = false;

        for (int ct = 0; ct < this.count && !found; ct++) {
            if (targetElement.equals(this.tree[ct])) {
                found = true;
                temp = this.tree[ct];
            }
        }
        if (!found) {
            throw new ElementNotFoundException("binary tree");
        }
        return temp;

    }

    /**
     * Iterador in order
     * @return
     */
    @Override
    public Iterator<T> iteratorInOrder() {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();
        inorder(0, templist);
        return templist.iterator();
    }

    protected void inorder(int node, ArrayUnorderedList<T> templist) {
        if (node < this.tree.length) {
            if (this.tree[node] != null) {
                inorder(node * 2 + 1, templist); //left
                templist.addToRear(this.tree[node]);
                inorder((node + 1) * 2, templist); //right
            }
        }
    }

    /**
     * Iterador pre order
     * @return
     */
    @Override
    public Iterator<T> iteratorPreOrder() {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();
        preOrder(0, templist);
        return templist.iterator();
    }

    protected void preOrder(int node, ArrayUnorderedList<T> templist) {
        if (node < this.tree.length) {
            if (this.tree[node] != null) {
                templist.addToRear(tree[node]);
                preOrder(node * 2 + 1, templist);
                preOrder((node + 1) * 2, templist);
            }
        }
    }

    /**
     * iterador post order
     * @return
     */
    @Override
    public Iterator<T> iteratorPostOrder() {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();
        postOrder(0, templist);
        return templist.iterator();
    }

    protected void postOrder(int node, ArrayUnorderedList<T> templist) {
        if (node < this.tree.length) {
            if (this.tree[node] != null) {
                preOrder(node * 2 + 1, templist);
                preOrder((node + 1) * 2, templist);
                templist.addToRear(tree[node]);
            }
        }
    }

    /**
     * iterador level order
     * @return
     * @throws EmptyCollectionException
     */
    @Override
    public Iterator<T> iteratorLevelOrder() throws EmptyCollectionException {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();
        levelOrder(0, templist);
        return templist.iterator();
    }

    protected void levelOrder(int node, ArrayUnorderedList<T> templist) throws EmptyCollectionException {
        if (node < this.tree.length) {
            QueueADT<Integer> nodes = new ArrayQueue<>();
            if (this.tree[node] != null) {
                nodes.enqueue(node);
            }
            while (!nodes.isEmpty()) {
                Integer element = nodes.dequeue();
                if (this.tree[element] != null) {
                    templist.addToRear(this.tree[element]);
                    if (this.tree[element * 2 + 1] != null) {
                        nodes.enqueue(element * 2 + 1);
                    }

                    if (this.tree[(element + 1) * 2] != null) {
                        nodes.enqueue((element + 1) * 2);
                    }
                } else {
                    templist.addToRear(null);
                }
            }

        }

    }

}
