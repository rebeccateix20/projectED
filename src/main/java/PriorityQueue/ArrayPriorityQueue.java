/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PriorityQueue;

import Exceptions.EmptyCollectionException;
import Heap.ArrayHeap;

/**
 *
 * @author Rebeca
 */
public class ArrayPriorityQueue<T> extends ArrayHeap<PriorityQueueNode<T>> {

    public ArrayPriorityQueue() {
        super();
    }

    /**
     * Adiciona um elemento
     * @param object elemento a ser adicionado
     * @param priority prioridade desse elemento
     */
    public void addElement(T object, double priority) {
        PriorityQueueNode<T> node = new PriorityQueueNode<T>(object, priority);
        super.addElement(node);
    }

    /**
     * Remove o proximo elemento com maior prioridade e devolve uma referência
     * para o elemento removido
     *
     * @return a reference to the next highest priority element in this queue
     */
    public T removeNext() throws EmptyCollectionException {
        PriorityQueueNode<T> temp = (PriorityQueueNode<T>) super.removeMin();
        return temp.getElement();
    }

}
