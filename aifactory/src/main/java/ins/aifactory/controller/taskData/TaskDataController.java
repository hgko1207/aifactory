package ins.aifactory.controller.taskData;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ins.aifactory.service.task.Task;
import ins.aifactory.service.task.TaskService;
import ins.aifactory.service.taskData.TaskData;
import ins.aifactory.service.taskData.TaskDataService;
import ins.core.util.StringUtil;
import ins.core.web.AbstractController;

@Controller
@RequestMapping("taskData")
public class TaskDataController extends AbstractController{
    
    @Autowired
    private TaskDataService service;
    
    @Autowired
    private TaskService taskService;
    
    @Resource protected Validator validator;
    
    @RequestMapping(value = "detail")
    public String detail(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, TaskData entity, BindingResult result) {
        if (entity == null) {
            throw new RuntimeException();
        }
        
        TaskData taskData = service.detail(entity);
        
        if( taskData == null ){
            taskData = new TaskData();
        }
        Task task = taskService.detail(entity.getTask());
        taskData.setTask(task);
        
        modelMap.addAttribute(taskData.getTask());
        modelMap.addAttribute(taskData);

        return "tls/sub/" + this.urlbase + "/detail";
    }
    
    @RequestMapping(value = "detailJsonView")
    public String detailJsonView(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, TaskData entity, BindingResult result) {
        
        TaskData taskData = service.detail(entity);
        
        if( taskData == null ){
            taskData = new TaskData();
            taskData.setTask(entity.getTask());
        }
        
        modelMap.addAttribute(taskData);

        return "jsonView";
    }
    
    @RequestMapping(value = "updateDc", method = RequestMethod.POST)
    public String updateDc(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, TaskData entity, BindingResult result) {
        TaskData detail = this.service.detail(entity);
        if(detail == null){
            detail=entity;
        }
        
        detail.setDataSetDc(StringUtil.convertTuiEditorSave(entity.getDataSetDc()));
        detail.setFileDc(StringUtil.convertTuiEditorSave(entity.getFileDc()));
        this.service.insertOrUpdate(detail);
        return "jsonView";
    }
    
    @RequestMapping(value = "updateFileInfo", method = RequestMethod.POST)
    public String updateFileInfo(HttpServletRequest request, HttpServletResponse response, TaskData entity, BindingResult result) {
        TaskData detailEntity = this.service.detail(entity);
        // Task Data 처음 등록시
        if(detailEntity == null){
            this.service.insert(entity);
        }else{
            String atchFileId = detailEntity.getCmmnFile().getAtchFileId();
            detailEntity.setCmmnFile(entity.getCmmnFile());
            detailEntity.getCmmnFile().setAtchFileId(atchFileId);
            this.service.update(detailEntity);
        }
        
        return "jsonView";
    }
    
}
