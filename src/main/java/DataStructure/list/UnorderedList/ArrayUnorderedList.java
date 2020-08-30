/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructure.list.UnorderedList;

import DataStructure.list.ArrayList;
import DataStructure.Exceptions.ElementNotFoundException;

/**
 * @author Rebeca
 */
public class ArrayUnorderedList<T> extends ArrayList<T> implements UnorderedListADT<T> {

    public ArrayUnorderedList() {
        super();
    }

    @Override
    public void addToFront(T element) {
        if (this.list.length == this.rear) {
            expandCapacity();
        }
        int i = 0;
        int rearAdd = this.rear;

        while (rearAdd > i) { //está a fazer um shift para a frente, ou seja, a abrir espaço para o elemento
            this.list[rearAdd] = this.list[rearAdd - 1];
            rearAdd--;
        }
        this.list[0] = element;

        this.modCount++;
        this.rear++;
    }

    @Override
    public void addToRear(T element) {
        if (this.list.length == this.rear) {
            expandCapacity();
        }
        this.list[this.rear] = element;
        this.rear++;
        this.modCount++;
    }

    @Override
    public void addAfter(T element, T target) throws ElementNotFoundException {
        if (this.list.length == this.rear) {
            expandCapacity();
        }
        int i = 0;
        boolean found = false;
        while (i < this.rear && !found) {
            if (target.equals(this.list[i])) {
                found = true;
            }
            i++;
        }
        if (!found) {
            throw new ElementNotFoundException();
        }

        int rearAdd = this.rear;

        while (rearAdd > i) { //está a fazer um shift para a frente, ou seja, a abrir espaço para o elemento
            this.list[rearAdd] = this.list[rearAdd - 1];
            rearAdd--;
        }

        this.list[i] = element;

        this.rear++;

        this.modCount++;

    }

    private void expandCapacity() {
        T[] expandList = (T[]) (new Object[(this.list.length * 2)]);

        for (int i = 0; i < this.list.length; i++) {
            expandList[i] = this.list[i];
        }

        this.list = expandList;
    }

}
