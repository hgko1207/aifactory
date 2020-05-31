package ins.aifactory.service.task;

import ins.core.entity.EntityCriterion;

public class TaskCriterion extends EntityCriterion {
    private String mode;
    private String userId;
    private String othbcYn;
    private String keyword;

    public String getMode() {
        return mode;
    }
    public void setMode(String mode) {
        this.mode = mode;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getOthbcYn() {
        return othbcYn;
    }
    public void setOthbcYn(String othbcYn) {
        this.othbcYn = othbcYn;
    }
    public String getKeyword() {
        return keyword;
    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
