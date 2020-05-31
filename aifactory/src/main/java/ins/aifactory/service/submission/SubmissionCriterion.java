package ins.aifactory.service.submission;

import ins.core.entity.EntityCriterion;

public class SubmissionCriterion extends EntityCriterion {

	private String taskId;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
}
