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
public class LinkedQueue<T> implements QueueADT<T> {

    private LinearNode<T> front;
    private LinearNode<T> rear;
    private int count;

    public LinkedQueue() {
        this.front = null;
        this.rear = null;
        this.count = 0;
    }

    @Override
    public void enqueue(T element) { //adiciona elemento à rear
        LinearNode<T> newNode = new LinearNode(element);

        if (this.count == 0) {
            this.front = newNode;
        } else {
            this.rear.setNext(newNode);
            newNode.setNext(null);
        }
        this.rear = newNode;
        this.count++;
    }

    @Override
    public T dequeue() throws EmptyCollectionException { //retirar à front

        if (isEmpty()) {
            throw new EmptyCollectionException();
        }

        LinearNode<T> tempNode = this.front; //nó a ser retirado
        this.front = tempNode.getNext();
        tempNode.setNext(null);
        this.count--;
        return tempNode.getElement();
    }

    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        return this.front.getElement();
    }

    @Override
    public boolean isEmpty() {
        return this.count == 0;
    }

    @Override
    public int size() {
        return this.count;
    }

    @Override
    public String toString() {
        LinearNode<T> tempNode = this.front;
        String s = "";
        while (tempNode != null) {
            s += tempNode.toString() + "\n";
            //System.out.println(tempNode.toString());
            tempNode = tempNode.getNext();
        }
        return s;
    }
}
