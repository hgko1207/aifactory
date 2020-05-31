package ins.aifactory.controller.qna;

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

import ins.aifactory.service.qna.Qna;
import ins.aifactory.service.qna.QnaCriterion;
import ins.aifactory.service.qna.QnaService;
import ins.aifactory.service.task.Task;
import ins.aifactory.service.task.TaskService;
import ins.core.entity.EntityPage;
import ins.core.entity.LoginInfo;
import ins.core.exception.ParameterValidException;
import ins.core.util.StringUtil;
import ins.core.web.AbstractController;

@Controller
@RequestMapping("qna")
public class QnaController extends AbstractController{
    
    @Autowired
    private QnaService service;
    
    @Autowired
    private TaskService taskService;
    
    @RequestMapping(value = "search")
    public String search(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, QnaCriterion criterion, BindingResult result, SessionStatus status, Authentication authentication) {
        if (criterion == null) {
            criterion = new QnaCriterion();
        }
        
        // Task 상세 정보 조회
        Task task = new Task(criterion.getTaskId());
        task = taskService.detail(task);
        
        modelMap.addAttribute(task);
        modelMap.addAttribute(criterion);
        
        return "tls/sub/" + this.urlbase + "/search";
    }
    
    @RequestMapping(value = "searchAjax")
    public String searchAjax(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, QnaCriterion criterion, BindingResult result, SessionStatus status, Authentication authentication) {
        LoginInfo principal = new LoginInfo();
        if(authentication != null)
            principal = (LoginInfo) authentication.getPrincipal();
        
        if (criterion == null) {
            criterion = new QnaCriterion();
        }
        
        EntityPage<Qna> qnaList = service.search(criterion);
        
        modelMap.addAttribute("result", qnaList);
        
        return "jsonView";
    }
    
    @RequestMapping(value = "detail")
    public String detail(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Qna entity, BindingResult result) {
        if (entity == null) {
            throw new RuntimeException();
        }

        modelMap.addAttribute(service.detail(entity));

        return "tls/one/" + this.urlbase + "/detail";
    }
    
    @RequestMapping(value = "insert", method = RequestMethod.GET)
    public String insertForm(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Qna entity) {
        modelMap.addAttribute(entity);

        return "tls/one/" + this.urlbase + "/insert";
    }
    
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public String insert(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @ModelAttribute @Valid Qna entity, BindingResult result, SessionStatus status) {
        if (result.hasErrors())
            throw new ParameterValidException(result);
        else {
            entity.setQnaSj(StringUtil.escapeHtml(entity.getQnaSj()));
            entity.setQnaCn(StringUtil.convertTuiEditorSave(entity.getQnaCn()));
            this.service.insert(entity);
            return "jsonView";
        }
    }
    
    @RequestMapping(value = "update", method = RequestMethod.GET)
    public String updateForm(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Qna entity, BindingResult result) {
        modelMap.addAttribute(service.detail(entity));
        return "tls/one/" + this.urlbase + "/update";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @ModelAttribute @Valid Qna entity, BindingResult result, SessionStatus status) {
        if (result.hasErrors())
            throw new ParameterValidException(result);
        else {
            entity.setQnaSj(StringUtil.escapeHtml(entity.getQnaSj()));
            entity.setQnaCn(StringUtil.convertTuiEditorSave(entity.getQnaCn()));
            this.service.update(entity);
            
            return "jsonView";
        }
    }
    
    @RequestMapping(value = "updateAnswer", method = RequestMethod.GET)
    public String updateAnswerForm(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Qna entity, BindingResult result) {
        modelMap.addAttribute(service.detail(entity));
        return "tls/one/" + this.urlbase + "/updateAnswer";
    }
    
    @RequestMapping(value = "updateAnswer", method = RequestMethod.POST)
    public String updateAnswer(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @ModelAttribute @Valid Qna entity, BindingResult result, SessionStatus status) {
        if (result.hasErrors())
            throw new ParameterValidException(result);
        else {
            entity.setQnaAnswer(StringUtil.convertTuiEditorSave(entity.getQnaAnswer()));
            this.service.updateAnswer(entity);
            
            return "jsonView";
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String delete(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Qna entity, BindingResult result) {
        this.service.delete(entity);
        
        return "jsonView";
    }
}
