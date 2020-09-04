package Game;

import DataStructure.list.OrderedList.ArrayOrderedList;

import javax.activation.UnsupportedDataTypeException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClassificationManagement {
    private ArrayOrderedList<Player> resultados;
    private String mapName;
    private int dificuldade;


    /**
     * Metodo construtor
     * @param mapName nome do mapa
     * @param dificuldade dificuldade
     */
    public ClassificationManagement(String mapName, int dificuldade) {
        this.mapName = mapName;
        this.resultados = new ArrayOrderedList<>();
        this.dificuldade = dificuldade;
    }

    /**
     * Metodo construtor
     * @param mapName nome do mapa
     */
    public ClassificationManagement(String mapName) {
        this.mapName = mapName;
        this.resultados = new ArrayOrderedList<>();
    }


    /**
     * Método que le os players do ficheiro
     */
    public void loadResults() {
        File file = new File("resources/results/"+this.mapName+".csv");
        if(file.exists()){
            try {
                Scanner scanner = new Scanner(file);
                scanner.nextLine();
                while (scanner.hasNextLine()) {
                    String[] resultado = scanner.nextLine().split(",");
                    this.resultados.add(new Player(resultado[0], Integer.parseInt(resultado[1]), Float.parseFloat(resultado[4]), Integer.parseInt(resultado[3])));
                }
                scanner.close();

                System.out.println("Resultados carregados");
            } catch (FileNotFoundException e) {
                System.out.println("Ficheiro nao existe");
            }catch (NoSuchElementException e){

            } catch (Exception e) {
                System.out.println(e.toString() + " LOAD");
                System.out.println("Erro interno");
            }
        }

    }

    /**
     * Método responsavel por adicionar os players ao ficheiro
     */
    public void saveResults() {
        File file = new File("resources/results/"+this.mapName+".csv");
        System.out.println(this.resultados.toString());
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("nome,pontos,mapa,dificuldade,time");
            Iterator<Player> iterator = this.resultados.iterator();
            while(iterator.hasNext()){
                Player resultado = iterator.next();
                fileWriter.write("\n" + resultado.getNome() + "," + resultado.getPontos() + "," + this.mapName + "," + resultado.getDificuldade() + "," + resultado.getEstimateTime());
            }
            fileWriter.close();

            System.out.println("Resultados Guardados");
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado");
        } catch (Exception e) {
            System.out.println(e.toString() + " SAVE");
            System.out.println("Erro interno");
        }
    }

    /**
     * Metodo repsonsavel por adicionar um utilizador às classificacoes
     * @param player
     * @throws UnsupportedDataTypeException
     */
    public void addPlayer(Player player) throws UnsupportedDataTypeException {
        loadResults();
        this.resultados.add(player);
        saveResults();
    }

    /**
     * Metodo responsavel por imprimir na consola as classificaçoes
     */
    public void printResults(){
        loadResults();
        System.out.println("RESULTADOS DO MAPA " + this.mapName);
        Iterator<Player> players = this.resultados.iterator();
        while(players.hasNext()){
            System.out.println(players.next().toString());
        }
    }

}
