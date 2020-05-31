package ins.aifactory.service.lapAdhrnc;

import ins.aifactory.service.cmmnCode.CmmnCode;
import ins.aifactory.service.cmmnFile.CmmnFile;
import ins.aifactory.service.lap.Lap;
import ins.aifactory.service.team.Team;
import ins.aifactory.service.user.User;
import ins.core.entity.EntityInfo;

public class LapAdhrnc extends EntityInfo {

	private Integer adhrncSn;
	private Lap lap;
	private CmmnCode adhrncSeCode;
	private Team team;
	private User user;
	private String modelNm;
	private Float scre;
	private Integer rank;
	private CmmnCode resultSbmisnMthdCode;
	private CmmnFile cmmnFile;

	public Lap getLap() {
		return lap;
	}

	public void setLap(Lap lap) {
		this.lap = lap;
	}

	public Integer getAdhrncSn() {
		return adhrncSn;
	}

	public void setAdhrncSn(Integer adhrncSn) {
		this.adhrncSn = adhrncSn;
	}

	public CmmnCode getAdhrncSeCode() {
		return adhrncSeCode;
	}

	public void setAdhrncSeCode(CmmnCode adhrncSeCode) {
		this.adhrncSeCode = adhrncSeCode;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getModelNm() {
		return modelNm;
	}

	public void setModelNm(String modelNm) {
		this.modelNm = modelNm;
	}

	public Float getScre() {
		return scre;
	}

	public void setScre(Float scre) {
		this.scre = scre;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public CmmnCode getResultSbmisnMthdCode() {
		return resultSbmisnMthdCode;
	}

	public void setResultSbmisnMthdCode(CmmnCode resultSbmisnMthdCode) {
		this.resultSbmisnMthdCode = resultSbmisnMthdCode;
	}

	public CmmnFile getCmmnFile() {
		return cmmnFile;
	}

	public void setCmmnFile(CmmnFile cmmnFile) {
		this.cmmnFile = cmmnFile;
	}

}
