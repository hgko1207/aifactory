package ins.aifactory.controller.leaderboard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import ins.aifactory.service.lap.LapCriterion;
import ins.aifactory.service.lap.LapService;
import ins.aifactory.service.lapAdhrnc.LapAdhrnc;
import ins.aifactory.service.lapAdhrnc.LapAdhrncCriterion;
import ins.aifactory.service.lapAdhrnc.LapAdhrncService;
import ins.aifactory.service.leaderboard.LeaderboardCriterion;
import ins.aifactory.service.task.Task;
import ins.aifactory.service.task.TaskService;
import ins.core.entity.EntityPage;
import ins.core.web.AbstractController;

@Controller
@RequestMapping("leaderboard")
public class LeaderboardController extends AbstractController{
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private LapAdhrncService lapAdhrncService;
    
    @Autowired
    private LapService lapService;
    
    @RequestMapping(value = "search")
    public String search(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, LeaderboardCriterion criterion, BindingResult result, SessionStatus status) {
        Task task = new Task();
        task.setTaskId(criterion.getTaskId());
        task = taskService.detail(task);
        
        LapCriterion lapCriterion = new LapCriterion();
        lapCriterion.setTaskId(task.getTaskId());
        int totalLapCount = lapService.searchCount(lapCriterion);
        
        modelMap.addAttribute(task);
        modelMap.addAttribute("criterion", criterion);
        modelMap.addAttribute("totalLapCount", totalLapCount);

        return "tls/sub/" + this.urlbase + "/search";
    }
    
    @RequestMapping(value = "searchAjax")
    public String searchAjax(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, LeaderboardCriterion criterion, BindingResult result, SessionStatus status) {
        LapAdhrncCriterion lapAdhrncCriterion = new LapAdhrncCriterion();
        lapAdhrncCriterion.setTaskId(criterion.getTaskId());
        if(criterion.isPublc()){
            lapAdhrncCriterion.setResultSbmisnMthdCode("0000");
        }else{
            lapAdhrncCriterion.setResultSbmisnMthdCode("0001");
        }
        lapAdhrncCriterion.getPagingInfo().setPageNo(criterion.getPagingInfo().getPageNo());
        EntityPage<LapAdhrnc> lapAdhrncList = lapAdhrncService.search(lapAdhrncCriterion);
        
        LapCriterion lapCriterion = new LapCriterion();
        lapCriterion.setTaskId(criterion.getTaskId());
        
        modelMap.addAttribute("result", lapAdhrncList);

        return "jsonView";
    }    
}
