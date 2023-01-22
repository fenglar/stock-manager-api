package pl.marcin.stockmanagerapi.exception;

public class UnknownException extends RuntimeException {
    public UnknownException(String message) {
        super(message);
    }
}
