package library.exception;

public class PublicationArleadyExistsException extends RuntimeException{
    public PublicationArleadyExistsException(String message) {
        super(message);
    }
}
