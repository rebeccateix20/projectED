package DataStructure.Graph.Network;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rebeca
 */
public class PathCostVertice {

    private Integer[] shortestElement;// Indices dos elementos do grafo
    private double cost;// peso do caminho

    public PathCostVertice(Integer[] shortestElement, double cost) {
        this.shortestElement = shortestElement;
        this.cost = cost;
    }

    public Integer[] getShortestElement() {
        return shortestElement;
    }

  
    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "<" + this.shortestElement[0] + "," + this.shortestElement[1] + "," + this.cost + ">";
    }
}
