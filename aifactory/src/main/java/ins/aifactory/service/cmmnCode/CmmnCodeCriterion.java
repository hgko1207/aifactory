package ins.aifactory.service.cmmnCode;

import ins.aifactory.service.cmmnCodeGroup.CmmnCodeGroup;
import ins.core.entity.EntityCriterion;

public class CmmnCodeCriterion extends EntityCriterion {
    
    private CmmnCodeGroup cmmnCodeGroup;
    private String codeNmLike;
    private String deleteYn;
    
    public CmmnCodeGroup getCmmnCodeGroup() {
        return cmmnCodeGroup;
    }
    public void setCmmnCodeGroup(CmmnCodeGroup cmmnCodeGroup) {
        this.cmmnCodeGroup = cmmnCodeGroup;
    }
    public String getCodeNmLike() {
        return codeNmLike;
    }
    public void setCodeNmLike(String codeNmLike) {
        this.codeNmLike = codeNmLike;
    }
    public String getDeleteYn() {
        return deleteYn;
    }
    public void setDeleteYn(String deleteYn) {
        this.deleteYn = deleteYn;
    }

}
