package Game;

import DataStructure.Exceptions.ElementNotFoundException;
import DataStructure.Exceptions.EmptyCollectionException;
import DataStructure.Exceptions.NoPathAvailable;
import DataStructure.Graph.Network.PathCostVerticeWithElement;

import java.util.Iterator;
import java.util.Scanner;

public class Main {

    private static void menuDif(){
        System.out.println("MENU DIF\n");
        System.out.println("1. BASICO\n");
        System.out.println("2. MEDIO\n");
        System.out.println("3. DIFICIL\n");
        System.out.println("0. SAIR\n");
    }

    private static void menu(){
        System.out.println("MENU \n");
        System.out.println("1. COMEÇAR\n");
        System.out.println("2. CLASSIFICAÇOES (nao implementado)\n");
        System.out.println("0. SAIR\n");
    }

    private static void menuSimOrMan(){
        System.out.println("MENU \n");
        System.out.println("1. Simulação\n");
        System.out.println("2. Manual\n");
        System.out.println("0. SAIR\n");
    }




    public static void main(String[] args) throws EmptyCollectionException, NoPathAvailable, ElementNotFoundException {
        GameManagement gameMan = new GameManagement();
        Scanner scanner = new Scanner(System.in);

            int option = -1;
            int option2, opt = -1;
            do{
                menu();
                System.out.println("Insira Opcao");
                option = scanner.nextInt();
                switch(option){
                    case 1:
                        do{
                            menuSimOrMan();
                            System.out.println("Insira o modo de jogo:");
                            opt = scanner.nextInt();
                            switch(opt){
                                case 1:
                                    do{
                                    menuDif();
                                    System.out.println("Insira Dificuldade");
                                    option2 = scanner.nextInt();
                                    switch(option2){
                                        case 1:
                                        case 2:
                                        case 3:

                                            gameMan.lerFicheiro("C:\\Users\\Rebeca\\Desktop\\edproject\\src\\main\\resources\\mapa.json", option2);

                                            if(gameMan.validateMap()){
                                                gameMan.simulation();
                                            } else {
                                                System.out.println("Mapa inválido");
                                            }

                                            break;
                                    }

                                } while(option2 != 0 && (option2<1||option2>2));
                                    break;
                                case 2:
                                    do{
                                        menuDif();
                                        System.out.println("Insira Dificuldade");
                                        option2 = scanner.nextInt();
                                        switch(option2){
                                            case 1:
                                            case 2:
                                            case 3:
                                                gameMan.lerFicheiro("C:\\Users\\Rebeca\\Desktop\\edproject\\src\\main\\resources\\mapa.json", option2);

                                                //gameMan.getNetwork().printAdjacencyMatrix();
                                                break;
                                        }

                                    } while(option2 != 0 && (option2<1||option2>2));
                                    break;
                            }
                        } while (opt != 0);
                        break;
                }

            } while(option != 0 && (option<1||option>2));

    }

}
