/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author Rebeca
 */
public class ElementNotFoundException extends Exception {
    /**
     * Método construtor da exceção
     */
    public ElementNotFoundException() {
    }

    /**
     * Método responsável por devolver uma mensagem quando a exceção for lançada
     *
     * @param message Mensagem da exceção
     */
    public ElementNotFoundException(String message) {
        super(message);
    }
}
