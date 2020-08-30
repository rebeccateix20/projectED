/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructure.list.OrderedList;

import javax.activation.UnsupportedDataTypeException;

import DataStructure.list.DoubleLinkedList;
import DataStructure.list.DoubleNode;

/**
 * @author Rebeca
 */
public class DoubleLinkedOrderedList<T> extends DoubleLinkedList<T> implements OrderedListADT<T> {

    public DoubleLinkedOrderedList() {
        super();
    }

    @Override
    public void add(T element) throws UnsupportedDataTypeException {

        if (!(element instanceof Comparable)) {
            throw new UnsupportedDataTypeException();
        }
        DoubleNode<T> newNode = new DoubleNode(element);

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
                newNode.setPrevious(null);
                this.front.setPrevious(newNode);
                this.front = this.front.getPrevious();
            } else if (((Comparable) element).compareTo(this.rear.getElement()) > 0) {//caso seja para ser adicionado na cauda
                this.rear.setNext(newNode);
                newNode.setPrevious(this.rear);
                newNode.setNext(null);
                this.rear = this.rear.getPrevious();
            } else {
                DoubleNode<T> current = this.front;
                while (current != null && ((Comparable) element).compareTo(current.getElement()) > 0) {
                    current = current.getNext();
                }
                newNode.setNext(current.getNext());
                newNode.setPrevious(current);
                current.setNext(newNode);
                current.getNext().setPrevious(newNode);
            }

        }
        this.count++;
        this.modCount++;
    }
}