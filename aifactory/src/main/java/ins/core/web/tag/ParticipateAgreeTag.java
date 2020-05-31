package ins.core.web.tag;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ins.aifactory.service.partcptAgre.PartcptAgre;
import ins.aifactory.service.partcptAgre.PartcptAgreService;
import ins.aifactory.service.task.Task;
import ins.aifactory.service.user.User;
import ins.core.entity.LoginInfo;

public class ParticipateAgreeTag extends SimpleTagSupport {

    private String taskId;
    
    protected boolean isAgree(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		boolean isAgree = false;
		if (auth != null) {
            Object principal = auth.getPrincipal();
            if( principal != null && principal instanceof LoginInfo ){
                PageContext pageContext = (PageContext) getJspContext();
                ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
                
                // 태스크 참가여부 조회
                PartcptAgre partcptAgre = new PartcptAgre();
                partcptAgre.setTask(new Task(taskId));
                partcptAgre.setUser(new User(((LoginInfo)principal).getId()));
                PartcptAgreService partcptAgreService = ctx.getBean(PartcptAgreService.class);
                partcptAgre = partcptAgreService.detail(partcptAgre);
                
                if(partcptAgre != null){
                    isAgree = true;
                }
            }
        }
        
        return isAgree;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

}
