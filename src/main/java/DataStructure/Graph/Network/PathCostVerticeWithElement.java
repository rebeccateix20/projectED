/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructure.Graph.Network;

/**
 * @author Rebeca
 */
public class PathCostVerticeWithElement<T> {
    private T previous;
    private T current;
    private double cost;

    public PathCostVerticeWithElement(T previous, T current, double cost) {
        this.previous = previous;
        this.current = current;
        this.cost = cost;
    }

    public T getPrevious() {
        return previous;
    }


    public T getCurrent() {
        return current;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "<" + this.previous + "," + this.current + "," + this.cost + ">";
    }


}
