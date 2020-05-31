package ins.aifactory.service.qna;

import ins.core.entity.EntityCriterion;

public class QnaCriterion extends EntityCriterion {
    private String taskId;

    public String getTaskId() {
        return taskId;
    }
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    
}