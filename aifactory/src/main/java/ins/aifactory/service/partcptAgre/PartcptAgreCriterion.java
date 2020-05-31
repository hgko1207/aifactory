package ins.aifactory.service.partcptAgre;

import ins.core.entity.EntityCriterion;

public class PartcptAgreCriterion extends EntityCriterion {
	
	private String taskId;
	private String userId;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
