package ins.aifactory.service.cmmnFileDetail;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ins.aifactory.service.cmmnFile.CmmnFile;
import ins.core.entity.EntityInfo;

public class CmmnFileDetail extends EntityInfo {
	
	@JsonIgnore
	private CmmnFile cmmnFile;
	private String key;
	private Integer fileSn;
	private String fileStreCours;
	private String streFileNm;
	private String orignlFileNm;
	private String fileExtsn;
	private String fileDc;
	private long fileSize;
	private String deleteYn;

	public CmmnFile getCmmnFile() {
		return cmmnFile;
	}

	public void setCmmnFile(CmmnFile cmmnFile) {
		this.cmmnFile = cmmnFile;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getFileSn() {
		return fileSn;
	}

	public void setFileSn(Integer fileSn) {
		this.fileSn = fileSn;
	}

	public String getFileStreCours() {
		return fileStreCours;
	}

	public void setFileStreCours(String fileStreCours) {
		this.fileStreCours = fileStreCours;
	}

	public String getStreFileNm() {
		return streFileNm;
	}

	public void setStreFileNm(String streFileNm) {
		this.streFileNm = streFileNm;
	}

	public String getOrignlFileNm() {
		return orignlFileNm;
	}

	public void setOrignlFileNm(String orignlFileNm) {
		this.orignlFileNm = orignlFileNm;
	}

	public String getFileExtsn() {
		return fileExtsn;
	}

	public void setFileExtsn(String fileExtsn) {
		this.fileExtsn = fileExtsn;
	}

	public String getFileDc() {
		return fileDc;
	}

	public void setFileDc(String fileDc) {
		this.fileDc = fileDc;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getDeleteYn() {
		return deleteYn;
	}

	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
	}
}