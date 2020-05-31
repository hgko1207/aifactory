package ins.aifactory.service.cmmnFileDetail;

import ins.core.service.InsBaseService;

public interface CmmnFileDetailService extends InsBaseService<CmmnFileDetail, CmmnFileDetailCriterion> {
    
    public int deleteByAtchFileId(String atchFileId);
    
}
