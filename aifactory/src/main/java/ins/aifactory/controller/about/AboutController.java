package ins.aifactory.controller.about;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import ins.aifactory.service.about.Post;
import ins.aifactory.service.about.PostCriterion;
import ins.aifactory.service.about.PostService;
import ins.aifactory.service.cmmnFile.CmmnFile;
import ins.aifactory.service.cmmnFile.CmmnFileService;

import ins.core.entity.EntityPage;
import ins.core.entity.LoginInfo;
import ins.core.exception.ParameterValidException;
import ins.core.util.StringUtil;
import ins.core.web.AbstractController;

@Controller
@RequestMapping("about")
public class AboutController extends AbstractController {
    
    @Autowired
    @Qualifier("AboutPostService")
    protected PostService service;
    
    @Autowired
    private CmmnFileService cmmnFileService;
   
    @Resource protected Validator validator;

    @RequestMapping(value="search")
    public String search(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, PostCriterion criterion, BindingResult result, SessionStatus status, Authentication authentication){
        LoginInfo principal = new LoginInfo();
        if(authentication != null)
            principal = (LoginInfo) authentication.getPrincipal();
        
        if (criterion == null) {
            criterion = new PostCriterion();
        }
        
        criterion.setUserId(principal.getId());
        modelMap.addAttribute(criterion);
        
        return "tls/main/" + this.urlbase + "/search";
        
    }
    @RequestMapping(value = "searchAjax")
    public String searchAjax(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, PostCriterion criterion, BindingResult result, SessionStatus status, Authentication authentication) {
        LoginInfo principal = new LoginInfo();
        if(authentication != null)
            principal = (LoginInfo) authentication.getPrincipal();
        
        if (criterion == null) {
            criterion = new PostCriterion();
        }
        
        criterion.setUserId(principal.getId());
        EntityPage<Post> postList = service.search(criterion);
        
        modelMap.addAttribute("result", postList);
        
        return "jsonView";
    }
    @RequestMapping(value="insert",method=RequestMethod.GET)
    public String insertForm(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestParam String mode){
        Post post = new Post();
        post.setPostCt(mode);
        modelMap.addAttribute(post);

        return "tls/sub/" + this.urlbase + "/insert";
    }
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public String insert(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @ModelAttribute Post entity,BindingResult result, SessionStatus status){
        validator.validate(entity, result);
        if (result.hasErrors())
            throw new ParameterValidException(result);
        else {
            entity.setPostDc(StringUtil.convertTuiEditorSave(entity.getPostDc()));
            this.service.insert(entity);
        
        return "jsonView";
        }
    }
    @RequestMapping(value = "detail")
    public String detail(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Post entity, BindingResult result) {
        if (entity == null) {
            throw new RuntimeException();
        }
        
        // Task 상세 정보 조회
        Post post = service.detail(entity);
        
        // 첨부파일 조회
        if( post.getCmmnFile() != null && !StringUtils.isBlank(post.getCmmnFile().getAtchFileId() ) ){
            CmmnFile cmmnFile = cmmnFileService.detail(post.getCmmnFile());
            post.setCmmnFile(cmmnFile);
        }
        
        modelMap.addAttribute(post);

        return "tls/sub/" + this.urlbase + "/detail";
    }
    
    @RequestMapping(value = "updateForm", method = RequestMethod.POST)
    public String updateForm(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Post entity, BindingResult result) {
        
        if (entity == null) {
            throw new RuntimeException();
        }
        
        // Task 상세 정보 조회
        Post post = service.detail(entity);
        
        // 첨부파일 조회
        if( post.getCmmnFile() != null && !StringUtils.isBlank(post.getCmmnFile().getAtchFileId() ) ){
            CmmnFile cmmnFile = cmmnFileService.detail(post.getCmmnFile());
            post.setCmmnFile(cmmnFile);
        }
        System.out.println("변");
        System.out.println(post.getCmmnFile());
        modelMap.addAttribute(post);

        return "tls/sub/" + this.urlbase + "/update";
    }
    
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @ModelAttribute Post entity, BindingResult result, SessionStatus status) {
        validator.validate(entity, result);
        if (result.hasErrors())
            throw new ParameterValidException(result);
        else {
            entity.setPostDc(StringUtil.convertTuiEditorSave(entity.getPostDc()));
            this.service.update(entity);
            return "jsonView";
        }
    }
}
