package ins.aifactory.service.cmmnCode;

import org.apache.commons.lang3.StringUtils;

import ins.aifactory.service.cmmnCodeGroup.CmmnCodeGroup;
import ins.core.entity.EntityInfo;

public class CmmnCode extends EntityInfo {
    
    private CmmnCodeGroup cmmnCodeGroup;
    private String cmmnCode;
    private String codeNm;
    private Integer sortOrdr;
    private String deleteYn;
    private String codeDc;
    
    public CmmnCode(){
    }
    
    public CmmnCode(String groupCode, String code){
        this.cmmnCodeGroup = new CmmnCodeGroup(groupCode);
        this.cmmnCode = code;
    }
    
    public CmmnCodeGroup getCmmnCodeGroup() {
        return cmmnCodeGroup;
    }
    public void setCmmnCodeGroup(CmmnCodeGroup cmmnCodeGroup) {
        this.cmmnCodeGroup = cmmnCodeGroup;
    }
    public String getCmmnCode() {
        return cmmnCode;
    }
    public void setCmmnCode(String cmmnCode) {
        this.cmmnCode = cmmnCode;
    }
    public String getCodeNm() {
        return codeNm;
    }
    public void setCodeNm(String codeNm) {
        this.codeNm = codeNm;
    }
    public Integer getSortOrdr() {
        return sortOrdr;
    }
    public void setSortOrdr(Integer sortOrdr) {
        this.sortOrdr = sortOrdr;
    }
    public String getDeleteYn() {
        if( StringUtils.isBlank(this.deleteYn) )
            this.deleteYn = "N";
        return deleteYn;
    }
    public void setDeleteYn(String deleteYn) {
        this.deleteYn = deleteYn;
    }
    public String getCodeDc() {
        return codeDc;
    }
    public void setCodeDc(String codeDc) {
        this.codeDc = codeDc;
    }
}
