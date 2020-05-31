package ins.aifactory.service.lapAdhrnc;

import ins.core.entity.EntityCriterion;

public class LapAdhrncCriterion extends EntityCriterion {

	private String userId;
	private String taskId;
	private String resultSbmisnMthdCode;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getResultSbmisnMthdCode() {
		return resultSbmisnMthdCode;
	}

	public void setResultSbmisnMthdCode(String resultSbmisnMthdCode) {
		this.resultSbmisnMthdCode = resultSbmisnMthdCode;
	}
}
