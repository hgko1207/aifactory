package ins.aifactory.service.lap;

import ins.core.entity.EntityCriterion;

public class LapCriterion extends EntityCriterion {
    private String taskId;
    private Integer lapSn;
    private String actvtyYn;
    
    public String getTaskId() {
        return taskId;
    }
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public Integer getLapSn() {
        return lapSn;
    }
    public void setLapSn(Integer lapSn) {
        this.lapSn = lapSn;
    }
    public String getActvtyYn() {
        return actvtyYn;
    }
    public void setActvtyYn(String actvtyYn) {
        this.actvtyYn = actvtyYn;
    }
}
