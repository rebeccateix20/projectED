/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructure.queue;

import DataStructure.Exceptions.EmptyCollectionException;

/**
 *
 * @author Rebeca
 */
public interface QueueADT<T> {

    /**
     * Adds one element to the rear of this DataStructure.queue.
     *
     * @param element the element to be added to the rear of this DataStructure.queue
     */
    public void enqueue(T element);

    /**
     * Removes and returns the element at the front of this DataStructure.queue.
     *
     * @return the element at the front of this DataStructure.queue
     */
    public T dequeue() throws EmptyCollectionException;

    /**
     * Returns without removing the element at the front of this DataStructure.queue.
     *
     * @return the first element in this DataStructure.queue
     */
    public T first() throws EmptyCollectionException;

    /**
     * Returns true if this DataStructure.queue contains no elements.
     *
     * @return true if this DataStructure.queue is empty
     */
    public boolean isEmpty();

    /**
     * Returns the number of elements in this DataStructure.queue.
     *
     * @return the integer representation of the size of this DataStructure.queue
     */
    public int size();

    /**
     * Returns a string representation of this DataStructure.queue.
     *
     * @return the string representation of this DataStructure.queue
     */
    public String toString();
}
