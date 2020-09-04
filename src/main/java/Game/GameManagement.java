package Game;

import DataStructure.Exceptions.ElementNotFoundException;
import DataStructure.Exceptions.EmptyCollectionException;
import DataStructure.Exceptions.NoPathAvailable;
import DataStructure.Graph.Network.ArrayDirectedNetwork;
import DataStructure.Graph.Network.PathCostVerticeWithElement;
import DataStructure.list.UnorderedList.ArrayUnorderedList;
import DataStructure.list.UnorderedList.UnorderedListADT;
import DataStructure.stack.ArrayStack;
import ExceptionsGame.InvalidMapException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.activation.UnsupportedDataTypeException;
import java.io.*;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;


public class GameManagement {
    private Mapa mapa;
    private ArrayDirectedNetwork<Aposento> network;
    private int dificuldade;
    private boolean teletransporte;
    private Aposento escudo;
    private Aposento acrescimo;
    private ArrayStack<Jogada> jogadas;


    public void setTeletransporte(boolean teletransporte) {
        this.teletransporte = teletransporte;
    }

    /**
     * Método construtor
     */
    public GameManagement() {
    }

    public Mapa getMapa() {
        return mapa;
    }

    /**
     * Método responsável por ler o ficheiro
     * @param ficheiro path do ficheiro
     * @param dificuldade dificuldade do mapa
     * @return
     * @throws InvalidMapException
     * @throws NoPathAvailable
     * @throws ElementNotFoundException
     * @throws EmptyCollectionException
     * @throws IOException
     * @throws ParseException
     */
    public boolean lerFicheiro(String ficheiro, int dificuldade) throws InvalidMapException, NoPathAvailable, ElementNotFoundException, EmptyCollectionException, IOException, ParseException {
        if (ficheiro != "" && dificuldade > 0 && dificuldade < 4) {
            JSONParser parser = new JSONParser();
            Reader reader = new FileReader(ficheiro);
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            String nome = jsonObject.get("nome").toString();

            int pontos = Integer.parseInt(jsonObject.get("pontos").toString());

            ArrayUnorderedList<Aposento> aposentos = new ArrayUnorderedList<>();
            JSONArray jsonArray_mapa = (JSONArray) jsonObject.get("mapa");

            for (Object obj : jsonArray_mapa) {
                JSONObject it = (JSONObject) obj;
                String nome_aposento = it.get("aposento").toString();

                int fantasma = Integer.parseInt(it.get("fantasma").toString());

                ArrayUnorderedList<String> ligacoes_aposento = new ArrayUnorderedList<>();
                JSONArray jsonArray_ligacoes = (JSONArray) it.get("ligacoes");
                for (Object jsonArray_ligacao : jsonArray_ligacoes) {
                    String ligacao = jsonArray_ligacao.toString();
                    ligacoes_aposento.addToRear(ligacao);
                }
                if (dificuldade == 1) {
                    Integer[] fantasmaTemp = {fantasma};
                    aposentos.addToRear(new Aposento(nome_aposento, fantasmaTemp, ligacoes_aposento, fantasma));
                } else if (dificuldade == 2) {
                    Integer[] fantasmaTemp = {fantasma, fantasma};
                    aposentos.addToRear(new Aposento(nome_aposento, fantasmaTemp, ligacoes_aposento, fantasma * 2));
                } else if (dificuldade == 3) {
                    Integer[] fantasmaTemp = {fantasma, fantasma, fantasma};
                    aposentos.addToRear(new Aposento(nome_aposento, fantasmaTemp, ligacoes_aposento, fantasma * 3));
                }
            }

            this.mapa = new Mapa(nome, pontos, aposentos);
            this.dificuldade = dificuldade;
            setTeletransporte(false);
            this.createNetwork();
            this.validateMap();
            this.addBonus();
            return true;
        } else {
            throw new InvalidMapException("Mapa invalido");
        }

    }

