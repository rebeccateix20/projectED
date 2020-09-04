/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructure.stack;

import DataStructure.Exceptions.EmptyCollectionException;


/**
 * @author Rebeca
 */
public interface StackADT<T> {

    /**
     * adiciona um elemento ao topo da stack
     *
     * @param element element to be pushed onto DataStructure.stack
     */
    public void push(T element);

    /**
     * devolve e remove o elemento no topo da stack
     *
     * @return T element removed from the top of the DataStructure.stack
     */
    public T pop() throws EmptyCollectionException;

    /**
     * retorna o elemento do topo
     *
     * @return T element on top of the DataStructure.stack
     */
    public T peek() throws EmptyCollectionException;

    /**
     * retorna true se estiver vazia
     *
     * @return boolean whether or not this DataStructure.stack is empty
     */
    public boolean isEmpty();

    /**
     * devolve o numero de elementos
     *
     * @return int number of elements in this DataStructure.stack
     */
    public int size();

    /**
     * devovle uma representacao da stack
     *
     * @return String representation of this DataStructure.stack
     */
    @Override
    public String toString();

}
