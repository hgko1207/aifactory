package ins.aifactory.controller.partcptAgre;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import ins.aifactory.service.partcptAgre.PartcptAgre;
import ins.aifactory.service.partcptAgre.PartcptAgreService;
import ins.core.entity.LoginInfo;
import ins.core.exception.ParameterValidException;
import ins.core.web.AbstractController;

@Controller
@RequestMapping("partcptAgre")
public class PartcptAgreController extends AbstractController{
    
    @Autowired
    private PartcptAgreService service;
    
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public String insert(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @ModelAttribute @Valid PartcptAgre entity, BindingResult result, SessionStatus status, Authentication authentication) {
        if (result.hasErrors())
            throw new ParameterValidException(result);
        else {
            LoginInfo principal = new LoginInfo();
            if(authentication != null)
                principal = (LoginInfo) authentication.getPrincipal();
            
            entity.getUser().setUserId(principal.getId());
            this.service.insert(entity);
            return "jsonView";
        }
    }
    
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @ModelAttribute @Valid PartcptAgre entity, BindingResult result, SessionStatus status, Authentication authentication) {
        if (result.hasErrors())
            throw new ParameterValidException(result);
        else {
            LoginInfo principal = new LoginInfo();
            if(authentication != null)
                principal = (LoginInfo) authentication.getPrincipal();
            
            entity.getUser().setUserId(principal.getId());
            
            this.service.update(entity);
            
            return "jsonView";
        }
    }
}
