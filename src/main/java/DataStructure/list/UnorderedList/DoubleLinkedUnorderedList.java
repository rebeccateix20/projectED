/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructure.list.UnorderedList;

import DataStructure.list.DoubleLinkedList;
import DataStructure.list.DoubleNode;
import DataStructure.Exceptions.ElementNotFoundException;

/**
 *
 * @author Rebeca
 */
public class DoubleLinkedUnorderedList<T> extends DoubleLinkedList<T> implements UnorderedListADT<T> {

    public DoubleLinkedUnorderedList() {
        super();
    }

    @Override
    public void addToFront(T element) {
        DoubleNode<T> newNode = new DoubleNode(element);
        if (this.isEmpty()) {
            this.front = this.rear = newNode;
        } else {
            newNode.setNext(this.front);
            newNode.setPrevious(null);
            this.front.setPrevious(newNode);
            this.front = this.front.getPrevious();
        }
        this.count++;
        this.modCount++;
    }

    @Override
    public void addToRear(T element) {
        DoubleNode<T> newNode = new DoubleNode(element);
        if (this.isEmpty()) {
            this.front = this.rear = newNode;
        } else {
            this.rear.setNext(newNode);
            newNode.setPrevious(this.rear);
            newNode.setNext(null);
            this.rear = this.rear.getPrevious();
        }
        this.count++;
        this.modCount++;
    }

    @Override
    public void addAfter(T element, T target) throws ElementNotFoundException {

        DoubleNode<T> newNode = new DoubleNode(element);
        if (this.isEmpty()) {
            this.front = this.rear = newNode;
        } else {
            DoubleNode<T> current = this.front;
            boolean found = false;
            while (current != null && !found) {
                if (target.equals(current.getElement())) {
                    found = true;
                }
                current = current.getNext();
            }
            if (!found) {
                throw new ElementNotFoundException();
            }
            newNode.setNext(this.front.getNext());
            newNode.setPrevious(current);
            this.front.setNext(newNode);
            this.front.getNext().setPrevious(newNode);
        }
        this.count++;
        this.modCount++;
    }

}
