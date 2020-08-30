/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructure.list;

import DataStructure.Exceptions.EmptyCollectionException;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Rebeca
 */
public class ArrayList<T> implements ListADT<T> {

    protected final int DEFAULT_CAPACITY = 100;
    protected T[] list;
    protected int rear;
    protected int modCount;

    public ArrayList(int capacity) {
        this.list = (T[]) new Object[capacity];
        this.rear = this.modCount = 0;
    }

    public ArrayList() {
        this.list = (T[]) new Object[DEFAULT_CAPACITY];
        this.rear = this.modCount = 0;
    }

    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        T element = this.list[0];
        this.list[0] = null;
        for (int i = 0; i < this.rear; i++) { //fazer shift dos elementos
            this.list[i] = this.list[i + 1];
        }
        this.rear--;
        this.modCount++;
        return element;
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        this.rear--;
        T element = this.list[this.rear];
        this.list[this.rear] = null;
        this.modCount++;
        return element;
    }

    @Override
    public T remove(T element) throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }

        if (element == this.list[0]) {
            return this.removeFirst();
        }

        if (element == this.list[this.rear]) {
            return this.removeLast();
        }

        for (int i = 0; i < this.rear; i++) {
            if (list[i] == element) {
                for (int j = i; j < this.rear; j++) {
                    this.list[j] = this.list[j + 1];
                }
            }
        }
        this.rear--;
        this.modCount++;
        return element;
    }

    @Override
    public T first() {
        return this.list[0];
    }

    @Override
    public T last() {
        return this.list[this.rear - 1];
    }

    @Override
    public boolean contains(T target) {
        boolean found = false;
        int i = 0;
        while (!found && i < this.list.length) {
            if (target.equals(this.list[i])) {
                found = true;
            }
            i++;
        }
        return found;
    }

    @Override
    public boolean isEmpty() {
        return this.rear == 0;
    }

    @Override
    public int size() {
        return this.rear;
    }

    @Override
    public String toString() {
        String list = "\n";

        for (int i = 0; i < this.rear; i++) {
            list = list + "Posição " + i + ": " + this.list[i] + "\n";
        }

        return list;
    }

    @Override
    public Iterator<T> iterator() {
        return new BasicIterator();
    }

    private class BasicIterator implements Iterator {

        private boolean okToRemove;
        private int expectedModCount;
        private int current;

        public BasicIterator() {
            this.okToRemove = false;
            this.expectedModCount = modCount;
            this.current = 0;
        }

        @Override
        public boolean hasNext() {
            return this.current != size();
        }

        @Override
        public T next() {
            if (this.expectedModCount != modCount) {
                throw new java.util.ConcurrentModificationException();
            }

            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            this.current++;
            this.okToRemove = true;
            return (T) list[this.current - 1];
        }

        @Override
        public void remove() {
            if (this.expectedModCount != modCount) {
                throw new java.util.ConcurrentModificationException();
            }

            if (!this.okToRemove) {
                throw new IllegalStateException();
            }

            try {
                this.current--;
                ArrayList.this.remove(ArrayList.this.list[this.current]);
                this.okToRemove = false;

            } catch (EmptyCollectionException ex) {
                Logger.getLogger(ArrayList.class.getName()).log(Level.SEVERE, null, ex);
            }

            this.expectedModCount = modCount;
        }
    }

}
