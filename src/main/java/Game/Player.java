/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.util.Date;

/**
 *
 * @author Rebeca
 */
public class Player implements Comparable<Player> {

    private String nome;
    private int pontos;
    private String mapa;
    private Date data;
    private int dificuldade;

    public Player(String nome, int pontos, String mapa, Date data, int dificuldade) {
        this.nome = nome;
        this.pontos = pontos;
        this.mapa = mapa;
        this.data = data;
        this.dificuldade = dificuldade;
    }

    public String getNome() {
        return nome;
    }

    public int getPontos() {
        return pontos;
    }

    public String getMapa() {
        return mapa;
    }

    public Date getData() {
        return data;
    }

    public int getDificuldade() {
        return dificuldade;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public void damage(int pontos){
        this.pontos -= pontos;
    }

    public void setMapa(String mapa) {
        this.mapa = mapa;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setDificuldade(int dificuldade) {
        this.dificuldade = dificuldade;
    }

    @Override
    public int compareTo(Player t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
