package Game;

import DataStructure.Exceptions.ElementNotFoundException;
import DataStructure.Exceptions.EmptyCollectionException;
import DataStructure.Exceptions.NoPathAvailable;
import ExceptionsGame.InvalidMapException;
import org.json.simple.parser.ParseException;

import javax.activation.UnsupportedDataTypeException;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

public class Main {

    private static void menuDif() {
        System.out.println("MENU DIF\n");
        System.out.println("1. BASICO");
        System.out.println("2. MEDIO");
        System.out.println("3. DIFICIL");
        System.out.println("0. SAIR");
    }

    private static void menu() {
        System.out.println("MENU \n");
        System.out.println("1. COMEÇAR");
        System.out.println("2. CLASSIFICAÇOES");
        System.out.println("0. SAIR");
    }

    private static void menuSimOrMan() {
        System.out.println("MENU \n");
        System.out.println("1. Simulação");
        System.out.println("2. Manual");
        System.out.println("0. SAIR");
    }


    public static void main(String[] args) throws EmptyCollectionException, NoPathAvailable, ElementNotFoundException, IOException, InvalidMapException, ParseException {
        GameManagement gameMan = new GameManagement();
        Scanner scanner = new Scanner(System.in);
        Date data = new Date();
        Player p1 = new Player("rebeca", 100, "mapa.json", data, 1);

        int option = -1;
        int option2, opt = -1;
        do {
            gameMan.lerFicheiro("resources/notExists.json", 1);
            menu();
            System.out.println("Insira Opcao");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    do {
                        menuSimOrMan();
                        System.out.println("Insira o modo de jogo:");
                        opt = scanner.nextInt();
                        switch (opt) {
                            case 1:
                                do {
                                    menuDif();
                                    System.out.println("Insira Dificuldade");
                                    option2 = scanner.nextInt();
                                    switch (option2) {
                                        case 1:
                                        case 2:
                                        case 3:
                                            gameMan.lerFicheiro("resources/mapa.json", option2);
                                            gameMan.simulation();
                                            break;
                                    }

                                } while (option2 != 0 && (option2 < 1 || option2 > 2));
                                break;
                            case 2:
                                do {
                                    menuDif();
                                    System.out.println("Insira Dificuldade");
                                    option2 = scanner.nextInt();
                                    switch (option2) {
                                        case 1:
                                        case 2:
                                        case 3:
                                            gameMan.lerFicheiro("resources/mapa.json", option2);
                                            gameMan.gameplay(p1);
                                            break;
                                    }
                                } while (option2 != 0 && (option2 < 1 || option2 > 2));
                                break;
                        }
                    } while (opt != 0);
                    break;
                case 2:
                    gameMan.loadResults();
                    Iterator<Player> results = gameMan.getResultados().iterator();
                    while (results.hasNext()) {
                        Player player = results.next();
                        System.out.println(player.toString());
                    }
                    break;
            }

        } while (option != 0 && (option < 1 || option > 2));

    }

}
