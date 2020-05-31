package ins.core.exception;

/**
 * 비밀번호  오류
 * @author itjeon
 *
 */
public class IncorrectPasswordException extends RuntimeException{

    
    private static final long serialVersionUID = 3703046802431562943L;
    private String message;
    
    public IncorrectPasswordException() {
        super();
    }

    public IncorrectPasswordException(String message) {
        super(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
