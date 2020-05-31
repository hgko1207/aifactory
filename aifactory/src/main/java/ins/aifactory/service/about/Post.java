package ins.aifactory.service.about;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import ins.aifactory.service.cmmnFile.CmmnFile;
import ins.core.entity.EntityInfo;

public class Post extends EntityInfo {

	private String postCt;// post category ex) news,member
	private String postId;
	private String postNm;
	private String postDc;
	private CmmnFile cmmnFile;

	private boolean fileChange;
	private Map<String, Object> map;

	public Post() {

	}

	public String getPostCt() {
		return postCt;
	}

	public void setPostCt(String postCt) {
		this.postCt = postCt;
	}

	public Post(String postId) {
		this.postId = postId;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getPostNm() {
		return postNm;
	}

	public void setPostNm(String postNm) {
		this.postNm = postNm;
	}

	public String getPostDc() {
		if (StringUtils.isEmpty(postDc) == false)
			return postDc.replaceAll("(\r\n|\r|\n|\n\r)", "");

		return "";

	}

	public void setPostDc(String postDc) {
		this.postDc = postDc;
	}

	public CmmnFile getCmmnFile() {
		return cmmnFile;
	}

	public void setCmmnFile(CmmnFile cmmnFile) {
		this.cmmnFile = cmmnFile;
	}

	public boolean isFileChange() {
		return fileChange;
	}

	public void setFileChange(boolean fileChange) {
		this.fileChange = fileChange;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

}
