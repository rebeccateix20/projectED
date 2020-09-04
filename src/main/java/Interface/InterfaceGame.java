package Interface;

import DataStructure.Exceptions.ElementNotFoundException;
import DataStructure.Exceptions.EmptyCollectionException;
import DataStructure.Exceptions.NoPathAvailable;
import DataStructure.list.UnorderedList.ArrayUnorderedList;
import ExceptionsGame.InvalidMapException;
import Game.ClassificationManagement;
import Game.GameManagement;
import Game.Player;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class InterfaceGame {
    private String path;
    private String pathClassification;
    private ClassificationManagement classMan;

    private void menuDif() {
        System.out.println("MENU DIF\n");
        System.out.println("1. BASICO");
        System.out.println("2. MEDIO");
        System.out.println("3. DIFICIL");
        System.out.println("0. SAIR");
    }

    private void menu() {
        System.out.println("MENU \n");
        System.out.println("1. COMEÇAR");
        System.out.println("0. SAIR");
    }


    private void menuSimOrManOrClass() {
        System.out.println("MENU \n");
        System.out.println("1. Simulação");
        System.out.println("2. Manual");
        System.out.println("3. Classificação");
        System.out.println("0. SAIR");
    }

    private void mapChoice() {
        int opSec;
        File directory = new File("resources/mapas/");
        int i;

        String[] listFiles = directory.list(); // Lista dos ficheiros na pasta

        do {
            for (i = 0; i < listFiles.length; i++) {
                System.out.println("\n" + (i + 1) + ": " + listFiles[i]);
            }

            System.out.println("\nEscolhe o mapa: ");
            Scanner s = new Scanner(System.in);
            opSec = s.nextInt();
        } while (opSec < 1 || opSec > (i));

        this.path = "resources/mapas/" + listFiles[(opSec - 1)];
    }

    private void mapChoiceForClassification() {
        int opSec;
        File directory = new File("resources/results/");
        int i;

        String[] listFiles = directory.list(); // Lista dos ficheiros na pasta

        do {
            for (i = 0; i < listFiles.length; i++) {
                System.out.println("\n" + (i + 1) + ": " + listFiles[i]);
            }

            System.out.println("\nEscolhe o mapa para ver as classificações: ");
            Scanner s = new Scanner(System.in);
            opSec = s.nextInt();
        } while (opSec < 1 || opSec > (i));

        this.pathClassification = listFiles[(opSec - 1)];
    }



    public InterfaceGame() throws EmptyCollectionException, ParseException, ElementNotFoundException, IOException, NoPathAvailable, InvalidMapException {
        GameManagement gameMan = new GameManagement();
        int opcao, op2, op3,op4;
        String nomePlayer;
        do{
            this.menu();
            System.out.println("Opcao:");
            Scanner scan = new Scanner(System.in);
            opcao = scan.nextInt();
        } while(opcao != 1);
        switch(opcao){
            case 1:
                do{
                    this.menuSimOrManOrClass();
                    Scanner scan3 = new Scanner(System.in);
                    System.out.print("Escolha a opcao: ");
                    op2 = scan3.nextInt();
                } while (op2!=0 && (op2<1 || op2>3));
                switch(op2){
                    case 1:
                        do {
                            this.mapChoice();
                            menuDif();
                            Scanner scan4 = new Scanner(System.in);
                            System.out.print("Insira Dificuldade");
                            op3 = scan4.nextInt();
                            switch (op3) {
                                case 1:
                                case 2:
                                case 3:
                                    gameMan.lerFicheiro(this.path, op3);
                                    System.out.println(gameMan.simulation());
                                    break;
                            }
                        } while (op3 != 0 && (op3 < 1 || op3 > 2));
                        break;
                    case 2:
                        do {
                            this.mapChoice();
                            menuDif();
                            System.out.println("Insira Dificuldade");
                            Scanner scan5 = new Scanner(System.in);
                            op4 = scan5.nextInt();

                        } while (op4 != 0 && (op4 < 1 || op4 > 3));
                        switch (op4) {
                            case 1:
                            case 2:
                            case 3:
                                System.out.println("Player: ");
                                Scanner scan2 = new Scanner(System.in);
                                nomePlayer = scan2.next();
                                Player player = new Player(nomePlayer, op4);

                                gameMan.lerFicheiro(this.path, op4);
                                ClassificationManagement classMan = new ClassificationManagement(gameMan.getMapa().getNome(), op4);
                                gameMan.gameplay(player);
                                classMan.addPlayer(player);
                                break;
                        }
                        break;

                    case 3:

                        mapChoiceForClassification();
                        String map = this.pathClassification.split("\\.")[0];
                        ClassificationManagement classMan2 = new ClassificationManagement(map);
                        classMan2.printResults();
                        break;
                }


        }
    }

}
