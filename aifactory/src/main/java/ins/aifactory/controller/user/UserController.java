package ins.aifactory.controller.user;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import ins.aifactory.service.user.User;
import ins.aifactory.service.user.UserService;
import ins.core.entity.LoginInfo;
import ins.core.exception.IncorrectPasswordException;
import ins.core.exception.ParameterValidException;
import ins.core.web.AbstractController;

@Controller
@RequestMapping("user")
public class UserController extends AbstractController{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    protected UserService service;
    
    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;
   
    @Resource protected Validator validator;

    @RequestMapping(value = "insert", method = RequestMethod.GET)
    public String insertForm(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, User entity, BindingResult result) {
        entity = new User();
        modelMap.addAttribute(entity);

        return this.urlbase + "/insert";
    }
    
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public void insert(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @ModelAttribute User entity, BindingResult result, SessionStatus status) {
        validator.validate(entity, result);
        if (result.hasErrors())
            throw new ParameterValidException(result);
        else {
            if( "Y".equals(entity.getAgreeTerm()) ){
                entity.setIndvdlinfoProcessPolcyAgreYn("Y");
                entity.setUseStplatAgreYn("Y");
            }else{
                throw new ParameterValidException("이용약관 및 개인정보보호방침에 동의하여 주십시오.");
            }
            
            String passwd = entity.getUserPwd();
            this.service.insert(entity);
            
            // 로그인 처리
            this.service.login(request, response, entity.getUserEmail(), passwd);
            
            try {
                response.sendRedirect("../");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    @RequestMapping(value = "updateForm", method = RequestMethod.GET)
    public String updateForm(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, User entity, BindingResult result, Authentication authentication) {
        LoginInfo principal = new LoginInfo();
        if(authentication != null)
            principal = (LoginInfo) authentication.getPrincipal();
        
        User user = new User();
        user.setUserId(principal.getId());
        
        String encPwd = service.getPwd(user);
        boolean rslt = bCryptPasswordEncoder.matches(entity.getUserPwd(), encPwd);
        
        if( !rslt ){
            throw new IncorrectPasswordException();
        }
        
        modelMap.addAttribute(service.detail(user));

        return this.urlbase + "/update";
    }
    
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @ModelAttribute User entity, BindingResult result, SessionStatus status, Authentication authentication) {
        validator.validate(entity, result);
        if (result.hasErrors())
            throw new ParameterValidException(result);
        else {
            LoginInfo principal = new LoginInfo();
            if(authentication != null)
                principal = (LoginInfo) authentication.getPrincipal();
            
            String passwd = entity.getUserPwd();
            entity.setUserId(principal.getId());
            this.service.update(entity);
        }
        
        return "jsonView";
    }
    
    @RequestMapping(value = "chkPwd")
    public String chkPwd(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestParam(required=true) String pwd, Authentication authentication) {
        LoginInfo principal = new LoginInfo();
        if(authentication != null)
            principal = (LoginInfo) authentication.getPrincipal();
        
        User user = new User();
        user.setUserId(principal.getId());
        String encPwd = service.getPwd(user);
        
        boolean rslt = bCryptPasswordEncoder.matches(pwd, encPwd);
        if( !rslt ){
            throw new IncorrectPasswordException();
        }
        
        return "jsonView";
    }
    
    @ResponseBody
    @RequestMapping(value = "chkPwdModifyValid", method = RequestMethod.POST)
    public boolean chkPwdModifyValid(@RequestParam(required=true, value="oldUserPwd") String userPwd, Authentication authentication) {
        LoginInfo principal = new LoginInfo();
        if(authentication != null)
            principal = (LoginInfo) authentication.getPrincipal();
        
        User user = new User();
        user.setUserId(principal.getId());
        String encPwd = service.getPwd(user);
        
        boolean rslt = bCryptPasswordEncoder.matches(userPwd, encPwd);
        
        return rslt;
    }
    
    @ResponseBody
    @RequestMapping(value = "chkDuplicateEmail", method = RequestMethod.POST)
    public boolean chkDuplicateEmail(String userEmail, Authentication authentication) {
        boolean rslt = false;
        User user = new User();
        LoginInfo principal = new LoginInfo();
        if(authentication != null){
            principal = (LoginInfo) authentication.getPrincipal();
            user.setUserId(principal.getId());
            user = service.detail(user);
            if(user.getUserEmail().equalsIgnoreCase(userEmail)){
                rslt = true;
            }
        }
        
        if( !rslt ) {
            user.setUserEmail(userEmail);
            rslt = service.chkDuplicateEmail(user);
        }
        
        return rslt; 
    }
}
