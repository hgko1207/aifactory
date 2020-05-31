package ins.core.web;

import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

public class InsWebBindingInitializer implements WebBindingInitializer {

    public void initBinder(WebDataBinder binder, WebRequest request) {
//      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//      dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new InsWebBindDateFormat(), false));
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
    }

}
