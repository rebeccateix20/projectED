/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructure.Graph;

import DataStructure.Exceptions.EmptyCollectionException;

import java.util.Iterator;

/**
 * @author Rebeca
 */
public interface GraphADT<T> {

    /**
     * Adiciona um vertice
     *
     * @param vertex o vertice a ser adicionado
     */
    public void addVertex(T vertex);

    /**
     * remove um vertice
     *
     * @param vertex vertice a ser removido
     */
    public void removeVertex(T vertex) throws EmptyCollectionException;

    /**
     * insere uma aresta entre dois vertices
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     */
    public void addEdge(T vertex1, T vertex2);

    /**
     * remove uma aresta entre dois vertices
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     */
    public void removeEdge(T vertex1, T vertex2) throws EmptyCollectionException;

    /**
     * retorn o iterador da travessia em largura começando num vertice
     *
     * @param startVertex the starting vertex
     * @return a breadth first iterator beginning at the given vertex
     */
    public Iterator iteratorBFS(T startVertex) throws EmptyCollectionException;

    /**
     * retorna o iterador da travessia em profundidado começando num vertice
     *
     * @param startVertex the starting vertex
     * @return a depth first iterator starting at the given vertex
     */
    public Iterator iteratorDFS(T startVertex) throws EmptyCollectionException;

    /**
     * retorna um iterador do menor caminho tendo em conta dois vertices
     *
     * @param startVertex  the starting vertex
     * @param targetVertex the ending vertex
     * @return an iterator that contains the shortest path between the two
     * vertices
     */
    public Iterator iteratorShortestPath(T startVertex, T targetVertex) throws EmptyCollectionException;

    /**
     * retorna verdadeiro se estiver vazia, falso en contrario
     *
     * @return true if this graph is empty
     */
    public boolean isEmpty();

    /**
     * retorna verdadeiro se for conexo, falso em contrario
     *
     * @return true if this graph is connected
     */
    public boolean isConnected() throws EmptyCollectionException;

    /**
     * retorna o numero de vertices do grafo
     *
     * @return the integer number of vertices in this graph
     */
    public int size();

    /**
     * retorna uma representacao da matriz de adjacencias
     */
    public void printAdjacencyMatrix();


}