    /**
     * Método responsavel por criar a network
     * @throws ElementNotFoundException
     */
    public void createNetwork() throws ElementNotFoundException {
        network = new ArrayDirectedNetwork<>();

        //Cria os vertices e adiciona à lista dos aposentos do mapa
        ArrayUnorderedList<String> ligacoesEntrada = new ArrayUnorderedList<>();
        ArrayUnorderedList<String> ligacoesSaida = new ArrayUnorderedList<>();
        boolean found = false;

        Iterator<Aposento> it = this.mapa.getAposentosIterator();

        while (it.hasNext() && !found) {
            Aposento ap = it.next();
            Iterator<String> lig = ap.getLigacoesIterator();

            while (!found && lig.hasNext()) {
                String ligacao = lig.next();

                if (ligacao.equals("entrada")) {
                    ligacoesEntrada.addToRear(ap.getNome());
                    found = true;
                }
            }
        }

        Aposento entrada = new Aposento("entrada", new Integer[]{0}, ligacoesEntrada, 0);

        Aposento exterior = new Aposento("exterior", new Integer[]{0}, ligacoesSaida, 0);
        this.mapa.getAposentos().addToFront(entrada);
        this.mapa.getAposentos().addToRear(exterior);

        Iterator<Aposento> aps = this.mapa.getAposentosIterator();
        while(aps.hasNext()){
            Aposento ap = aps.next();
            network.addVertex(ap);
        }

        //Para cada aposento vai criar as arestas
        Iterator<Aposento> iterator = this.mapa.getAposentosIterator();

        while (iterator.hasNext()) {
            Aposento ap = iterator.next();
            Iterator<String> iterator1 = ap.getLigacoesIterator();

            while (iterator1.hasNext()) {
                String lig = iterator1.next();

                if (!lig.equals("entrada")) {
                    this.network.addEdge(this.searchAposento(ap.getNome()), this.searchAposento(lig), ap.getCostTotal());
                }
            }
        }
    }

    /**
     * Método que permite adicionar os bonus ao mapa
     */
    public void addBonus() {
        Random random = new Random();
        int option = random.nextInt((3 - 1) + 1) + 1;
        Random random2 = new Random();
        int i2 = random2.nextInt(this.mapa.getNumberAposentosSemFantasma());
        Iterator<Aposento> aposentoSemFantasma = this.mapa.getAposentosSemFantasmaIterator();
        while (aposentoSemFantasma.hasNext()) {
            Aposento aposento = aposentoSemFantasma.next();
            if (i2 == 0) {
                switch (option) {
                    case 1: //Acresce 25 pontos de vida ao jogador
                        System.out.println("APOSENTO COM ACRESCIMO :::::::::::::::: " + aposento.getNome());
                        aposento.setCostTotal(-25);
                        this.acrescimo = aposento;
                        break;
                    case 2: //Escudo
                        System.out.println("APOSENTO COM ESCUDO :::::::::::::::: " + aposento.getNome());
                        Random r = new Random();
                        int dano = r.nextInt((this.mapa.getMaxDamageFantasma() - 1) + 1) + 1;
                        aposento.setCostTotal(-dano);
                        System.out.println("VALOR ESCUDO :::::::::::::: " + dano);
                        this.escudo = aposento;
                        break;

                    case 3: //Teletransporte
                        System.out.println("CALHOU TELETRANSPORTE :::::::::::::::: " + aposento.getNome());
                        aposento.setTeletransporte(true);
                        break;
                }
            }
            i2--;
        }
    }

    /**
     * Método que a partir do nome, devolve o aposento correspondente
     * @param aposento nome do aposento
     * @return
     * @throws ElementNotFoundException
     */
    public Aposento searchAposento(String aposento) throws ElementNotFoundException {
        UnorderedListADT aposentos = this.mapa.getAposentos();
        Aposento aposento1 = null;
        boolean found = false;


        Iterator<Aposento> iterator = aposentos.iterator();

        while (!found && iterator.hasNext()) {
            aposento1 = iterator.next();
            if ((aposento1.getNome()).equals(aposento)) {
                found = true;
            }
        }

        if (!found) {
            throw new ElementNotFoundException("Aposento não existe");
        }

        return aposento1;
    }

    /**
     * Método responsavel pela validação do mapa
     * @return
     * @throws NoPathAvailable
     * @throws EmptyCollectionException
     * @throws ElementNotFoundException
     * @throws InvalidMapException
     */
    private boolean validateMap() throws NoPathAvailable, EmptyCollectionException, ElementNotFoundException, InvalidMapException {
        double custo = 0.0;
        int nFantasmas = 0;
        ArrayUnorderedList<String> lista = new ArrayUnorderedList<>();
        Iterator<PathCostVerticeWithElement<Aposento>> iterator = this.network.iteratorShortestPathWeight(this.searchAposento("entrada"), this.searchAposento("exterior"));
        while (iterator.hasNext()) {
            PathCostVerticeWithElement<Aposento> p = iterator.next();
            Aposento ap = p.getCurrent();
            lista.addToRear(ap.getNome());
            Integer[] fantasmas = ap.getFantasmas();
            for (int i = 0; i < fantasmas.length; i++) {
                custo += fantasmas[i];
            }
            nFantasmas += ap.getNFantasmas();
        }
        if (custo < this.mapa.getPontos() && lista.contains("entrada") && lista.contains("exterior") && nFantasmas < this.mapa.getNAposentos()) {
            System.out.println("Mapa carregado e validado");
            this.printMap();
            return true;
        } else {
            throw new InvalidMapException("Mapa Inválido");
        }
    }

