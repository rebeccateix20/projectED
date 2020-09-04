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
     * remove e devolve o primeiro elemento
     *
     * @return the first element from this DataStructure.list
     */
    public T removeFirst() throws EmptyCollectionException;

    /**
     * remove e devolve o ultimo elemento
     *
     * @return the last element from this DataStructure.list
     */
    public T removeLast() throws EmptyCollectionException;

    /**
     * devolve e remove um certo elemento
     *
     * @param element the element to be removed from the DataStructure.list
     */
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException;

    /**
     * retorna uma referencia do primeiro elemento
     *
     * @return a reference to the first element in this DataStructure.list
     */
    public T first();

    /**
     * reotrna uma referencia ao ultimo elemento
     *
     * @return a reference to the last element in this DataStructure.list
     */
    public T last();

    /**
     * retorna true se conter um elemento, falso em contrario
     *
     * @param target the target that is being sought in the DataStructure.list
     * @return true if the DataStructure.list contains this element
     */
    public boolean contains(T target);

    /**
     * retorna true se estiver vazia, falso em contrario
     *
     * @return true if this DataStructure.list contains no elements
     */
    public boolean isEmpty();

    /**
     * retorna o numero de elementos da lista
     *
     * @return the integer representation of number of elements in this DataStructure.list
     */
    public int size();

    /**
     * retorna um iterador da lista
     *
     * @return an iterator over the elements in this DataStructure.list
     */
    public Iterator<T> iterator();

    /**
     * retorna uma representacao da lista
     *
     * @return a string representation of this DataStructure.list
     */
    @Override
    public String toString();

}
