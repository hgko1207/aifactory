package ins.core.web;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestMapping;

public abstract class AbstractController implements ApplicationContextAware {

    protected Log logger = LogFactory.getLog(getClass());
    
    @Resource(name = "messageSource")
    protected MessageSource messageSource;

    protected ApplicationContext applicationContext;
    protected String urlbase;

    public AbstractController() {
        this.urlbase = retrieveUrlBase();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected String retrieveUrlBase() {
        RequestMapping rm = this.getClass().getAnnotation(RequestMapping.class);
        String url = rm.value()[0];
        return url; //url.substring(1, url.indexOf("/*"));
    }

}