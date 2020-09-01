package Game;

import DataStructure.Graph.Network.ArrayDirectedNetwork;
import DataStructure.list.UnorderedList.ArrayUnorderedList;

import java.util.Iterator;

public class Jogada {
    private ArrayUnorderedList<Aposento> aposentos;
    private Aposento currentAposento;
    private int points;

    public Jogada(ArrayUnorderedList<Aposento> aposentos, Aposento currentAposento, int points) {
        this.aposentos = new ArrayUnorderedList<>();

        //Não igualar, temos de fazer um copy
        Iterator<Aposento> aps = aposentos.iterator();
        while(aps.hasNext()){
            Aposento ap = aps.next();
            this.aposentos.addToRear(new Aposento(ap.getNome(),ap.getFantasma(),ap.getLigacoes()));
        }
        this.currentAposento = currentAposento;
        this.points = points;
    }

    public ArrayUnorderedList<Aposento> getAposentos() {
        return aposentos;
    }

    public Aposento getCurrentAposento() {
        return currentAposento;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        String s = "Jogada{" +
                ", currentAposento=" + currentAposento.getNome() + ", points=" + points + "Aposentos{";
        Iterator<Aposento> aps = aposentos.iterator();
        while(aps.hasNext()){
            Aposento ap = aps.next();
            s+= ap.getNome() + " - " + ap.getFantasma();
        }
        s+=" }";
        return s;
    }
}
