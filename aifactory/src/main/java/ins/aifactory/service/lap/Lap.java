package ins.aifactory.service.lap;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ins.aifactory.service.cmmnCode.CmmnCode;
import ins.aifactory.service.lapRank.LapRank;
import ins.aifactory.service.task.Task;
import ins.core.entity.EntityInfo;

public class Lap  extends EntityInfo{
    @JsonIgnore
    private Task task;
    private Integer lapSn;
    private CmmnCode lapEndCndCode;
    private Integer chkPnttmCycle;
    private Float goalScre;
    private String actvtyYn;
    private String achivYn;
    private Date beginDttm;
    private Date endDttm;
    
    private List<LapRank> lapRank;
    private Integer totalPoint;
    
    public Lap(){
        
    }
    
    public Lap(Task task){
        this.task = task;
    }
    
    public Task getTask() {
        return task;
    }
    public void setTask(Task task) {
        this.task = task;
    }
    public Integer getLapSn() {
        return lapSn;
    }
    public void setLapSn(Integer lapSn) {
        this.lapSn = lapSn;
    }
    public CmmnCode getLapEndCndCode() {
        return lapEndCndCode;
    }
    public void setLapEndCndCode(CmmnCode lapEndCndCode) {
        this.lapEndCndCode = lapEndCndCode;
    }
    public Integer getChkPnttmCycle() {
        return chkPnttmCycle;
    }
    public void setChkPnttmCycle(Integer chkPnttmCycle) {
        this.chkPnttmCycle = chkPnttmCycle;
    }
    public Float getGoalScre() {
        if(goalScre == null) goalScre = 0f;
        return goalScre;
    }
    public void setGoalScre(Float goalScre) {
        this.goalScre = goalScre;
    }
    public String getActvtyYn() {
        return actvtyYn;
    }
    public void setActvtyYn(String actvtyYn) {
        this.actvtyYn = actvtyYn;
    }
    public String getAchivYn() {
        return achivYn;
    }
    public void setAchivYn(String achivYn) {
        this.achivYn = achivYn;
    }
    public Date getBeginDttm() {
        return beginDttm;
    }
    public void setBeginDttm(Date beginDttm) {
        this.beginDttm = beginDttm;
    }
    public Date getEndDttm() {
        if( endDttm != null ){
            Calendar cal = Calendar.getInstance();
            cal.setTime(endDttm);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 999);
            
            endDttm = cal.getTime();
        }
        return endDttm;
    }
    public void setEndDttm(Date endDttm) {
        this.endDttm = endDttm;
    }
    public List<LapRank> getLapRank() {
        return lapRank;
    }
    public void setLapRank(List<LapRank> lapRank) {
        this.lapRank = lapRank;
    }
    public Integer getTotalPoint() {
        this.totalPoint = 0;
        if(lapRank != null){
            for(LapRank lapRank : this.lapRank)
                totalPoint += lapRank.getMoney();
        }
        
        return totalPoint;
    }
}
