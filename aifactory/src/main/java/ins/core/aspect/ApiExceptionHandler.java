package ins.core.aspect;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import ins.core.exception.ActiveLapNoExistException;
import ins.core.exception.IncorrectPasswordException;
import ins.core.exception.ParameterValidException;

@ControllerAdvice
public class ApiExceptionHandler {
 
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);
            
    @Resource(name = "messageSource")
    private MessageSource messageSource;
    
    @ExceptionHandler({ParameterValidException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleParameterValidException(ParameterValidException ex, HttpServletRequest request) {
        String code = ApiConstants.CODE_BIND_VALID_ERROR;
        String message = ex.getMessage();
        
        ModelAndView mav = new ModelAndView();
        mav.addObject(ApiConstants.CODE, code);
        mav.addObject(ApiConstants.MESSAGE, message);
        mav.setViewName("jsonView");
        
        return mav;
    }
    
    @ExceptionHandler({Exception.class, RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleException(Exception ex, HttpServletRequest request) {
        
        ex.printStackTrace();
        
        ModelAndView mav = new ModelAndView();
        
        if (isAjaxRequest(request)) {
            String code;
            String message;
            
            if (ex instanceof org.springframework.jdbc.BadSqlGrammarException || ex instanceof org.mybatis.spring.MyBatisSystemException) {
                code = ApiConstants.CODE_ERR_DB;
                message = "데이터 베이스 오류가 발생하였습니다.";
            } else if (ex instanceof org.springframework.transaction.TransactionSystemException || ex instanceof org.springframework.transaction.CannotCreateTransactionException) {
                code = ApiConstants.CODE_ERR_DB_CONN;
                message = "데이터 베이스 연결 오류가 발생하였습니다.";
            } else if (ex instanceof org.springframework.dao.DuplicateKeyException) {
                code = ApiConstants.CODE_ERR_DUPLICATE;
                message = "중복키 오류가 발생하였습니다.";
            } else if (ex instanceof ActiveLapNoExistException) {
                code = ApiConstants.CODE_ERR_NO_ACTIVE_LAP;
                message = "진행중인 LAP이 존재하지 않습니다.";
            } else if (ex instanceof IncorrectPasswordException) {
                code = ApiConstants.CODE_ERR_INCORRECT_PASSWORD;
                message = "Incorrect Password.";
            } else {
                code = ApiConstants.CODE_ERROR;
                message = "알 수 없는 오류가 발생하였습니다.";
            }
            
            mav.addObject(ApiConstants.CODE, code);
            mav.addObject(ApiConstants.MESSAGE, message);
            mav.setViewName("jsonView");
            
            LOGGER.error("##############################################################");
            LOGGER.error("EXCEPTION_CLASS="+ex.getClass().getCanonicalName());
            LOGGER.error("CODE="+code);
            LOGGER.error("MESSAGE="+message);
            LOGGER.error("EXCEPTION_MESSAGE="+ex.getMessage());
            LOGGER.error("##############################################################");
        }else{
            throw new InternalServerErrorException();
        }
        
        return mav;
    }
    
    private boolean isAjaxRequest(HttpServletRequest req) {
        return req.getHeader("AJAX") != null && req.getHeader("AJAX").equals(Boolean.TRUE.toString());
    }
    
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public class InternalServerErrorException extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }
}