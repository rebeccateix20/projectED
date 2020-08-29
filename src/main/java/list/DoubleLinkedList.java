/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package list;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rebeca
 */
public class DoubleLinkedList<T> implements ListADT<T> {

    protected DoubleNode<T> rear;
    protected DoubleNode<T> front;
    protected int count, modCount;

    public DoubleLinkedList() {
        this.rear = null;
        this.front = null;
        this.modCount = 0;
        this.count = 0;
    }

    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        T element = this.front.getElement();
        this.front = this.front.getNext();
        this.front.setPrevious(null);
        this.modCount++;
        this.count--;
        return element;
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        T element = this.rear.getElement();
        this.rear = this.rear.getPrevious();
        this.rear.setNext(null);
        this.modCount++;
        this.count--;
        return element;
    }

    @Override
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }

        boolean found = false;
        DoubleNode<T> current = this.front;
        DoubleNode<T> previous = null;
        while (current != null && !found) {
            if (element.equals(current.getElement())) {
                found = true;
            } else {
                previous = current;
                current = current.getNext();
            }
        }

        if (!found) {
            throw new ElementNotFoundException();
        }

        if (count == 1) {
            this.front = this.rear = null;
        } else if (current.equals(this.front)) {
            this.front = current.getNext();
        } else if (current.equals(this.rear)) {
            this.rear = previous;
            this.rear.setNext(null);
        } else {
            previous.setNext(current.getNext());
        }
        count--;
        return current.getElement();
    }

    @Override
    public T first() {
        return this.front.getElement();
    }

    @Override
    public T last() {
        return this.rear.getElement();
    }

    @Override
    public boolean contains(T target) {
        boolean found = false;
        DoubleNode<T> current = this.front;
        while (current != null && !found) {
            if (target.equals(current.getElement())) {
                found = true;
            } else {
                current = current.getNext();
            }
        }
        return found;
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
        String lista = "";
        DoubleNode<T> current = this.front;
        int i = 0;

        while (current != null) {
            lista = lista + "Posição " + i + ": " + current.getElement().toString() + "\n";
            current = current.getNext();
            i++;
        }

        return lista;
    }

    @Override
    public Iterator<T> iterator() {
        return new BasicIterator();
    }

    private class BasicIterator implements Iterator {

        private boolean okToRemove;
        private int expectedModCount;
        private DoubleNode<T> current;

        public BasicIterator() {
            this.okToRemove = false;
            this.expectedModCount = modCount;
            this.current = front;
        }

        @Override
        public boolean hasNext() {
            return this.current != null;
        }

        @Override
        public T next() {
            if (this.expectedModCount != modCount) {
                throw new java.util.ConcurrentModificationException();
            }

            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            this.current = this.current.getNext();
            this.okToRemove = true;
            if (this.current != null) {
                return this.current.getPrevious().getElement();
            } else {
                return rear.getElement();
            }
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
                T element;
                if (this.current != null) {
                    element = this.current.getPrevious().getElement();
                } else {
                    element = rear.getElement();
                }
                DoubleLinkedList.this.remove(element);
                this.okToRemove = false;

            } catch (EmptyCollectionException ex) {
                Logger.getLogger(ArrayList.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ElementNotFoundException ex) {
                Logger.getLogger(DoubleLinkedList.class.getName()).log(Level.SEVERE, null, ex);
            }

            this.expectedModCount = modCount;
        }
    }
}
