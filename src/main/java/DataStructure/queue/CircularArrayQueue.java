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
public class CircularArrayQueue<T> implements QueueADT<T> {

    private T[] circularArray;
    private int front;
    private int rear;
    private int count;
    private final int DEFAULT_CAPACITY = 100;

    public CircularArrayQueue() {
        this.circularArray = (T[]) (new Object[DEFAULT_CAPACITY]);
        this.front = 0;
        this.rear = 0;
        this.count = 0;
    }

    public CircularArrayQueue(int capacity) {
        this.circularArray = (T[]) (new Object[capacity]);
        this.front = 0;
        this.rear = 0;
        this.count = 0;
    }

    @Override
    public void enqueue(T element) {
        if (size() == this.circularArray.length) {
            expandCapacity();
        }
        this.circularArray[this.rear] = element;

        this.rear = (this.rear + 1) % this.circularArray.length;
        this.count++;
    }

    @Override
    public T dequeue() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        T remover = this.circularArray[this.front];
        this.circularArray[this.front] = null;
        this.front = (this.front + 1) % this.circularArray.length;
        this.count--;
        return remover;

    }

    @Override
    public T first() {
        return this.circularArray[this.front];
    }

    @Override
    public boolean isEmpty() {
        return this.count == 0;
    }

    @Override
    public int size() {
        return this.count;
    }

    private void expandCapacity() {
        T[] newCircular = (T[]) (new Object[(this.circularArray.length * 2)]);
        int count4newCircular = 0;
        /*o circular array que precisamos de aumentar capacity pode ter nulls pelo meio, por isso é preciso
        verificar e ir adicionando ao novo circular com a capacidade dobrada*/
        for (int i = this.front; i < this.circularArray.length; i++) {
            if (this.circularArray[i] != null) {
                newCircular[count4newCircular] = this.circularArray[i];
                count4newCircular++;
            }
        }
        if (count4newCircular != this.size()) { //o suposto é ter o mesmo número de elementos
            for (int i = 0; i < this.rear; i++) { //este for é caso o front esteja no 50, ele só faz do 50 ao 100, não fazendo os primeiros 50
                newCircular[count4newCircular] = this.circularArray[i];
                count4newCircular++;
            }
        }
        this.front = 0;
        this.rear = this.count;
        this.circularArray = newCircular;
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < this.circularArray.length; i++) {
            if (this.circularArray[i] != null) {
                s += this.circularArray[i] + "\n";
            }

        }
        return s;
    }

}
