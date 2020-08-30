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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.Random;



public class GameManagement {
    private Mapa mapa;
    private ArrayDirectedNetwork<Aposento> network;
    private ArrayOrderedList<Player> resultados;
    private int classificacao_pontos;
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

    public int getClassificacao_pontos() {
        return classificacao_pontos;
    }

    public GameManagement(Mapa mapa, ArrayDirectedNetwork<Aposento> network, ArrayOrderedList<Player> resultados, int classificacao_pontos) {
        this.mapa = mapa;
        this.network = network;
        this.resultados = resultados;
        this.classificacao_pontos = classificacao_pontos;
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
                    ligacoes_aposento.addToFront(ligacao);
                }
                aposentos.addToFront(new Aposento(nome_aposento, fantasma, ligacoes_aposento));
            }
            this.mapa = new Mapa(nome, pontos, aposentos);
            this.classificacao_pontos = pontos;
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

    public void createNetwork() throws ElementNotFoundException {
        network = new ArrayDirectedNetwork<>();

        ArrayUnorderedList<String> ligacoesEntrada = new ArrayUnorderedList<>();
        ArrayUnorderedList<String> ligacoesSaida = new ArrayUnorderedList<>();
        boolean found = false;

        Iterator<Aposento> it = this.mapa.getAposentos().iterator();

        while(it.hasNext() && !found){
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

        network.addVertex(entrada);
        for (Aposento aposento : mapa.getAposentos()) {
            network.addVertex(aposento);
        }
        network.addVertex(exterior);

/*
        int n_aposentos_sem_fantasma = mapa.getAposentos().size();
        for (Aposento aposento : mapa.getAposentos()) {
            if (aposento.getFantasma() > 0)
                n_aposentos_sem_fantasma--;
        }

        Random bonusRandom = new Random();
        int bonus = bonusRandom.nextInt((3 - 1) + 1) + 1; //para sortear qual o bonus entre 1-3
        switch(bonus){
            case 1:
                Random random2 = new Random();
                int i2 = random2.nextInt(n_aposentos_sem_fantasma);
                for (Aposento aposento : mapa.getAposentos()) {
                    if (i2 == 0) {
                        aposento.setFantasma(-25);
                        //System.out.println(aposento.getNome());
                    }
                    i2--;
                }
                break;
            case 2:
                Random random = new Random();

                int i = random.nextInt(n_aposentos_sem_fantasma);
                int danomax=0;
                for (Aposento aposento : mapa.getAposentos()) {
                    if (aposento.getFantasma() > danomax) {
                        danomax= aposento.getFantasma();
                    }
                }

                Random random1 = new Random();
                int dano= random1.nextInt(danomax);
                for (Aposento aposento : mapa.getAposentos()) {
                    if (i == 0) {
                        aposento.setFantasma(-dano);
                        //System.out.println(aposento.getNome());
                    }
                    i--;
                }
                break;
            case 3:
                break;
        }
*/
/*
        for (Aposento obj_aposento : mapa.getAposentos()) {
            System.out.println(obj_aposento.getNome());
        }*/
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
        Iterator<PathCostVerticeWithElement<Aposento>> iterator= this.network.iteratorShortestPathWeight(this.searchDivision("entrada"), this.searchDivision("exterior"));
        while (iterator.hasNext()){
            PathCostVerticeWithElement<Aposento> p = iterator.next();
            Aposento ap = p.getCurrent();
            custo += ap.getFantasma();
        }
        if(custo<this.mapa.getPontos()){
            return true;
        } else {
            return false;
        }
    }

    public void simulation() throws EmptyCollectionException, ElementNotFoundException {
        Iterator<Aposento> iterator= this.network.iteratorShortestPath(this.searchDivision("entrada"), this.searchDivision("exterior"));
        while (iterator.hasNext()){
            Aposento ap = iterator.next();
            System.out.print(ap.getNome() + " - ");
        }
    }

}
