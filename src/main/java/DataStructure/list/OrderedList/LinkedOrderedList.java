/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructure.list.OrderedList;

import javax.activation.UnsupportedDataTypeException;
import DataStructure.list.LinearNode;
import DataStructure.list.LinkedList;

/**
 *
 * @author Rebeca
 */
public class LinkedOrderedList<T> extends LinkedList<T> implements OrderedListADT<T> {

    public LinkedOrderedList() {
        super();
    }

    @Override
    public void add(T element) throws UnsupportedDataTypeException {
        if (!(element instanceof Comparable)) {
            throw new UnsupportedDataTypeException();
        }
        LinearNode<T> newNode = new LinearNode(element);

        /*
        CompareTo devolve: -1 caso menor
                            1 caso maior
                            0 caso igual
         */
        if (this.isEmpty()) {
            this.front = this.rear = newNode;
        } else {
            if (((Comparable) element).compareTo(this.front.getElement()) < 0) { //caso seja para ser adicionado à cabeça
                newNode.setNext(this.front);
                this.front = newNode;
            } else if (((Comparable) element).compareTo(this.rear.getElement()) > 0) {//caso seja para ser adicionado na cauda
                this.rear.setNext(newNode);
                newNode.setNext(null);
                this.rear = this.rear.getNext();
            } else {
                LinearNode<T> previous = null;
                LinearNode<T> current = this.front;
                while (current != null && ((Comparable) element).compareTo(current.getElement()) > 0) {
                    previous = current;
                    current = current.getNext();
                }
                newNode.setNext(current);
                previous.setNext(newNode);
            }

        }
        this.count++;
        this.modCount++;
    }

}
