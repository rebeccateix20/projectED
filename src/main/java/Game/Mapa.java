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

    public ArrayUnorderedList<Aposento> getAposentos() {
        return aposentos;
    }

    public Iterator<Aposento> getAposentosIterator() {
        return this.aposentos.iterator();
    }

    public int getNumberAposentosSemFantasma() {
        int count = 0;
        for(Aposento ap : this.aposentos){
            if(ap.getCostTotal() <= 0 && !ap.getNome().equals("entrada") && !ap.getNome().equals("exterior")){
                count++;
            }
        }
        return count;
    }

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

    public int getNAposentos(){
        return this.aposentos.size();
    }


}
