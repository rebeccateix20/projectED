package ExceptionsGame;

public class CoexistsGhostsException extends Exception {
    /**
     * Método construtor da exceção
     */
    public CoexistsGhostsException() {
    }

    /**
     * Método responsável por devolver uma mensagem quando a exceção for lançada
     *
     * @param message Mensagem da exceção
     */
    public CoexistsGhostsException(String message) {
        super(message);
    }
}
