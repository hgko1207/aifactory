package ins.core.exception;

/**
 * 활성화 중인 Lap이 없을 경우의 오류
 * @author itjeon
 *
 */
public class ActiveLapNoExistException extends RuntimeException{

    
    private static final long serialVersionUID = -2347766476586123947L;
    private String message;
    
    public ActiveLapNoExistException() {
        super();
    }

    public ActiveLapNoExistException(String message) {
        super(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
