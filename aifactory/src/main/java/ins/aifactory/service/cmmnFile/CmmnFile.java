package ins.aifactory.service.cmmnFile;

import java.util.ArrayList;
import java.util.List;

import ins.aifactory.service.cmmnFileDetail.CmmnFileDetail;
import ins.core.entity.EntityInfo;

public class CmmnFile extends EntityInfo {

	private String atchFileId;
	private String deleteYn;
	private List<CmmnFileDetail> files;

	public String getAtchFileId() {
		return atchFileId;
	}

	public void setAtchFileId(String atchFileId) {
		this.atchFileId = atchFileId;
	}

	public String getDeleteYn() {
		return deleteYn;
	}

	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
	}

	public List<CmmnFileDetail> getFiles() {
		if (this.files == null)
			this.files = new ArrayList<CmmnFileDetail>();
		return files;
	}

	public void setFiles(List<CmmnFileDetail> files) {
		this.files = files;
	}

}
