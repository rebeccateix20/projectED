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
public class ArrayQueue<T> implements QueueADT<T> {

    private final int DEFAULT_CAPACITY = 100;
    private int front, rear;
    private T[] queue;

    public ArrayQueue() {
        this.front = 0;
        this.rear = 0;
        this.queue = (T[]) (new Object[this.DEFAULT_CAPACITY]);
    }

    public ArrayQueue(int capacity) {
        this.front = 0;
        this.rear = 0;
        this.queue = (T[]) (new Object[capacity]);
    }

    @Override
    public void enqueue(T element) {
        if (this.size() == this.queue.length) {
            this.expandCapacity();
        }
        queue[rear] = element;
        this.rear++;

    }

    @Override
    public T dequeue() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        T temp = queue[this.front];
        for (int i = 0; i < this.rear; i++) {
            queue[i] = queue[i + 1];
        }
        this.queue[this.rear] = null;
        this.rear--;
        return temp;
    }

    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        return this.queue[front];
    }

    @Override
    public boolean isEmpty() {
        return this.rear == 0;
    }

    @Override
    public int size() {
        return this.rear;
    }

    private void expandCapacity() {
        T[] newQueue = (T[]) (new Object[this.queue.length * 2]);
        for (int i = 0; i < this.queue.length; i++) {
            newQueue[i] = this.queue[i];
        }
        this.queue = newQueue;
    }

    @Override
    public String toString() {
        String list = "";

        for (int i = 0; i < this.rear; i++) {
            list = list + "Posição " + i + ": " + this.queue[i] + "\n";
        }

        return list;
    }

}
