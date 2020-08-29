/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package list.OrderedList;

import javax.activation.UnsupportedDataTypeException;
import list.ArrayList;

/**
 *
 * @author Rebeca
 */
public class ArrayOrderedList<T> extends ArrayList<T> implements OrderedListADT<T> {

    public ArrayOrderedList() {
        super();
    }
    

    @Override
    public void add(T element) throws UnsupportedDataTypeException{
        if (!(element instanceof Comparable)) {
            throw new UnsupportedDataTypeException();
        }
        if(this.list.length==size()){
            expandCapacity();
        }
        int i=0;
        /*
        CompareTo devolve: -1 caso menor
                            1 caso maior
                            0 caso igual
        */
        while (i < this.rear && ((Comparable) element).compareTo(this.list[i]) > 0) {
            i++;
        }
        
        int rearAdd = this.rear;
        
        while(rearAdd > i){ //está a fazer um shift para a frente, ou seja, a abrir espaço para o elemento
            this.list[rearAdd] = this.list[rearAdd-1];
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
