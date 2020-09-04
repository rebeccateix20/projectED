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

    public Aposento(String nome, Integer[] fantasmas, ArrayUnorderedList<String> ligacoes, int costTotal) {
        this.nome = nome;
        this.fantasmas = fantasmas;
        this.ligacoes = ligacoes;
        this.costTotal = costTotal;
    }

    public int getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(int costTotal) {
        this.costTotal += costTotal;
    }

    public void setTeletransporte(boolean teletransporte) {
        this.teletransporte = teletransporte;
    }

    public boolean isTeletransporte() {
        return teletransporte;
    }

    public String getNome() {
        return nome;
    }

    public Integer[] getFantasmas() {
        return fantasmas;
    }

    public int getFantasma(int index) {
        return this.fantasmas[index];
    }

    public ArrayUnorderedList<String> getLigacoes() {
        return ligacoes;
    }

    public Iterator<String> getLigacoesIterator() {
        return this.ligacoes.iterator();
    }

    public void setFantasma(int index, int value) {
        this.fantasmas[index] = value;
    }

    /*
    public int getCostFantasmas() {
        int cost = 0;
        for (int i = 0; i < fantasmas.length; i++) {
            cost += fantasmas[i];
        }
        return cost;
    }*/

    public void shiftFantasmas() {
        for (int i = 0; i < this.fantasmas.length-1; i++) {
            this.fantasmas[i] = this.fantasmas[i+1];
        }
        this.fantasmas[this.fantasmas.length-1] = 0;
    }

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
