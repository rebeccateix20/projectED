/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructure.stack;

import DataStructure.Exceptions.EmptyCollectionException;

/**
 * @author Rebeca
 */
public class ArrayStack<T> implements StackADT<T> {

    /**
     * constant to represent the default capacity of the array
     */
    private final int DEFAULT_CAPACITY = 100;
    /**
     * int that represents both the number of elements and the next available
     * position in the array
     */
    private int top;
    /**
     * array of generic elements to represent the DataStructure.stack
     */
    private T[] stack;

    /**
     * Creates an empty DataStructure.stack using the default capacity.
     */
    public ArrayStack() {
        top = 0;
        stack = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    /**
     * Creates an empty DataStructure.stack using the specified capacity.
     *
     * @param initialCapacity represents the specified capacity
     */
    public ArrayStack(int initialCapacity) {
        top = 0;
        stack = (T[]) (new Object[initialCapacity]);
    }

    /**
     * Adds the specified element to the top of this DataStructure.stack, expanding the
     * capacity of the DataStructure.stack array if necessary.
     *
     * @param element generic element to be pushed onto DataStructure.stack
     */
    @Override
    public void push(T element) {

        if (size() == stack.length) {
            expandCapacity();
        }
        stack[top] = element;
        top++;
    }

    /**
     * Removes the element at the top of this DataStructure.stack and returns a reference to
     * it. Throws an EmptyCollectionException if the DataStructure.stack is empty.
     *
     * @return T element removed from top of DataStructure.stack
     * @throws EmptyCollectionException if a pop is attempted on empty DataStructure.stack
     */
    @Override
    public T pop() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Stack");
        }
        top--;
        T result = stack[top];
        stack[top] = null;
        return result;
    }

    /**
     * Returns a reference to the element at the top of this DataStructure.stack. The element
     * is not removed from the DataStructure.stack. Throws an EmptyCollectionException if the
     * DataStructure.stack is empty.
     *
     * @return T element on top of DataStructure.stack
     * @throws EmptyCollectionException if a peek is attempted on empty DataStructure.stack
     */
    @Override
    public T peek() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Stack");
        }
        return stack[top - 1];
    }

    @Override
    public boolean isEmpty() {
        return this.stack.length == 0;
    }

    @Override
    public int size() {
        return this.top;
    }

    private void expandCapacity() {
        T[] newStack = (T[]) (new Object[this.stack.length * 2]);
        for (int i = 0; i < this.stack.length; i++) {
            newStack[i] = this.stack[i];
        }
        this.stack = newStack;
    }

    public void printStack() {
        for (int i = 0; i < this.top; i++) {
            System.out.println(this.stack[i]);
        }
    }

}
