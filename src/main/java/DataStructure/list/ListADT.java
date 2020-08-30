/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructure.list;

import DataStructure.Exceptions.ElementNotFoundException;
import DataStructure.Exceptions.EmptyCollectionException;

import java.util.Iterator;

/**
 * @author Rebeca
 */
public interface ListADT<T> extends Iterable<T> {

    /**
     * Removes and returns the first element from this DataStructure.list.
     *
     * @return the first element from this DataStructure.list
     */
    public T removeFirst() throws EmptyCollectionException;

    /**
     * Removes and returns the last element from this DataStructure.list.
     *
     * @return the last element from this DataStructure.list
     */
    public T removeLast() throws EmptyCollectionException;

    /**
     * Removes and returns the specified element from this DataStructure.list.
     *
     * @param element the element to be removed from the DataStructure.list
     */
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException;

    /**
     * Returns a reference to the first element in this DataStructure.list.
     *
     * @return a reference to the first element in this DataStructure.list
     */
    public T first();

    /**
     * Returns a reference to the last element in this DataStructure.list.
     *
     * @return a reference to the last element in this DataStructure.list
     */
    public T last();

    /**
     * Returns true if this DataStructure.list contains the specified target element.
     *
     * @param target the target that is being sought in the DataStructure.list
     * @return true if the DataStructure.list contains this element
     */
    public boolean contains(T target);

    /**
     * Returns true if this DataStructure.list contains no elements.
     *
     * @return true if this DataStructure.list contains no elements
     */
    public boolean isEmpty();

    /**
     * Returns the number of elements in this DataStructure.list.
     *
     * @return the integer representation of number of elements in this DataStructure.list
     */
    public int size();

    /**
     * Returns an iterator for the elements in this DataStructure.list.
     *
     * @return an iterator over the elements in this DataStructure.list
     */
    public Iterator<T> iterator();

    /**
     * Returns a string representation of this DataStructure.list.
     *
     * @return a string representation of this DataStructure.list
     */
    @Override
    public String toString();

}