    /**
     * Método responsavel pelo modo de simulação do jogo
     * @return
     * @throws EmptyCollectionException
     * @throws ElementNotFoundException
     */
    public String simulation() throws EmptyCollectionException, ElementNotFoundException {
        String s="";
        Iterator<Aposento> iterator = this.network.iteratorShortestPath(this.searchAposento("entrada"), this.searchAposento("exterior"));
        while (iterator.hasNext()) {
            Aposento ap = iterator.next();
            s+= ap.getNome() + " - ";
        }
        return s;
    }

    /**
     * Método responsavel pelo gameplay manual do jogo
     * @param player
     * @throws ElementNotFoundException
     * @throws UnsupportedDataTypeException
     * @throws EmptyCollectionException
     */
    public void gameplay(Player player) throws ElementNotFoundException, UnsupportedDataTypeException, EmptyCollectionException {
        this.jogadas = new ArrayStack<>();
        int nUndos = 0;

        if (this.dificuldade == 1) {
            nUndos = 5;
        } else if (this.dificuldade == 2) {
            nUndos = 3;
        } else if (this.dificuldade == 3) {
            nUndos = 1;
        }

        player.setPontos(this.mapa.getPontos());
        Aposento ap = this.searchAposento("entrada");

        player.setStartTime();
        while (ap != this.searchAposento("exterior")) {

            System.out.println("Vida: " + player.getPontos());
            System.out.println("Posição: " + ap.getNome());
            System.out.println("Opcoes: ");

            ArrayUnorderedList<Integer> lista = new ArrayUnorderedList<>();
            Iterator<String> it = ap.getLigacoesIterator();

            int i = 0;

            while (it.hasNext()) {
                Iterator<Aposento> itr = this.mapa.getAposentosIterator();
                i = 0;
                String ap2 = it.next();

                if (!ap2.equals("entrada")) {
                    while (itr.hasNext()) {
                        Aposento ap4Check = itr.next();
                        if (ap2.equals(ap4Check.getNome())) {
                            System.out.println(i + " - " + ap2 + " ");
                            lista.addToRear(i);
                        }
                        i++;
                    }
                }
            }
            System.out.println("0 - See Mapa ");

            if (nUndos != 0) {
                System.out.println("-1 - Undo Jogada ");
            }

            boolean stopIt = false;
            int opcao;
            do {
                System.out.println("Para onde deseja ir: ");
                Scanner s = new Scanner(System.in);
                opcao = s.nextInt();

                if (lista.contains(opcao)) {
                    ap = this.choice(opcao);

                    if (ap.isTeletransporte()) {
                        this.teletransporte = true;
                        ap.setTeletransporte(false);
                    }

                    if (ap.getCostTotal() > 0 && !this.teletransporte) {
                        System.out.println("BOO! Apareceu um fantasma!");
                    } else if (ap.getCostTotal() > 0 && this.teletransporte) {
                        Iterator<Aposento> apSemFantasmas = this.mapa.getAposentosSemFantasmaIterator();
                        Random random2 = new Random();
                        int i2 = random2.nextInt(this.mapa.getNumberAposentosSemFantasma());
                        while (apSemFantasmas.hasNext()) {
                            Aposento apSemGhost = apSemFantasmas.next();
                            if (i2 == 0) {
                                System.out.println("APOSENTO QUE VAI SER TELETRANSPORTADO :::::::::::::: " + apSemGhost.getNome());
                                this.teletransporte = false;
                                ap = apSemGhost;
                            }
                            i2--;
                        }
                    } else if (ap.getCostTotal() < 0 && this.escudo != null && ap.getNome().equals(this.escudo.getNome())) {
                        System.out.println("ENTROU ESCUDO");
                        Aposento apTemp = this.escudo;
                        apTemp.setCostTotal(0);
                        this.escudo = null;
                    } else if (ap.getCostTotal() < 0 && this.acrescimo != null && ap.getNome().equals(this.acrescimo.getNome())) {
                        System.out.println("ENTROU ACRESCIMO ");
                        Aposento apTemp = this.acrescimo;
                        apTemp.setCostTotal(0);
                        this.acrescimo = null;
                    }

                    if (player.getPontos() - ap.getCostTotal() > 0) {
                        player.damage(ap.getCostTotal());
                    } else {
                        player.setPontos(0);
                        ap = searchAposento("exterior");
                        System.out.println("GAME OVER!!!");
                        break;
                    }

                    Mapa mapa3 = new Mapa(this.mapa.getPontos(), this.mapa.getAposentos());
                    this.jogadas.push(new Jogada(mapa3.getAposentos(), ap, player.getPontos()));
                    moveFantasmas(ap);
                    stopIt = true;
                } else if (opcao == 0) {
                    this.printMap();
                } else if (opcao == -1 && nUndos != 0) {
                    try {
                        this.jogadas.pop();
                        Jogada jog = this.jogadas.peek();
                        Mapa mapaSec = new Mapa(this.mapa.getNome(), this.mapa.getPontos(), jog.getAposentos());
                        ap = jog.getCurrentAposento();
                        player.setPontos(jog.getPoints());
                        this.mapa = mapaSec;
                        nUndos--;
                        stopIt = true;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Não pode fazer undo");
                    }

                }
            } while (!stopIt);

        }

        player.setEstimateTime();
        System.out.println("TERMINOU!");
        System.out.println("Obteve: " + player.getPontos() + " pontos");

    }

