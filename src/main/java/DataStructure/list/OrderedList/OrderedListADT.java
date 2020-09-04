/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructure.list.OrderedList;

import javax.activation.UnsupportedDataTypeException;

import DataStructure.list.ListADT;

/**
 * @author Rebeca
 */
public interface OrderedListADT<T> extends ListADT<T> {
    /**
     * adiciona o elemento na posicao adequada
     *
     * @param element the element to be added to this DataStructure.list
     */
    public void add(T element) throws UnsupportedDataTypeException;
}
