/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructure.Graph;

import DataStructure.Exceptions.EmptyCollectionException;
import java.util.Iterator;
import DataStructure.list.UnorderedList.ArrayUnorderedList;
import DataStructure.list.UnorderedList.UnorderedListADT;
import DataStructure.queue.ArrayQueue;
import DataStructure.queue.LinkedQueue;
import DataStructure.queue.QueueADT;
import DataStructure.stack.LinkedStack;

/**
 *
 * @author Rebeca
 */
public class ArrayNotDirectedGraph<T> implements GraphADT<T> {

    protected final int DEFAULT_CAPACITY = 10;
    protected int numVertices; // number of vertices in the graph
    protected double[][] adjMatrix; // adjacency matrix
    protected T[] vertices; // values of vertices

    public ArrayNotDirectedGraph() {
        numVertices = 0;
        this.adjMatrix = new double[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    @Override
    public void addVertex(T vertex) {
        if (numVertices == vertices.length) {
            expandCapacity();
        }
        vertices[numVertices] = vertex;
        for (int i = 0; i <= numVertices; i++) {
            adjMatrix[numVertices][i] = 0.0;
            adjMatrix[i][numVertices] = 0.0;
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
    public void removeVertex(T vertex) throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        int indexRemove = getIndex(vertex);
        if (indexIsValid(indexRemove)) {
            for (int i = indexRemove; i < this.numVertices; i++) {
                this.vertices[i] = this.vertices[i + 1];
            }
            this.numVertices--;
            //fazer shift das colunas em cada linha
            for (int i = 0; i < this.numVertices; i++) {
                for (int j = indexRemove; j < this.numVertices; j++) {
                    this.adjMatrix[i][j] = this.adjMatrix[i][j + 1];
                }
            }

            //fazer shift das linhas abaixo da do elemento removido
            for (int i = indexRemove; i < this.numVertices; i++) {
                for (int j = 0; j < this.numVertices; j++) {
                    this.adjMatrix[i][j] = this.adjMatrix[i + 1][j];
                }
            }

            for (int i = indexRemove; i < this.numVertices + 1; i++) {
                for (int j = indexRemove; j < this.numVertices + 1; j++) {
                    this.adjMatrix[i][j] = 0.0;
                }
            }
        }

    }

    @Override
    public void addEdge(T vertex1, T vertex2) {
        addEdge(getIndex(vertex1), getIndex(vertex2));
    }

    public void addEdge(int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = 1.0;
            adjMatrix[index2][index1] = 1.0;
        }
    }

    protected int getIndex(T vertex) {
        boolean found = false;
        int current = 0, index = -1;
        while (!found && current < this.numVertices) {
            if (vertex.equals(this.vertices[current])) {
                found = true;
                index = current;
            }
            current++;
        }
        return index;
    }

    public T getVertex(int i){
        if (i >= 0 && i < vertices.length)
            return vertices[i];
        else
            throw new ArrayIndexOutOfBoundsException("Vertex doesn't exists");
    }
    protected boolean indexIsValid(int index) {
        return (index > -1 && index < this.numVertices);
    }

    @Override
    public void removeEdge(T vertex1, T vertex2) {
        this.removeEdge(this.getIndex(vertex1), this.getIndex(vertex2));
    }

    private void removeEdge(int index1, int index2) {
        if (this.indexIsValid(index1) && this.indexIsValid(index2)) {
            this.adjMatrix[index1][index2] = 0.0;
            this.adjMatrix[index2][index1] = 0.0;
        }
    }

    @Override
    public Iterator iteratorBFS(T startVertex) throws EmptyCollectionException {
        int startIndex = getIndex(startVertex);
        Integer x;
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<Integer>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();
        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }
        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalQueue.enqueue(new Integer(startIndex));
        visited[startIndex] = true;
        while (!traversalQueue.isEmpty()) {
            x = traversalQueue.dequeue();
            resultList.addToRear(vertices[x.intValue()]);
            /**
             * Encontra todos os vertex adjacentes a x e que não foram visitados
             * e adiciona-os à DataStructure.queue
             */
            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[x][i] == 1.0 && !visited[i]) {
                    traversalQueue.enqueue(new Integer(i));
                    visited[i] = true;
                }
            }
        }
        return resultList.iterator();
    }

    @Override
    public Iterator iteratorDFS(T startVertex) throws EmptyCollectionException {
        int startIndex = getIndex(startVertex);
        Integer x;
        boolean found;
        LinkedStack<Integer> traversalStack = new LinkedStack<Integer>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
        boolean[] visited = new boolean[numVertices];
        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalStack.push(new Integer(startIndex));
        resultList.addToRear(vertices[startIndex]);
        visited[startIndex] = true;
        while (!traversalStack.isEmpty()) {
            x = traversalStack.peek();
            found = false;
            /**
             * Encontra um vértice adjacente a x que não tenha sido visitado e
             * faz push desse vertex para a DataStructure.stack
             */
            for (int i = 0; (i < numVertices) && !found; i++) {
                if (adjMatrix[x][i] == 1.0 && !visited[i]) {
                    traversalStack.push(new Integer(i));
                    resultList.addToRear(vertices[i]);
                    visited[i] = true;
                    found = true;
                }
            }
            if (!found && !traversalStack.isEmpty()) {
                traversalStack.pop();
            }
        }
        return resultList.iterator();

    }

    @Override
    public Iterator iteratorShortestPath(T startVertex, T targetVertex) throws EmptyCollectionException {
        int startIndex = getIndex(startVertex);
        int endIndex = getIndex(targetVertex);
        Integer[] x;
        QueueADT<Integer[]> traversalQueue = new ArrayQueue<Integer[]>();
        UnorderedListADT<Integer[]> resultList = new ArrayUnorderedList<>();
        UnorderedListADT<T> path = new ArrayUnorderedList<>();
        
        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }
        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        Integer[] start = {-1, startIndex};
        traversalQueue.enqueue(start);
        visited[startIndex] = true;
        boolean found = false;

        while (!found && !traversalQueue.isEmpty()) {
            x = traversalQueue.dequeue();
            resultList.addToRear(x);

            if (x[1] == endIndex) {
                found = true;
            } else {
                /**
                 * Encontra todos os vertex adjacentes a x e que não foram
                 * visitados e adiciona-os à DataStructure.queue
                 */
                for (int i = 0; i < numVertices; i++) {
                    if (adjMatrix[x[1]][i] == 1.0 && !visited[i]) {
                        Integer[] add = new Integer[2];
                        add[0] = x[1];
                        add[1] = i;
                        traversalQueue.enqueue(add);
                        visited[i] = true;
                    }
                }
            }
        }
        
        int order = endIndex;
        while(!resultList.isEmpty()){
            Integer[] elementToOrder = resultList.removeLast();
            if(elementToOrder[1] == order){
                path.addToFront(this.vertices[elementToOrder[1]]);
                order = elementToOrder[0];
            }
        }
        return path.iterator();
    }

    @Override
    public boolean isEmpty() {
        return this.numVertices == 0;
    }

    @Override
    public boolean isConnected() throws EmptyCollectionException {
        boolean isConnected = true;
        int index = 0;

        while (isConnected && index != this.numVertices) {

            int count = 0;

            /**
             * Um grafo é conexo se para cada vertice index, o resultado da
             * travessia em largura seja igual ao nº de vertices total
             */
            Iterator<T> itr1 = this.iteratorBFS(this.vertices[index]);

            while (itr1.hasNext()) {
                count++;
                itr1.next();
            }

            if (count != this.numVertices) {
                isConnected = false;
            }

            index++;
        }

        return isConnected;
    }

    @Override
    public int size() {
        return this.numVertices;
    }

    @Override
    public void printAdjacencyMatrix() {
        for (int i = 0; i < this.numVertices; i++) {
            for (int j = 0; j < this.numVertices; j++) {
                System.out.println("Aposento Linha: " + this.vertices[i] + " Aposento Linha:  " + this.vertices[j] + " Valor:  " + this.adjMatrix[i][j]);
            }
            System.out.println("\n");
        }

        for (int i = 0; i < this.numVertices; i++) {
            System.out.println("" + this.vertices[i]);
        }

        System.out.println("\n");

    }
}
