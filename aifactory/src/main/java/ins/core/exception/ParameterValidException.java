package ins.core.exception;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * Controller에 모델 바인딩 오류 Exception
 * @author itjeon
 *
 */
public class ParameterValidException extends RuntimeException{

    private static final long serialVersionUID = 3474439059826197312L;
    
    private String message;
    
    public ParameterValidException() {
        super();
    }

    public ParameterValidException(String message) {
        super(message);
    }

    public ParameterValidException(BindingResult result) {
        
        String msg = "";
        for( FieldError fieldErr : result.getFieldErrors() ){
            if( !StringUtils.isBlank(msg) ) msg += "\n";
            msg += fieldErr.getField() + ", " + fieldErr.getRejectedValue() + ", " + fieldErr.getDefaultMessage().substring(0, 50) + "...";
        }
        
        this.message = msg;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
