/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructure.stack;

import DataStructure.Exceptions.EmptyCollectionException;


/**
 *
 * @author Rebeca
 */
public interface StackADT<T> {

    /**
     * Adds one element to the top of this DataStructure.stack.
     *
     * @param element element to be pushed onto DataStructure.stack
     */
    public void push(T element);

    /**
     * Removes and returns the top element from this DataStructure.stack.
     *
     * @return T element removed from the top of the DataStructure.stack
     */
    public T pop() throws EmptyCollectionException;

    /**
     * Returns without removing the top element of this DataStructure.stack.
     *
     * @return T element on top of the DataStructure.stack
     */
    public T peek() throws EmptyCollectionException;

    /**
     * Returns true if this DataStructure.stack contains no elements.
     *
     * @return boolean whether or not this DataStructure.stack is empty
     */
    public boolean isEmpty();

    /**
     * Returns the number of elements in this DataStructure.stack.
     *
     * @return int number of elements in this DataStructure.stack
     */
    public int size();

    /**
     * Returns a string representation of this DataStructure.stack.
     *
     * @return String representation of this DataStructure.stack
     */
    @Override
    public String toString();

}
