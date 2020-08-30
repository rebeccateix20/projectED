/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructure.list;

/**
 *
 * @author Rebeca
 */
public class LinearNode<T> {

    private T element;
    private LinearNode<T> next;

    public LinearNode() {
        this.element = null;
        this.next = null;
    }

    public LinearNode(T element) {
        this.element = element;
        this.next = null;
    }

    public T getElement() {
        return element;
    }

    public LinearNode getNext() {
        return next;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public void setNext(LinearNode<T> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "" + element + "";
    }

}
