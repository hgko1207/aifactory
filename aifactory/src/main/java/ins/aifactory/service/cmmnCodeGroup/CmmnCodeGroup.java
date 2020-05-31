package ins.aifactory.service.cmmnCodeGroup;

import org.apache.commons.lang3.StringUtils;

import ins.core.entity.EntityInfo;

public class CmmnCodeGroup  extends EntityInfo {
    
    private String groupCode;
    private String groupNm;
    private Integer sortOrdr;
    private String deleteYn;
    private String groupCodeCn;
    
    public CmmnCodeGroup(){
        
    }
    
    public CmmnCodeGroup(String groupCode){
        this.groupCode = groupCode;
    }
    
    public String getGroupCode() {
        return groupCode;
    }
    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }
    public String getGroupNm() {
        return groupNm;
    }
    public void setGroupNm(String groupNm) {
        this.groupNm = groupNm;
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
    public String getGroupCodeCn() {
        return groupCodeCn;
    }
    public void setGroupCodeCn(String groupCodeCn) {
        this.groupCodeCn = groupCodeCn;
    }
}
