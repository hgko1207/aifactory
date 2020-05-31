package ins.aifactory.controller.task;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import ins.aifactory.service.adhrncMoneyPymnt.AdhrncMoneyPymnt;
import ins.aifactory.service.adhrncMoneyPymnt.AdhrncMoneyPymntCriterion;
import ins.aifactory.service.adhrncMoneyPymnt.AdhrncMoneyPymntService;
import ins.aifactory.service.cmmnFile.CmmnFile;
import ins.aifactory.service.cmmnFile.CmmnFileService;
import ins.aifactory.service.lap.Lap;
import ins.aifactory.service.lap.LapCriterion;
import ins.aifactory.service.lap.LapService;
import ins.aifactory.service.lapRank.LapRankCriterion;
import ins.aifactory.service.lapRank.LapRankService;
import ins.aifactory.service.task.Task;
import ins.aifactory.service.task.TaskCriterion;
import ins.aifactory.service.task.TaskService;
import ins.core.entity.EntityPage;
import ins.core.entity.LoginInfo;
import ins.core.exception.ParameterValidException;
import ins.core.util.StringUtil;
import ins.core.web.AbstractController;

@Controller
@RequestMapping("task")
public class TaskController extends AbstractController{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);
    
    @Autowired
    protected TaskService service;
    
    @Autowired
    protected LapService lapService;
    
    @Autowired
    protected LapRankService lapRankService;
    
    @Autowired
    protected AdhrncMoneyPymntService adhrncMoneyPymntService;
    
    @Autowired
    private CmmnFileService cmmnFileService;
   
    @Resource protected Validator validator;
    
    @RequestMapping(value = "search")
    public String search(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, TaskCriterion criterion, BindingResult result, SessionStatus status, Authentication authentication) {
        LoginInfo principal = new LoginInfo();
        if(authentication != null)
            principal = (LoginInfo) authentication.getPrincipal();
        
        if (criterion == null) {
            criterion = new TaskCriterion();
        }
        
        criterion.setUserId(principal.getId());
        modelMap.addAttribute(criterion);
        
        return "tls/main/" + this.urlbase + "/search";
    }
    
    @RequestMapping(value = "searchAjax")
    public String searchAjax(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, TaskCriterion criterion, BindingResult result, SessionStatus status, Authentication authentication) {
        LoginInfo principal = new LoginInfo();
        if(authentication != null)
            principal = (LoginInfo) authentication.getPrincipal();
        
        if (criterion == null) {
            criterion = new TaskCriterion();
        }
        
        criterion.setUserId(principal.getId());
        EntityPage<Task> taskList = service.search(criterion);
        
        modelMap.addAttribute("result", taskList);
        
        return "jsonView";
    }
    
    @RequestMapping(value = "detail")
    public String detail(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Task entity, BindingResult result) {
        if (entity == null) {
            throw new RuntimeException();
        }
        
        // Task 상세 정보 조회
        Task task = service.detail(entity);
        
        // Lap 정보 조회
        LapCriterion lapCriterion = new LapCriterion();
        lapCriterion.setTaskId(task.getTaskId());
        List<Lap> lapList = lapService.list(lapCriterion);
        task.setLaps(lapList);
        
        // Rank 조회
        LapRankCriterion lapRankCriterion = new LapRankCriterion();
        for(Lap lap : lapList){
            lapRankCriterion.setLap(lap);
            lap.setLapRank(lapRankService.list(lapRankCriterion));
        }
        
        // 첨부파일 조회
        if( task.getCmmnFile() != null && !StringUtils.isBlank(task.getCmmnFile().getAtchFileId() ) ){
            CmmnFile cmmnFile = cmmnFileService.detail(task.getCmmnFile());
            task.setCmmnFile(cmmnFile);
        }
        
        // Lap Status 조회
        AdhrncMoneyPymntCriterion adhrncMoneyPymntCriterion = new AdhrncMoneyPymntCriterion();
        adhrncMoneyPymntCriterion.setTaskId(task.getTaskId());
        List<AdhrncMoneyPymnt> lapStatusList = adhrncMoneyPymntService.listStatus(adhrncMoneyPymntCriterion);
        
        modelMap.addAttribute("lapStatus", lapStatusList);
        modelMap.addAttribute(task);

        return"tls/sub/" + this.urlbase + "/detail";
    }

    @RequestMapping(value = "insert", method = RequestMethod.GET)
    public String insertForm(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        
        modelMap.addAttribute(new Task());

        return "tls/sub/" + this.urlbase + "/insert";
    }

    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public String insert(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @ModelAttribute Task entity, BindingResult result, SessionStatus status) {
        validator.validate(entity, result);
        if (result.hasErrors())
            throw new ParameterValidException(result);
        else {
            entity.setTaskDc(StringUtil.convertTuiEditorSave(entity.getTaskDc()));
            this.service.insert(entity);
            return "jsonView";
        }
    }
    
    @RequestMapping(value = "updateForm", method = RequestMethod.POST)
    public String updateForm(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Task entity, BindingResult result) {
        
        if (entity == null) {
            throw new RuntimeException();
        }
        
        // Task 상세 정보 조회
        Task task = service.detail(entity);
        
        // Lap 정보 조회
        LapCriterion lapCriterion = new LapCriterion();
        lapCriterion.setTaskId(task.getTaskId());
        List<Lap> lapList = lapService.list(lapCriterion);
        task.setLaps(lapList);
        
        // Rank 조회
        LapRankCriterion lapRankCriterion = new LapRankCriterion();
        for(Lap lap : lapList){
            lapRankCriterion.setLap(lap);
            lap.setLapRank(lapRankService.list(lapRankCriterion));
        }
        
        // 첨부파일 조회
        if( task.getCmmnFile() != null && !StringUtils.isBlank(task.getCmmnFile().getAtchFileId() ) ){
            CmmnFile cmmnFile = cmmnFileService.detail(task.getCmmnFile());
            task.setCmmnFile(cmmnFile);
        }
        
        modelMap.addAttribute(task);

        return "tls/sub/" + this.urlbase + "/update";
    }
    
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @ModelAttribute Task entity, BindingResult result, SessionStatus status) {
        validator.validate(entity, result);
        if (result.hasErrors())
            throw new ParameterValidException(result);
        else {
            entity.setTaskDc(StringUtil.convertTuiEditorSave(entity.getTaskDc()));
            this.service.update(entity);
            return "jsonView";
        }
    }
}
