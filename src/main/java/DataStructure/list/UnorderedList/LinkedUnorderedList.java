/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructure.list.UnorderedList;

import DataStructure.Exceptions.ElementNotFoundException;
import DataStructure.list.LinearNode;
import DataStructure.list.LinkedList;

/**
 * @author Rebeca
 */
public class LinkedUnorderedList<T> extends LinkedList<T> implements UnorderedListADT<T> {

    public LinkedUnorderedList() {
        super();
    }

    @Override
    public void addToFront(T element) {
        LinearNode<T> newNode = new LinearNode(element);
        if (this.isEmpty()) {
            this.front = this.rear = newNode;
        } else {
            newNode.setNext(this.front);
            this.front = newNode;
        }
        this.count++;
        this.modCount++;

    }

    @Override
    public void addToRear(T element) {
        LinearNode<T> newNode = new LinearNode(element);

        if (this.isEmpty()) {
            this.front = this.rear = newNode;
        } else {
            this.rear.setNext(newNode);
            newNode.setNext(null);
            this.rear = this.rear.getNext();
            this.count++;
            this.modCount++;
        }

    }

    @Override
    public void addAfter(T element, T target) throws ElementNotFoundException {
        LinearNode<T> newNode = new LinearNode(element);
        LinearNode<T> previous = null;
        if (this.isEmpty()) {
            this.front = this.rear = newNode;
        } else {
            LinearNode<T> current = this.front;
            boolean found = false;
            while (current != null && !found) {
                if (target.equals(current.getElement())) {
                    found = true;
                }
                previous = current;
                current = current.getNext();
            }
            if (!found) {
                throw new ElementNotFoundException();
            }
            newNode.setNext(current);
            previous.setNext(newNode);
        }
        this.count++;
        this.modCount++;
    }

}
