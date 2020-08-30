/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructure.Graph.Network;

import DataStructure.Exceptions.EmptyCollectionException;
import DataStructure.Exceptions.NoPathAvailable;
import DataStructure.Graph.ArrayDirectedGraph;
import DataStructure.PriorityQueue.ArrayPriorityQueue;
import DataStructure.PriorityQueue.PriorityQueueNode;
import java.util.Iterator;

import DataStructure.list.UnorderedList.ArrayUnorderedList;
import DataStructure.list.UnorderedList.UnorderedListADT;

/**
 *
 * @author Rebeca
 */
public class ArrayDirectedNetwork<T> extends ArrayDirectedGraph<T> implements NetworkADT<T> {

    public ArrayDirectedNetwork() {
        super();
    }

    @Override
    public void addVertex(T vertex) {
        if (numVertices == vertices.length) {
            expandCapacity();
        }
        vertices[numVertices] = vertex;
        for (int i = 0; i <= numVertices; i++) {
            adjMatrix[numVertices][i] = Double.POSITIVE_INFINITY;
            adjMatrix[i][numVertices] = Double.POSITIVE_INFINITY;
        }
        numVertices++;
    }

    private void expandCapacity() {
        T[] newVertices = (T[]) (new Object[this.vertices.length * 2]);
        double[][] newAdjMatrix = new double[this.vertices.length * 2][this.vertices.length * 2];
        for (int i = 0; i < this.vertices.length; i++) {
            newVertices[i] = this.vertices[i];
        }
        for (int i = 0; i < this.adjMatrix.length; i++) { //linha
            for (int j = 0; j < this.adjMatrix[i].length; j++) {
                newAdjMatrix[i][j] = this.adjMatrix[i][j];
            }
        }
        this.vertices = newVertices;
        this.adjMatrix = newAdjMatrix;
    }

    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        addEdge(getIndex(vertex1), getIndex(vertex2), weight);
    }

    public void addEdge(int index1, int index2, double weight) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = weight;
        }
    }

    @Override
    public double shortestPathWeight(T vertex1, T vertex2) throws NoPathAvailable, EmptyCollectionException {
        int startIndex = getIndex(vertex1);
        int endIndex = getIndex(vertex2);
        ArrayPriorityQueue<Integer[]> traversalPriorityQueue = new ArrayPriorityQueue<Integer[]>();

        if (indexIsValid(startIndex) && indexIsValid(endIndex)) {

            boolean[] visited = new boolean[numVertices];
            //Inicializa o array com todos os vertices como não visitados
            for (int i = 0; i < numVertices; i++) {
                visited[i] = false;
            }

            Integer[] start = {-1, startIndex};
            double minCost = 0.0;
            traversalPriorityQueue.addElement(start, minCost); //adiciona o primeiro elemento
            PriorityQueueNode<Integer[]> pair = null;
            boolean found = false;

            while (!found && !traversalPriorityQueue.isEmpty()) {
                pair = traversalPriorityQueue.removeMin();

                if (pair.getElement()[1] == endIndex) {
                    found = true;
                } else {
                    double minCostPair = pair.getPriority();
                    visited[pair.getElement()[1]] = true;
                    /**
                     * Encontra todos os vertex adjacentes a x e que não foram
                     * visitados e adiciona-os à DataStructure.queue
                     */
                    for (int i = 0; i < numVertices; i++) {
                        if (adjMatrix[pair.getElement()[1]][i] != Double.POSITIVE_INFINITY && !visited[i]) {
                            double cost = adjMatrix[pair.getElement()[1]][i] + minCostPair;
                            Integer[] add = new Integer[2];
                            add[0] = pair.getElement()[1];
                            add[1] = i;
                            traversalPriorityQueue.addElement(add, cost);

                        }
                    }
                }
            }

            return pair.getPriority();
        }
        throw new NoPathAvailable();
    }

    public Iterator iteratorShortestPathWeight(T startVertex, T targetVertex) throws EmptyCollectionException, NoPathAvailable {
        int startIndex = getIndex(startVertex);
        int endIndex = getIndex(targetVertex);
        ArrayPriorityQueue<Integer[]> traversalPriorityQueue = new ArrayPriorityQueue<Integer[]>();
        UnorderedListADT<PathCostVertice> resultList = new ArrayUnorderedList<>();
        UnorderedListADT<PathCostVerticeWithElement<T>> path = new ArrayUnorderedList<>();

        if (indexIsValid(startIndex) && indexIsValid(endIndex)) {

            boolean[] visited = new boolean[numVertices];
            //Inicializa o array com todos os vertices como não visitados
            for (int i = 0; i < numVertices; i++) {
                visited[i] = false;
            }

            Integer[] start = {-1, startIndex};
            double minCost = 0.0;
            traversalPriorityQueue.addElement(start, minCost); //adiciona o primeiro elemento
            PriorityQueueNode<Integer[]> pair = null;
            boolean found = false;

            while (!found && !traversalPriorityQueue.isEmpty()) {
                pair = traversalPriorityQueue.removeMin();

                if (pair.getElement()[1] == endIndex) {
                    found = true;
                } else {
                    double minCostPair = pair.getPriority();
                    visited[pair.getElement()[1]] = true;
                    /**
                     * Encontra todos os vertex adjacentes a x e que não foram
                     * visitados e adiciona-os à DataStructure.queue
                     */
                    for (int i = 0; i < numVertices; i++) {
                        if (adjMatrix[pair.getElement()[1]][i] != Double.POSITIVE_INFINITY && !visited[i]) {
                            double cost = adjMatrix[pair.getElement()[1]][i] + minCostPair;
                            Integer[] add = new Integer[2];
                            add[0] = pair.getElement()[1];
                            add[1] = i;
                            traversalPriorityQueue.addElement(add, cost);

                            resultList.addToRear(new PathCostVertice(add, cost));
                        }
                    }
                }
            }

            int order = endIndex;
            PathCostVerticeWithElement elementToAdd = null;
            while (!resultList.isEmpty()) {
                PathCostVertice elementToOrder = resultList.removeLast();
                if (elementToOrder.getShortestElement()[1] == order) {
                    elementToAdd = new PathCostVerticeWithElement(this.vertices[elementToOrder.getShortestElement()[0]], this.vertices[elementToOrder.getShortestElement()[1]], elementToOrder.getCost());
                    path.addToFront(elementToAdd);
                    order = elementToOrder.getShortestElement()[0];
                }
            }

            PathCostVerticeWithElement first = new PathCostVerticeWithElement(null, this.vertices[start[1]], 0.0);
            path.addToFront(first);
            return path.iterator();
        }
        throw new NoPathAvailable();

    }

    public T getElementVertice(int index) {
        T vertice = null;
        if (index > -1 && index < this.numVertices) {
            vertice = this.vertices[index];
        }

        return vertice;
    }
}
