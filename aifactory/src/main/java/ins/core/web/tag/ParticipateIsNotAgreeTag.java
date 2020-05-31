package ins.core.web.tag;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;

public class ParticipateIsNotAgreeTag extends ParticipateAgreeTag{
    
    @Override
    public void doTag() throws JspException, IOException {
        StringWriter sw = new StringWriter();
        
        boolean isAgree = isAgree();
        if( !isAgree ){
            getJspBody().invoke(sw);
        }
        
        getJspContext().getOut().write(sw.toString());
    }
}
