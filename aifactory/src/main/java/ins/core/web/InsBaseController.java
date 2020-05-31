package ins.core.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import ins.core.entity.EntityCriterion;
import ins.core.entity.EntityInfo;
import ins.core.exception.ParameterValidException;
import ins.core.service.InsBaseService;
import ins.core.util.ApplicationContextUtils;
import ins.core.util.ClassUtil;

public abstract class InsBaseController<T extends EntityInfo, C extends EntityCriterion, S extends InsBaseService<T, C>> extends AbstractController implements InitializingBean{

    protected Log logger = LogFactory.getLog(getClass());
    
    protected S service;
    @Resource protected Validator validator;

    protected Class<T> entityClass;
    protected Class<C> criterionClass;
    protected Class<S> serviceClass;

    protected InsBaseController(Class<T> entityClass, Class<C> criterionClass, Class<S> serviceClass) {
        super();
        this.entityClass = entityClass;
        this.criterionClass = criterionClass;
        this.serviceClass = serviceClass;
    }
    
    public void afterPropertiesSet() throws Exception {
        service = (S) ApplicationContextUtils.getBeanByType(applicationContext, serviceClass);
    }

    @RequestMapping(value = "main", method = RequestMethod.GET)
    public String main(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, C criterion, BindingResult result, SessionStatus status) {
        try {
            criterion = (C) criterionClass.newInstance();
        } catch (InstantiationException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

        modelMap.addAttribute(criterion);

        return makeViewUrl("main", this.urlbase + "/main");
    }

    @RequestMapping(value = "searchAll")
    public String searchAll(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, C criterion, BindingResult result, SessionStatus status) {
        status.setComplete();

        try {
            criterion = (C) criterionClass.newInstance();
        } catch (InstantiationException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        modelMap.addAttribute(criterion);
        modelMap.addAttribute("result", service.searchAll(criterion));

        return makeViewUrl("main", this.urlbase + "/search");
    }

    @RequestMapping(value = "search")
    public String search(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, C criterion, BindingResult result, SessionStatus status) {
        if (criterion == null) {
            try {
                criterion = (C) criterionClass.newInstance();
            } catch (InstantiationException e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }
        modelMap.addAttribute(criterion);
        modelMap.addAttribute("result", service.search(criterion));

        return makeViewUrl("main", this.urlbase + "/search");
    }

    @RequestMapping(value = "listAll")
    public String listAll(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, C criterion, BindingResult result, SessionStatus status) {
        status.setComplete();

        try {
            criterion = (C) criterionClass.newInstance();
        } catch (InstantiationException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        modelMap.addAttribute(criterion);
        modelMap.addAttribute("result", service.listAll(criterion));

        return makeViewUrl("main", this.urlbase + "/search");
    }

    @RequestMapping(value = "list")
    public String list(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, C criterion, BindingResult result, SessionStatus status) {
        if (criterion == null) {
            try {
                criterion = (C) criterionClass.newInstance();
            } catch (InstantiationException e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }
        modelMap.addAttribute(criterion);
        modelMap.addAttribute("result", service.list(criterion));

        return makeViewUrl("main", this.urlbase + "/search");
    }

    @RequestMapping(value = "detail")
    public String detail(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, T entity, BindingResult result) {
        if (entity == null) {
            throw new RuntimeException();
        }

        modelMap.addAttribute(ClassUtil.getCamelcaseSimpleName(entityClass), service.detail(entity));

        return makeViewUrl("main", this.urlbase + "/detail");
    }

    @RequestMapping(value = "insert", method = RequestMethod.GET)
    public String insertForm(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, T entity, BindingResult result) {
        try {
            entity = (T) entityClass.newInstance();
        } catch (InstantiationException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        modelMap.addAttribute(entity);

        return makeViewUrl("main", this.urlbase + "/insert");
    }

    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public String insert(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @ModelAttribute @Valid T entity, BindingResult result, SessionStatus status) {
        if (result.hasErrors())
            throw new ParameterValidException(result);
        else {
            this.service.insert(entity);
            return "jsonView";
        }
    }

    @RequestMapping(value = "update", method = RequestMethod.GET)
    public String updateForm(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, T entity, BindingResult result) {
        modelMap.addAttribute(service.detail(entity));
        return this.urlbase + "/update";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @ModelAttribute @Valid T entity, BindingResult result, SessionStatus status) {
        if (result.hasErrors())
            throw new ParameterValidException(result);
        else {
            this.service.update(entity);
            
            return "jsonView";
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String delete(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, T entity, BindingResult result) {
        this.service.delete(entity);
        
        return "jsonView";
    }
    
    protected String makeViewUrl(String viewType, String url){
        String viewUrl = "";
        if( viewType.equals("main") ){
            viewUrl = "tls/main/"+getMainUrlString();
        }else{
            viewUrl = "tls/sub/"+getSubUrlString();
        }
        
        if( viewUrl.equalsIgnoreCase("jsonView") ){
            viewUrl = "jsonView";
        }else{
            viewUrl = viewUrl + url;
        }
        
        return viewUrl;
    }
    
    /**
     * Tiles 구분용 Main Page URL Prefix String
     * 
     * @return
     */
    protected abstract String getMainUrlString();
    
    /**
     * Tiles 구분용 Sub Page URL Prefix String
     *  
     * @return
     */
    protected abstract String getSubUrlString();
    
    
}