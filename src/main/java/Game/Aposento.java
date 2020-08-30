/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import DataStructure.list.UnorderedList.ArrayUnorderedList;

import java.util.Iterator;

/**
 *
 * @author Rebeca
 */
public class Aposento {

    private String nome;
    private int fantasma;
    private ArrayUnorderedList<String> ligacoes;

    public Aposento(String nome, int fantasma, ArrayUnorderedList<String> ligacoes) {
        this.nome = nome;
        this.fantasma = fantasma;
        this.ligacoes = ligacoes;
    }

    public String getNome() {
        return nome;
    }

    public int getFantasma() {
        return fantasma;
    }

    public ArrayUnorderedList<String> getLigacoes() {
        return ligacoes;
    }

    public Iterator<String> getLigacoesIterator(){
        return this.ligacoes.iterator();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setFantasma(int dificuldade) {
        this.fantasma *= dificuldade;
    }

    public void setLigacoes(ArrayUnorderedList<String> ligacoes) {
        this.ligacoes = ligacoes;
    }



}