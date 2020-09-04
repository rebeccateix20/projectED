/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructure.BinaryTree;

import DataStructure.Exceptions.ElementNotFoundException;
import DataStructure.Exceptions.EmptyCollectionException;

import java.util.Iterator;

/**
 * @author Rebeca
 */
public interface BinaryTreeADT<T> {

    /**
     * retorna uma referencia para o eleemnto raiz
     *
     * @return a reference to the root
     */
    public T getRoot() throws EmptyCollectionException;

    /**
     * retorna verdadeiro se estiver vazia e falso em contrario
     *
     * @return true if this binary tree is empty
     */
    public boolean isEmpty();

    /**
     * retorna o numero de elementos da arvore
     *
     * @return the integer number of elements in this tree
     */
    public int size() throws EmptyCollectionException;

    /**
     * retorna verdadeiro se a arvore tiver um elemento que corresponda ao passado como parametro
     * falso em contrario
     *
     * @param targetElement the element being sought in the tree
     * @return true if the tree contains the target element
     */
    public boolean contains(T targetElement);

    /**
     * retorna uma referencia para o elemento que procura caso exista
     *
     * @param targetElement the element being sought in the tree
     * @return a reference to the specified element
     */
    public T find(T targetElement) throws ElementNotFoundException, EmptyCollectionException;

    /**
     * retorna uma string da arvore
     *
     * @return a string representation of the binary tree
     */
    public String toString();

    /**
     * Performs an inorder traversal on this binary tree by calling an
     * overloaded, recursive inorder method that starts with the root.
     *
     * @return an iterator over the elements of this binary tree
     */
    public Iterator<T> iteratorInOrder();

    /**
     * Performs a preorder traversal on this binary tree by calling an
     * overloaded, recursive preorder method that starts with the root.
     *
     * @return an iterator over the elements of this binary tree
     */
    public Iterator<T> iteratorPreOrder();

    /**
     * Performs a postorder traversal on this binary tree by calling an
     * overloaded, recursive postorder method that starts with the root.
     *
     * @return an iterator over the elements of this binary tree
     */
    public Iterator<T> iteratorPostOrder();

    /**
     * Performs a levelorder traversal on the binary tree, using a DataStructure.queue.
     *
     * @return an iterator over the elements of this binary tree
     */
    public Iterator<T> iteratorLevelOrder() throws EmptyCollectionException;

}
