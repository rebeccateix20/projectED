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
    private int dificuldade;
    private Instant startTime;
    private float estimateTime;


    public Player(String nome, int dificuldade) {
        this.nome = nome;
        this.dificuldade = dificuldade;
    }

    public Player(String nome, int pontos, float estimateTime, int dificuldade) {
        this.nome = nome;
        this.pontos = pontos;
        this.estimateTime = estimateTime;
        this.dificuldade = dificuldade;
    }

    public void setDificuldade(int dificuldade) {
        this.dificuldade = dificuldade;
    }

    public int getDificuldade() {
        return dificuldade;
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
        return this.pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public void damage(int pontos) {
        this.pontos -= pontos;
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
        return "Player: " + this.nome + ", pontos: " + this.pontos + ", dificuldade: " + this.dificuldade + ", tempo: " + this.getEstimateTime();

    }
}
