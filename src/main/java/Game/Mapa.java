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
public class Mapa {
    private String nome;
    private int pontos;
    private ArrayUnorderedList<Aposento> aposentos;

    public Mapa(String nome, int pontos, ArrayUnorderedList<Aposento> aposentos) {
        this.nome = nome;
        this.pontos = pontos;
        this.aposentos = aposentos;
    }

    public Mapa(int pontos, ArrayUnorderedList<Aposento> aposentos) {
        this.pontos = pontos;
        this.aposentos = aposentos;

    }

    public String getNome() {
        return nome;
    }

    public int getPontos() {
        return pontos;
    }

    /**
     * Metodo que devolve a lista dos aposentos
     * @return
     */
    public ArrayUnorderedList<Aposento> getAposentos() {
        return aposentos;
    }


    /**
     * Devolve iterador dos aposentos
     * @return
     */
    public Iterator<Aposento> getAposentosIterator() {
        return this.aposentos.iterator();
    }


    /**
     * Metodo que conta quando aposentos ha sem fantasma
     * @return contador de aposentos sem fantasma
     */
    public int getNumberAposentosSemFantasma() {
        int count = 0;
        for(Aposento ap : this.aposentos){
            if(ap.getCostTotal() <= 0 && !ap.getNome().equals("entrada") && !ap.getNome().equals("exterior")){
                count++;
            }
        }
        return count;
    }

    /**
     * Metodo que encontra o dano maximo que os fantasmas no jogo podem tirar
     * @return dano maximo dos fantasmas
     */
    public int getMaxDamageFantasma() {
        int max = 0;
        for (Aposento ap : this.aposentos) {
            for (int i = 0; i < ap.getFantasmas().length; i++) {
                if (ap.getFantasma(i) > max) {
                    max = ap.getFantasma(i);
                }
            }
        }
        return max;
    }

    /**
     * Metodo que devolve o iterador dos aposentos sem fantasma
     * @return iterador dos aposentos sem fantasma
     */
    public Iterator<Aposento> getAposentosSemFantasmaIterator() {
        ArrayUnorderedList<Aposento> lista = new ArrayUnorderedList<>();
        int custo;
        for (Aposento ap : this.aposentos) {
            custo = 0;
            for (int i = 0; i < ap.getFantasmas().length; i++) {
                custo += ap.getFantasma(i);
            }

            if (custo == 0 && !ap.getNome().equals("entrada") && !ap.getNome().equals("exterior")) {
                lista.addToRear(ap);
            }

        }
        return lista.iterator();
    }

    /**
     * Metodo que devolve o numero de aposentos do mapa
     * @return
     */
    public int getNAposentos(){
        return this.aposentos.size();
    }


}
