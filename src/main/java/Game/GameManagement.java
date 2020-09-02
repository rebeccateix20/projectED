package Game;

import DataStructure.Exceptions.ElementNotFoundException;
import DataStructure.Exceptions.EmptyCollectionException;
import DataStructure.Exceptions.NoPathAvailable;
import DataStructure.Graph.Network.ArrayDirectedNetwork;
import DataStructure.Graph.Network.PathCostVerticeWithElement;
import DataStructure.list.LinkedList;
import DataStructure.list.OrderedList.ArrayOrderedList;
import DataStructure.list.UnorderedList.ArrayUnorderedList;
import DataStructure.list.UnorderedList.LinkedUnorderedList;
import DataStructure.list.UnorderedList.UnorderedListADT;
import DataStructure.stack.ArrayStack;
import ExceptionsGame.InvalidMapException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.activation.UnsupportedDataTypeException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;


public class GameManagement {
    private Mapa mapa;
    private ArrayDirectedNetwork<Aposento> network;
    private ArrayOrderedList<Player> resultados;
    private int dificuldade;
    private boolean teletransporte;
    private Aposento escudo;
    private Aposento acrescimo;
    private ArrayStack<Jogada> jogadas;

    public ArrayOrderedList<Player> getResultados() {
        return resultados;
    }

    public void setTeletransporte(boolean teletransporte) {
        this.teletransporte = teletransporte;
    }

    public GameManagement(Mapa mapa, ArrayDirectedNetwork<Aposento> network, ArrayOrderedList<Player> resultados) {
        this.mapa = mapa;
        this.network = network;
        this.resultados = resultados;
    }

    public GameManagement() {
    }

