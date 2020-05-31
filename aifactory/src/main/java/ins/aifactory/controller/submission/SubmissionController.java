package ins.aifactory.controller.submission;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import ins.aifactory.service.lapAdhrnc.LapAdhrnc;
import ins.aifactory.service.lapAdhrnc.LapAdhrncCriterion;
import ins.aifactory.service.lapAdhrnc.LapAdhrncService;
import ins.aifactory.service.submission.SubmissionCriterion;
import ins.aifactory.service.task.Task;
import ins.aifactory.service.task.TaskService;
import ins.core.entity.EntityPage;
import ins.core.entity.LoginInfo;
import ins.core.web.AbstractController;

@Controller
@RequestMapping("submission")
public class SubmissionController extends AbstractController{
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private LapAdhrncService lapAdhrncService;
    
    @RequestMapping(value = "search")
    public String search(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
            , SubmissionCriterion criterion, BindingResult result, Authentication authentication) {
        
        LoginInfo principal = (LoginInfo) authentication.getPrincipal();
        
        Task task = new Task();
        task.setTaskId(criterion.getTaskId());
        task = taskService.detail(task);
        
        LapAdhrncCriterion lapAdhrncCriterion = new LapAdhrncCriterion();
        lapAdhrncCriterion.setTaskId(criterion.getTaskId());
        lapAdhrncCriterion.setUserId(principal.getId());
        
        EntityPage<LapAdhrnc> lapAdhrncList = lapAdhrncService.search(lapAdhrncCriterion);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String modelNm = principal.getEmail() + "_" + sdf.format(new Date());
        
        modelMap.addAttribute(task);
        modelMap.addAttribute(criterion);
        modelMap.addAttribute("modelNm", modelNm);
        modelMap.addAttribute("result", lapAdhrncList);

        return "tls/sub/" + this.urlbase + "/search";
    }
}
