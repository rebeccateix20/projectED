import DataStructure.Exceptions.ElementNotFoundException;
import DataStructure.Exceptions.EmptyCollectionException;
import DataStructure.Exceptions.NoPathAvailable;
import ExceptionsGame.InvalidMapException;
import Game.GameManagement;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.FileNotFoundException;
import java.io.IOException;

public class lerFicheiroTest {

    private GameManagement game;

    @BeforeEach
    public void setUp() {
        this.game = new GameManagement();
    }

    @Test
    public void NotJSONFileTest() {
        Assertions.assertThrows(Exception.class, () -> this.game.lerFicheiro("resources/test.txt", 1));
    }

    @Test
    public void EmptyPathTest() {
        Assertions.assertThrows(InvalidMapException.class, () -> this.game.lerFicheiro("", 1));
    }

    @Test
    public void DificuldadeMaiorQue3Test() {
        Assertions.assertThrows(InvalidMapException.class, () -> this.game.lerFicheiro("resources/mapa.json", 4));
    }

    @Test
    public void DificuldadeMenorQue1Test() {
        Assertions.assertThrows(InvalidMapException.class, () -> this.game.lerFicheiro("resources/mapa.json", 0));
    }

    @Test
    public void MapaSemConexaoTest() {
        Assertions.assertThrows(InvalidMapException.class, () -> this.game.lerFicheiro("resources/mapaSemConexao.json", 1));
    }

    @Test
    public void MapaSemEntradaTest() {
        Assertions.assertThrows(InvalidMapException.class, () -> this.game.lerFicheiro("resources/mapaInvalidoSemEntrada.json", 1));
    }

    @Test
    public void MapaSemSaidaTest() {
        Assertions.assertThrows(InvalidMapException.class, () -> this.game.lerFicheiro("resources/mapaInvalidoSemSaida.json", 1));
    }

    @Test
    public void MapaSemCaminhoPossivelParaPassarComVidaTest() {
        Assertions.assertThrows(InvalidMapException.class, () -> this.game.lerFicheiro("resources/mapaMuitoCaro.json", 1));
    }

    @Test
    public void MapaFantasmasTodosAposentosNivel1Test() throws ElementNotFoundException, IOException, InvalidMapException, NoPathAvailable, EmptyCollectionException, ParseException {
        this.game.lerFicheiro("resources/mapaTudoFantasmas.json", 1);
    }

    @Test
    public void MapaFantasmasTodosAposentosNivel2Test(){
        Assertions.assertThrows(InvalidMapException.class, () -> this.game.lerFicheiro("resources/mapaTudoFantasmas.json", 2));
    }

    @Test
    public void FileNotExistTest() {
        Assertions.assertThrows(FileNotFoundException.class, () -> this.game.lerFicheiro("resources/notExists.json", 1));
    }

    @Test
    public void EntradaComFantasmaTest() throws EmptyCollectionException, ParseException, ElementNotFoundException, IOException, NoPathAvailable, InvalidMapException {
        this.game.lerFicheiro("resources/mapaFantasmaEntrada.json", 1);
    }


}