    public boolean lerFicheiro(String ficheiro, int dificuldade) {
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader(ficheiro)) {
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
        } catch (FileNotFoundException e) {
            System.out.println("ficheiro não encontrado");
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Erro ao ler o mapa");
        }
        return false;
    }

    public void createNetwork() throws ElementNotFoundException {
        network = new ArrayDirectedNetwork<>();

        //Cria os vertices e adiciona à lista dos aposentos do mapa
        ArrayUnorderedList<String> ligacoesEntrada = new ArrayUnorderedList<>();
        ArrayUnorderedList<String> ligacoesSaida = new ArrayUnorderedList<>();
        boolean found = false;

        Iterator<Aposento> it = this.mapa.getAposentos().iterator();

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

        for (Aposento aposento : mapa.getAposentos()) {
            network.addVertex(aposento);
        }

        //Para cada aposento vai criar as arestas
        Iterator<Aposento> iterator = this.mapa.getAposentos().iterator();

        while (iterator.hasNext()) {
            Aposento ap = iterator.next();
            Iterator<String> iterator1 = ap.getLigacoesIterator();

            while (iterator1.hasNext()) {
                String lig = iterator1.next();

                if (!lig.equals("entrada")) {
                    this.network.addEdge(this.searchAposento(ap.getNome()), this.searchAposento(lig), ap.getCostFantasmas());
                }
            }
        }
    }

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

    private Aposento searchAposento(String aposento) throws ElementNotFoundException {
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

    public boolean validateMap() throws NoPathAvailable, EmptyCollectionException, ElementNotFoundException, InvalidMapException {
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

    public void simulation() throws EmptyCollectionException, ElementNotFoundException {
        Iterator<Aposento> iterator = this.network.iteratorShortestPath(this.searchAposento("entrada"), this.searchAposento("exterior"));
        while (iterator.hasNext()) {
            Aposento ap = iterator.next();
            System.out.print(ap.getNome() + " - ");
        }
        System.out.print("\n");
    }

    public void gameplay(Player player) throws ElementNotFoundException, UnsupportedDataTypeException, EmptyCollectionException {
        this.jogadas = new ArrayStack<>();
        int nUndos = 0;

        if(this.dificuldade == 1){
            nUndos = 5;
        } else if(this.dificuldade == 2){
            nUndos = 3;
        } else if(this.dificuldade == 3){
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
                Iterator<Aposento> itr = this.mapa.getAposentos().iterator();
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

            if (nUndos !=0) {
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

                    if (ap.getFantasma(0) > 0 && !this.teletransporte && this.acrescimo == null && this.escudo == null) {
                        System.out.println("BOO! Apareceu um fantasma!");

                    } else if (ap.getFantasma(0) > 0 && this.teletransporte) {
                        System.out.println("ENTROU NO TELETRANSPORTE");
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
                        player.damage(ap.getCostTotal());
                        Aposento apTemp = this.escudo;
                        apTemp.setCostTotal(0);
                        this.escudo = null;
                    } else if (ap.getCostTotal() < 0 && this.acrescimo != null && ap.getNome().equals(this.acrescimo.getNome())) {
                        System.out.println("ENTROU ACRESCIMO ");
                        player.damage(ap.getCostTotal());
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
                } else if (opcao == -1 && nUndos!=0) {

                    try{
                        this.jogadas.pop();
                        Jogada jog = this.jogadas.peek();
                        Mapa mapaSec = new Mapa(this.mapa.getNome(), this.mapa.getPontos(), jog.getAposentos());
                        ap = jog.getCurrentAposento();
                        player.setPontos(jog.getPoints());
                        this.mapa = mapaSec;
                        nUndos--;
                        stopIt = true;
                    } catch(ArrayIndexOutOfBoundsException e){
                        System.out.println("Não pode fazer undo");
                    }

                }
            } while (!stopIt);

        }

        player.setEstimateTime();
        System.out.println("TERMINOU!");
        System.out.println("Obteve: " + player.getPontos() + " pontos");
        this.addResults(player.getNome(), player.getPontos(), this.mapa.getNome(), this.dificuldade, player.getEstimateTime());

    }

    private void printMap() throws ElementNotFoundException {
        Iterator<Aposento> ap = this.mapa.getAposentos().iterator();

        while (ap.hasNext()) {
            Aposento apo = ap.next();

            System.out.print(apo.getNome() + ": ");

            Iterator<String> lig = apo.getLigacoesIterator();

            while (lig.hasNext()) {
                Aposento apCheck = this.searchAposento(lig.next());
                if (apCheck.getCostFantasmas() > 0) {
                    System.out.print(" -> " + apCheck.getNome() + "(FANTASMA! PERDES " + apCheck.getCostFantasmas() + " PONTOS)");
                } else {
                    System.out.print(" -> " + apCheck.getNome());
                }
            }
            System.out.println("");
        }
    }

    private Aposento choice(int opc) {
        return this.network.getElementVertice(opc);
    }

    private void moveFantasmas(Aposento playerAp) throws ElementNotFoundException {
        for (Aposento ap4 : this.mapa.getAposentos()) {
            System.out.print("APOSENTO: " + ap4.getNome() + " FANTASMAS: ");
            Integer[] fan = ap4.getFantasmas();
            for (int i = 0; i < fan.length; i++) {
                System.out.print(fan[i]);
            }
            System.out.println();
        }

        for (Aposento ap : this.mapa.getAposentos()) {
            if (ap.getCostFantasmas() > 0) {
                Iterator<String> it = ap.getLigacoesIterator();
                while (it.hasNext()) {
                    Aposento ap2 = this.searchAposento(it.next());
                    int j = 0;
                    while (j < ap.getFantasmas().length) {
                        if (ap.getFantasma(j) != 0) {
                            if (ap2.getCostFantasmas() == 0 && !ap2.equals(playerAp) && !ap2.equals(this.searchAposento("exterior"))) {
                                ap2.setCostTotal(ap.getFantasma(j));
                                ap2.setFantasma(0, ap.getFantasma(j));

                                ap.setFantasma(j, 0);
                                ap.setCostTotal(-ap.getFantasma(j));

                                Iterator<String> it2 = ap2.getLigacoesIterator();
                                while (it2.hasNext()) {
                                    Aposento apUpdate = this.searchAposento(it2.next());
                                    this.network.removeEdge(ap2, apUpdate);
                                    this.network.removeEdge(apUpdate, ap2);
                                    this.network.addEdge(apUpdate, ap2, ap2.getCostFantasmas());
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
                    if(ap.getCostFantasmas() != 0 && ap.getFantasma(0)==0){
                        ap.shiftFantasmas();
                    }
                }
            }

        }

    }

    public void loadResults() {
        File file = new File("resources/results.csv");
        this.resultados = new ArrayOrderedList<>();
        try {
            Scanner scanner = new Scanner(file);

            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String[] resultado = scanner.nextLine().split(",");
                this.resultados.add(new Player(resultado[0], Integer.parseInt(resultado[1]), resultado[2], new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(resultado[3]), Integer.parseInt(resultado[4]), Float.parseFloat(resultado[5])));
            }
            scanner.close();

            System.out.println("Resultados carregados");
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro nao existe");
        } catch (Exception e) {
            System.out.println("Erro interno");
        }
    }

    public void saveResults() {
        File file = new File("resources/results.csv");
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("nome,pontos,mapa,data,dificuldade,time");
            for (Player resultado : this.resultados) {
                fileWriter.write("\n" + resultado.getNome() + "," + resultado.getPontos() + "," + resultado.getMapa() + "," + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(resultado.getData()) + "," + resultado.getDificuldade() + "," + resultado.getEstimateTime());
            }
            fileWriter.close();

            System.out.println("Resultados Guardados");
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado");
        } catch (Exception e) {
            System.out.println("Erro interno");
        }
    }

    public void addResults(String nome_jogador, int pontos, String nome_mapa, int dificuladade, float time) throws UnsupportedDataTypeException {
        loadResults();
        this.resultados.add(new Player(nome_jogador, pontos, nome_mapa, new Date(), dificuladade, time));
        saveResults();
    }


}
