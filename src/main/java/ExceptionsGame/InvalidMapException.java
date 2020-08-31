package ExceptionsGame;

public class InvalidMapException extends Exception {

    /**
     * Método construtor da exceção
     */
    public InvalidMapException() {
    }

    /**
     * Método responsável por devolver uma mensagem quando a exceção for lançada
     *
     * @param message Mensagem da exceção
     */
    public InvalidMapException(String message) {
        super(message);
    }
}