    /**
     * Método repsonsavel por dar print do mapa
     * @throws ElementNotFoundException
     */
    public void printMap() throws ElementNotFoundException {
        Iterator<Aposento> ap = this.mapa.getAposentosIterator();

        while (ap.hasNext()) {
            Aposento apo = ap.next();

            System.out.print(apo.getNome() + ": ");

            Iterator<String> lig = apo.getLigacoesIterator();

            while (lig.hasNext()) {
                Aposento apCheck = this.searchAposento(lig.next());
                if (apCheck.getCostTotal() > 0) {
                    System.out.print(" -> " + apCheck.getNome() + "(FANTASMA! PERDES " + apCheck.getCostTotal() + " PONTOS)");
                } else {
                    System.out.print(" -> " + apCheck.getNome());
                }
            }
            System.out.println("");
        }
    }

    /**
     * Método que devolve um aposento consoante um valor
     * @param opc
     * @return
     */
    private Aposento choice(int opc) {
        return this.network.getElementVertice(opc);
    }

    /**
     * Método responsavel por mover os fantasmas a cada rodada
     * @param playerAp
     * @throws ElementNotFoundException
     */
    public void moveFantasmas(Aposento playerAp) throws ElementNotFoundException {
        /*for (Aposento ap4 : this.mapa.getAposentos()) {
            System.out.print("APOSENTO: " + ap4.getNome() + " FANTASMAS: ");
            Integer[] fan = ap4.getFantasmas();
            for (int i = 0; i < fan.length; i++) {
                System.out.print(fan[i]);
            }
            System.out.print(" CUSTO TOTAL: " + ap4.getCostTotal());
            System.out.println();
        }*/

        for (Aposento ap : this.mapa.getAposentos()) {
            if (ap.getCostTotal() > 0) {
                Iterator<String> it = ap.getLigacoesIterator();
                while (it.hasNext()) {
                    Aposento ap2 = this.searchAposento(it.next());
                    int j = 0;
                    while (j < ap.getFantasmas().length) {
                        if (ap.getFantasma(j) != 0) {
                            if (ap2.getCostTotal() == 0 && !ap2.equals(playerAp) && !ap2.equals(this.searchAposento("exterior"))) {

                                ap2.setCostTotal(ap.getFantasma(j));
                                ap2.setFantasma(0, ap.getFantasma(j));

                                ap.setCostTotal(-ap.getFantasma(j));
                                ap.setFantasma(j, 0);


                                Iterator<String> it2 = ap2.getLigacoesIterator();
                                while (it2.hasNext()) {
                                    Aposento apUpdate = this.searchAposento(it2.next());
                                    this.network.removeEdge(ap2, apUpdate);
                                    this.network.removeEdge(apUpdate, ap2);
                                    this.network.addEdge(apUpdate, ap2, ap2.getCostTotal());
                                    this.network.addEdge(ap2, apUpdate, 0);
                                }

                                Iterator<String> it3 = ap.getLigacoesIterator();
                                while (it3.hasNext()) {
                                    Aposento apUpdate2 = this.searchAposento(it3.next());
                                    this.network.removeEdge(ap, apUpdate2);
                                    this.network.removeEdge(apUpdate2, ap);
                                    this.network.addEdge(apUpdate2, ap, 0);
                                    this.network.addEdge(ap, apUpdate2, 0);
                                }
                            }
                        }
                        j++;
                    }
                    //shift do fantasma para a posição 0
                    if (ap.getCostTotal() != 0 && ap.getFantasma(0) == 0) {
                        ap.shiftFantasmas();
                    }
                }
            }

        }

    }



}
