/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

/**
 * @author Rebeca
 */
public class Player implements Comparable<Player> {

    private String nome;
    private int pontos;
    private String mapa;
    private Date data;
    private int dificuldade;
    private Instant startTime;
    private float estimateTime;

    public Player(String nome, int pontos, String mapa, Date data, int dificuldade) {
        this.nome = nome;
        this.pontos = pontos;
        this.mapa = mapa;
        this.data = data;
        this.dificuldade = dificuldade;
    }

    public Player(String nome, int pontos, String mapa, Date data, int dificuldade, float estimateTime) {
        this.nome = nome;
        this.pontos = pontos;
        this.mapa = mapa;
        this.data = data;
        this.dificuldade = dificuldade;
        this.estimateTime = estimateTime;
    }

    public String getNome() {
        return nome;
    }

    public void setEstimateTime() {
        this.estimateTime = (float) (Duration.between(this.startTime, Instant.now())).toMillis() / 1000;
    }

    public void setStartTime() {
        this.startTime = Instant.now();
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

    public void damage(int pontos) {
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

    public Instant getStartTime() {
        return startTime;
    }

    public float getEstimateTime() {
        return estimateTime;
    }

    @Override
    public int compareTo(Player t) {
        if (this.getPontos() < t.getPontos()) {
            return 1;
        } else if (this.getPontos() > t.getPontos()) {
            return -1;
        } else {
            if (this.getEstimateTime() > t.getEstimateTime()) {
                return 1;
            } else if (this.getEstimateTime() < t.getEstimateTime()) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    @Override
    public String toString() {
        return "Utilizador: " + this.nome + ", " + this.pontos + " pontos" + ", dificuldade: " + this.dificuldade + ", " + "data: " + new SimpleDateFormat("dd-MM-yyyy HH:mm").format(data) + ", tempo: " + this.getEstimateTime();

    }
}
