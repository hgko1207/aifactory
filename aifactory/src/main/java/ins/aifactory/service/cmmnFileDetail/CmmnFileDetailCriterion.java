package ins.aifactory.service.cmmnFileDetail;

import ins.core.entity.EntityCriterion;

public class CmmnFileDetailCriterion extends EntityCriterion {

	private String atchFileId;

	private Integer fileSn;

	public String getAtchFileId() {
		return atchFileId;
	}

	public void setAtchFileId(String atchFileId) {
		this.atchFileId = atchFileId;
	}

	public Integer getFileSn() {
		return fileSn;
	}

	public void setFileSn(Integer fileSn) {
		this.fileSn = fileSn;
	}

}
