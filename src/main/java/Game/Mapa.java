/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import DataStructure.Exceptions.ElementNotFoundException;
import DataStructure.list.UnorderedList.ArrayUnorderedList;
import DataStructure.list.UnorderedList.UnorderedListADT;

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

    public String getNome() {
        return nome;
    }

    public int getPontos() {
        return pontos;
    }

    public ArrayUnorderedList<Aposento> getAposentos() {
        return aposentos;
    }

    public Iterator<Aposento> getAposentosIterator(){
        return this.aposentos.iterator();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public void setAposentos(ArrayUnorderedList<Aposento> aposentos) {
        this.aposentos = aposentos;
    }

    public int getNumberAposentosSemFantasma() {
        int count = 0;
        for (Aposento ap : this.aposentos) {
            if (ap.getFantasma() > 0 && !ap.getNome().equals("entrada") && !ap.getNome().equals("exterior")) {
                count++;
            }
        }
        return count;
    }

    public int getMaxDamageFantasma(){
        int max = 0;
        for (Aposento ap : this.aposentos) {
            if (ap.getFantasma() > max) {
                max = ap.getFantasma();
            }
        }
        return max;
    }

    public Iterator<Aposento> getAposentosSemFantasmaIterator(){
        ArrayUnorderedList<Aposento> lista = new ArrayUnorderedList<>();
        for(Aposento ap: this.aposentos){
            if(ap.getFantasma() == 0 && !ap.getNome().equals("entrada") && !ap.getNome().equals("exterior")){
                lista.addToRear(ap);
            }
        }
        return lista.iterator();
    }


}
