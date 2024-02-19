package library.exception;

public class UserArleadyExistsException extends RuntimeException{
    public UserArleadyExistsException(String message) {
        super(message);
    }
}
