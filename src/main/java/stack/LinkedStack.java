/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stack;

import Exceptions.EmptyCollectionException;

/**
 *
 * @author Rebeca
 */
public class LinkedStack<T> implements StackADT<T> {

    private LinearNode<T> top;
    private int count;

    public LinkedStack() {
        this.top = null;
        this.count = 0;
    }

    @Override
    public void push(T element) {
        LinearNode<T> newNode = new LinearNode(element);
        LinearNode<T> oldNode = this.top;

        if (count == 0) {
            this.top = newNode;
        } else {
            newNode.setNext(oldNode);
            this.top = newNode;
        }
        this.count++;

    }

    /**
     * ISTO REMOVE À CABEÇA PORQUE LIFO
     *
     * @return
     * @throws EmptyCollectionException
     */
    @Override
    public T pop() throws EmptyCollectionException {
        LinearNode<T> node = this.top;

        this.top = node.getNext();
        node.setNext(null);
        count--;
        return node.getElement();
    }

    @Override
    public T peek() throws EmptyCollectionException {
        return this.top.getElement();
    }

    @Override
    public boolean isEmpty() {
        return this.count==0;
    }

    @Override
    public int size() {
        return this.count;
    }
    
    /*
    Print da stack
    */
    public void printLinkedStack(){
        LinearNode<T> tempNode = this.top;
        while(tempNode != null){
            System.out.println(tempNode.toString());
            tempNode = tempNode.getNext();
        }
    }

}
