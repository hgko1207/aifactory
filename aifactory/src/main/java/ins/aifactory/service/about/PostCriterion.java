package ins.aifactory.service.about;

import ins.core.entity.EntityCriterion;

public class PostCriterion extends EntityCriterion {
    private String mode;
    private String userId;
    private String keyword;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    
}
