/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructure.queue;

import DataStructure.Exceptions.EmptyCollectionException;

/**
 * @author Rebeca
 */
public interface QueueADT<T> {

    /**
     * adiciona um elemento no final da queue
     *
     * @param element the element to be added to the rear of this DataStructure.queue
     */
    public void enqueue(T element);

    /**
     * devolve e remove o primeiro elemento da queue
     *
     * @return the element at the front of this DataStructure.queue
     */
    public T dequeue() throws EmptyCollectionException;

    /**
     * retorna o primeiro elemento
     *
     * @return the first element in this DataStructure.queue
     */
    public T first() throws EmptyCollectionException;

    /**
     * verifica se esta vazia
     *
     * @return true if this DataStructure.queue is empty
     */
    public boolean isEmpty();

    /**
     * devovlle o numero de elementos
     *
     * @return the integer representation of the size of this DataStructure.queue
     */
    public int size();

    /**
     * devolve uma string da queue
     *
     * @return the string representation of this DataStructure.queue
     */
    public String toString();
}
