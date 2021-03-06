/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructure.Graph.Network;

import DataStructure.Exceptions.EmptyCollectionException;
import DataStructure.Exceptions.NoPathAvailable;
import DataStructure.Graph.GraphADT;

/**
 * @author Rebeca
 */
public interface NetworkADT<T> extends GraphADT<T> {
    /**
     * insere uma aresta entre dois vertices com o peso
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @param weight  the weight
     */
    public void addEdge(T vertex1, T vertex2, double weight);

    /**
     * retorna o peso do caminho mais barato
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return the weight of the shortest path in this network
     */
    public double shortestPathWeight(T vertex1, T vertex2) throws NoPathAvailable, EmptyCollectionException;
}
