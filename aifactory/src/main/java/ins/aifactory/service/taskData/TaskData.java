package ins.aifactory.service.taskData;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ins.aifactory.service.cmmnFile.CmmnFile;
import ins.aifactory.service.task.Task;
import ins.core.entity.EntityInfo;

public class TaskData extends EntityInfo{
    @JsonIgnore
    private Task task;
    private String dataSetDc;
    private String fileDc;
    private CmmnFile cmmnFile;
    
    public Task getTask() {
        return task;
    }
    public void setTask(Task task) {
        this.task = task;
    }
    public String getDataSetDc() {
        return dataSetDc;
    }
    public void setDataSetDc(String dataSetDc) {
        this.dataSetDc = dataSetDc;
    }
    public String getFileDc() {
        return fileDc;
    }
    public void setFileDc(String fileDc) {
        this.fileDc = fileDc;
    }
    public CmmnFile getCmmnFile() {
        return cmmnFile;
    }
    public void setCmmnFile(CmmnFile cmmnFile) {
        this.cmmnFile = cmmnFile;
    }
}
