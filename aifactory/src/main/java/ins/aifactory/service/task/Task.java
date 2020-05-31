package ins.aifactory.service.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import ins.aifactory.service.cmmnFile.CmmnFile;
import ins.aifactory.service.lap.Lap;
import ins.core.entity.EntityInfo;

public class Task extends EntityInfo{
    
    private String taskId;
    private String taskNm;
    private String taskSumry;
    private String taskDc;
    private CmmnFile cmmnFile;
    private Date beginDttm;
    private Date endDttm;
    private String othbcYn;
    private String grdngFileCours;
    private List<Lap> laps;
    
    private boolean fileChange;
    private Map<String, Object> map;
    
    public Task(){
        
    }
    
    public Task(String taskId){
        this.taskId = taskId;
    }
    
    public String getTaskId() {
        return taskId;
    }
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public String getTaskNm() {
        return taskNm;
    }
    public void setTaskNm(String taskNm) {
        this.taskNm = taskNm;
    }
    public String getTaskSumry() {
        return taskSumry;
    }
    public void setTaskSumry(String taskSumry) {
        this.taskSumry = taskSumry;
    }
    public String getTaskDc() {
        if( StringUtils.isEmpty(taskDc) == false )
            return taskDc.replaceAll("(\r\n|\r|\n|\n\r)", "");
        
        return "";
        
    }
    public void setTaskDc(String taskDc) {
        this.taskDc = taskDc;
    }
    public CmmnFile getCmmnFile() {
        return cmmnFile;
    }
    public void setCmmnFile(CmmnFile cmmnFile) {
        this.cmmnFile = cmmnFile;
    }
    public Date getBeginDttm() {
        return beginDttm;
    }
    public String getBeginDttmStr() {
        String beginDate = "";
        if( beginDttm != null ){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            beginDate = sdf.format(beginDttm);
        }
        return beginDate;
    }
    public void setBeginDttm(Date beginDttm) {
        this.beginDttm = beginDttm;
    }
    public Date getEndDttm() {
        return endDttm;
    }
    public String getEndDttmStr() {
        String endDate = "";
        if( endDttm != null ){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            endDate = sdf.format(endDttm);
        }
        return endDate;
    }
    public void setEndDttm(Date endDttm) {
        this.endDttm = endDttm;
    }
    
    public String getOthbcYn() {
        return othbcYn;
    }
    public void setOthbcYn(String othbcYn) {
        this.othbcYn = othbcYn;
    }
    public String getGrdngFileCours() {
        return grdngFileCours;
    }
    public void setGrdngFileCours(String grdngFileCours) {
        this.grdngFileCours = grdngFileCours;
    }

    public List<Lap> getLaps() {
        return laps;
    }
    public void setLaps(List<Lap> laps) {
        this.laps = laps;
    }
    public boolean isFileChange() {
        return fileChange;
    }
    public void setFileChange(boolean fileChange) {
        this.fileChange = fileChange;
    }
    public Map<String, Object> getMap() {
        return map;
    }
    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
    
}
