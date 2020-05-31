package ins.aifactory.service.partcptAgre;

import ins.aifactory.service.task.Task;
import ins.aifactory.service.user.User;
import ins.core.entity.EntityInfo;

public class PartcptAgre extends EntityInfo{
    private Task task;
    private User user;
    private String agreYn;
    
    public Task getTask() {
        return task;
    }
    public void setTask(Task task) {
        this.task = task;
    }
    public User getUser() {
        if( user == null ) user = new User();
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getAgreYn() {
        return agreYn;
    }
    public void setAgreYn(String agreYn) {
        this.agreYn = agreYn;
    }
    
}
