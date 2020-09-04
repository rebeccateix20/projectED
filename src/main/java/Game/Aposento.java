/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import DataStructure.list.UnorderedList.ArrayUnorderedList;

import java.util.Iterator;

/**
 * @author Rebeca
 */
public class Aposento {

    private String nome;
    private Integer[] fantasmas;
    private int costTotal;
    private ArrayUnorderedList<String> ligacoes;
    private boolean teletransporte;

    /**
     * Método construtor
     * @param nome
     * @param fantasmas
     * @param ligacoes
     * @param costTotal
     */
    public Aposento(String nome, Integer[] fantasmas, ArrayUnorderedList<String> ligacoes, int costTotal) {
        this.nome = nome;
        this.fantasmas = fantasmas;
        this.ligacoes = ligacoes;
        this.costTotal = costTotal;
    }

    /**
     * Devolve o custo total de fantasmas neste aposento
     * @return custo total fantasmas no aposento
     */
    public int getCostTotal() {
        return costTotal;
    }

    /**
     * define o custo total de fantasmas neste aposento
     * @param costTotal
     */
    public void setCostTotal(int costTotal) {
        this.costTotal += costTotal;
    }

    /**
     * Define o teletransporte para eeste aposento
     * @param teletransporte
     */
    public void setTeletransporte(boolean teletransporte) {
        this.teletransporte = teletransporte;
    }

    /**
     * Devolve se o aposento é tem o bonus de teletransporte
     * @return
     */
    public boolean isTeletransporte() {
        return teletransporte;
    }

    public String getNome() {
        return nome;
    }

    /**
     * Metodo que devolve a lista de fantasmas do aposento
     * @return lista de fantasmas
     */
    public Integer[] getFantasmas() {
        return fantasmas;
    }


    /**
     * Método responsavel por devolver uo valor do fantasma num determinado indice
     * @param index indice do fantasma
     * @return valor do fantasma
     */
    public int getFantasma(int index) {
        return this.fantasmas[index];
    }

    /**
     * Devolde uma lista das ligacoes do aposento
     * @return lista de ligacoes do aposento
     */
    public ArrayUnorderedList<String> getLigacoes() {
        return ligacoes;
    }

    /**
     * Metodo que devolve iterador das ligacoes do aposento
     * @return iterador das ligacoes do aposento
     */
    public Iterator<String> getLigacoesIterator() {
        return this.ligacoes.iterator();
    }

    /**
     * Método responsável por atribuit um valor a um determinado fantasma
     * @param index
     * @param value
     */
    public void setFantasma(int index, int value) {
        this.fantasmas[index] = value;
    }

    /**
     * Método responsável por dar shift dos fantasmas quando estes são movidos para outro aposento
     */
    public void shiftFantasmas() {
        for (int i = 0; i < this.fantasmas.length-1; i++) {
            this.fantasmas[i] = this.fantasmas[i+1];
        }
        this.fantasmas[this.fantasmas.length-1] = 0;
    }

    /**
     * Metodo que devolve o numero de fantasmas no aposento
     * @return numero de fantasmas
     */
    public int getNFantasmas(){
        int count = 0;
        for(int i=0; i<this.fantasmas.length; i++){
            if(this.fantasmas[i] > 0){
                count++;
            }
        }
        return count;
    }

}
