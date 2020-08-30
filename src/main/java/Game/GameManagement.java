package Game;

import DataStructure.Exceptions.ElementNotFoundException;
import DataStructure.Exceptions.EmptyCollectionException;
import DataStructure.Exceptions.NoPathAvailable;
import DataStructure.Graph.Network.ArrayDirectedNetwork;
import DataStructure.Graph.Network.PathCostVerticeWithElement;
import DataStructure.list.OrderedList.ArrayOrderedList;
import DataStructure.list.UnorderedList.ArrayUnorderedList;
import DataStructure.list.UnorderedList.UnorderedListADT;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.activation.UnsupportedDataTypeException;
import java.io.*;
import java.text.DecimalFormat;
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

    public Mapa getMapa() {
        return mapa;
    }

    public ArrayDirectedNetwork<Aposento> getNetwork() {
        return network;
    }

    public ArrayOrderedList<Player> getResultados() {
        return resultados;
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
                aposentos.addToRear(new Aposento(nome_aposento, fantasma, ligacoes_aposento));
            }
            this.mapa = new Mapa(nome, pontos, aposentos);
            this.dificuldade = dificuldade;

            createNetwork();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("ficheiro não encontrado");
        } catch (Exception e) {
            System.out.println("Erro ao ler o mapa");
        }
        return false;
    }

    private void dificuldade(int dificuldade) {
        Iterator<Aposento> iterator = this.mapa.getAposentos().iterator();

        while (iterator.hasNext()) {
            Aposento ap = iterator.next();
            ap.setFantasma(dificuldade);
        }
    }

    public void createNetwork() throws ElementNotFoundException {
        network = new ArrayDirectedNetwork<>();

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

        Aposento entrada = new Aposento("entrada", 0, ligacoesEntrada);

        Aposento exterior = new Aposento("exterior", 0, ligacoesSaida);
        this.mapa.getAposentos().addToFront(entrada);
        this.mapa.getAposentos().addToRear(exterior);

        for (Aposento aposento : mapa.getAposentos()) {
            network.addVertex(aposento);
        }

        this.dificuldade(this.dificuldade);

        Iterator<Aposento> iterator = this.mapa.getAposentos().iterator();

        while (iterator.hasNext()) {
            Aposento ap = iterator.next();
            Iterator<String> iterator1 = ap.getLigacoesIterator();

            while (iterator1.hasNext()) {
                String lig = iterator1.next();

                if (!lig.equals("entrada")) {
                    this.network.addEdge(this.searchDivision(ap.getNome()), this.searchDivision(lig), this.searchDivision(lig).getFantasma());
                }
            }
        }

    }

    private Aposento searchDivision(String aposento) throws ElementNotFoundException {
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

    public boolean validateMap() throws NoPathAvailable, EmptyCollectionException, ElementNotFoundException {
        double custo = 0.0;
        Iterator<PathCostVerticeWithElement<Aposento>> iterator = this.network.iteratorShortestPathWeight(this.searchDivision("entrada"), this.searchDivision("exterior"));
        while (iterator.hasNext()) {
            PathCostVerticeWithElement<Aposento> p = iterator.next();
            Aposento ap = p.getCurrent();
            custo += ap.getFantasma();
        }
        if (custo < this.mapa.getPontos()) {
            return true;
        } else {
            return false;
        }
    }

    public void simulation() throws EmptyCollectionException, ElementNotFoundException {
        Iterator<Aposento> iterator = this.network.iteratorShortestPath(this.searchDivision("entrada"), this.searchDivision("exterior"));
        while (iterator.hasNext()) {
            Aposento ap = iterator.next();
            System.out.print(ap.getNome() + " - ");
        }
    }

    public void gameplay(Player player) throws ElementNotFoundException, UnsupportedDataTypeException {

        Aposento ap = this.searchDivision("entrada");

        player.setStartTime();
        while (ap != this.searchDivision("exterior")) {

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

            boolean stopIt = false;
            int opcao;

            do {
                System.out.println("Para onde deseja ir: ");
                Scanner s = new Scanner(System.in);
                opcao = s.nextInt();

                if (lista.contains(opcao)) {
                    ap = this.choice(opcao);

                    if (ap.getFantasma() > 0) {
                        System.out.println("BOO! Apareceu um fantasma!");
                    }

                    if (player.getPontos() - ap.getFantasma() > 0) {
                        player.damage(ap.getFantasma());
                    } else {
                        player.setPontos(0);
                        ap = searchDivision("exterior");
                        System.out.println("GAME OVER!!!");
                        break;
                    }
                    moveFantasmas(ap);

                    stopIt = true;
                } else if (opcao == 0) {
                    this.printMap();
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
                Aposento apCheck = this.searchDivision(lig.next());
                if (apCheck.getFantasma() > 0) {
                    System.out.print(" -> " + apCheck.getNome() + "(FANTASMA! PERDES " + apCheck.getFantasma() + " PONTOS)");
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
        boolean found = false;
        for (Aposento ap : this.mapa.getAposentos()) {
            found = false;
            if (ap.getFantasma() > 0) {
                Iterator<String> it = ap.getLigacoesIterator();
                while (it.hasNext() && !found) {
                    Aposento ap2 = this.searchDivision(it.next());
                    if (ap2.getFantasma() == 0 && !ap2.equals(playerAp) && !ap2.equals(this.searchDivision("exterior"))) {
                        ap2.setFantasma(15, this.dificuldade);
                        ap.setFantasma(0);

                        Iterator<String> it2 = ap2.getLigacoesIterator();
                        while (it2.hasNext()) {
                            Aposento apUpdate = this.searchDivision(it2.next());
                            this.network.removeEdge(ap2, apUpdate);
                            this.network.removeEdge(apUpdate, ap2);
                            this.network.addEdge(apUpdate, ap2, ap2.getFantasma());
                            this.network.addEdge(ap2, apUpdate, 0);
                        }

                        Iterator<String> it3 = ap.getLigacoesIterator();
                        while (it3.hasNext()) {
                            Aposento apUpdate2 = this.searchDivision(it3.next());
                            this.network.removeEdge(ap, apUpdate2);
                            this.network.removeEdge(apUpdate2, ap);
                            this.network.addEdge(apUpdate2, ap, 0);
                            this.network.addEdge(ap, apUpdate2, 0);
                        }
                        found = true;
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

    public void addResults(String nome_jogador,int pontos, String nome_mapa, int dificuladade, float time) throws UnsupportedDataTypeException {
        loadResults();
        this.resultados.add(new Player(nome_jogador,pontos, nome_mapa, new Date(), dificuladade, time));
        saveResults();
    }


}
