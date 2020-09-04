package Game;

import DataStructure.Graph.Network.ArrayDirectedNetwork;
import DataStructure.list.UnorderedList.ArrayUnorderedList;

import java.util.Iterator;

public class Jogada {
    private ArrayUnorderedList<Aposento> aposentos;
    private Aposento currentAposento;
    private int points;

    /**
     * Metodo construtor da jogada
     * @param aposentos aposentos do mapa
     * @param currentAposento aposento do jogador
     * @param points pontos do jogador
     */
    public Jogada(ArrayUnorderedList<Aposento> aposentos, Aposento currentAposento, int points) {
        this.aposentos = new ArrayUnorderedList<>();

        Iterator<Aposento> aps = aposentos.iterator();
        while(aps.hasNext()){
            Aposento ap = aps.next();
            Integer[] newFantasmas = new Integer[ap.getFantasmas().length];
            for(int i=0; i<ap.getFantasmas().length; i++){
                newFantasmas[i] = ap.getFantasma(i);
            }
            this.aposentos.addToRear(new Aposento(ap.getNome(),newFantasmas,ap.getLigacoes(), ap.getCostTotal()));
        }
        this.currentAposento = currentAposento;
        this.points = points;
    }

    /**
     * Metodo que devolve os aposentos da jogada
     * @return
     */
    public ArrayUnorderedList<Aposento> getAposentos() {
        return aposentos;
    }

    /**
     * Metodo que devolve o aposento atual do jogador na jogada
     * @return
     */
    public Aposento getCurrentAposento() {
        return currentAposento;
    }

    /**
     * Metodo que devolve os pontos do player na jogada
     * @return
     */
    public int getPoints() {
        return points;
    }


    /**
     * Metodo responsavel por imprimir a jogada
     * @return
     */
    @Override
    public String toString() {
        String s = "Jogada{" +
                ", currentAposento=" + currentAposento.getNome() + ", points=" + points + "Aposentos{";
        Iterator<Aposento> aps = aposentos.iterator();
        while(aps.hasNext()){
            Aposento ap = aps.next();
            s+= ap.getNome();
            for(int i= 0; i<ap.getFantasmas().length; i++){
                s += " - " + ap.getFantasma(i);
            }
        }
        s+=" }";
        return s;
    }
}
