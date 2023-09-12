package hello.siconnectproject.exception;

public class NotFoundUserException extends RuntimeException{
    public NotFoundUserException() {
    }

    public NotFoundUserException(String message) {
        super(message);
    }

    public NotFoundUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
