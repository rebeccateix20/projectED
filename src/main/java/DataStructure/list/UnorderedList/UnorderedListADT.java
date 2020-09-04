/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructure.list.UnorderedList;

import DataStructure.Exceptions.ElementNotFoundException;
import DataStructure.list.ListADT;

/**
 * @author Rebeca
 */
public interface UnorderedListADT<T> extends ListADT<T> {

    /**
     * Adiciona um elemento ao inicio da lista
     * @param element elemento a adicionar
     */
    public void addToFront(T element);

    /**
     * Adiciona um elemento ao fim da lista
     * @param element elemento a adicionar
     */
    public void addToRear(T element);

    /**
     * Adiciona um elemento depois de outro elemento
     * @param element elemento a adicionar
     * @param target elemento que vai ser o anterior ao elemento a ser adicionado
     * @throws ElementNotFoundException
     */
    public void addAfter(T element, T target) throws ElementNotFoundException;
}
