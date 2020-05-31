package ins.aifactory.service.leaderboard;

import ins.core.entity.EntityCriterion;

public class LeaderboardCriterion extends EntityCriterion {
	
	private String taskId;
	private boolean publc = true;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public boolean isPublc() {
		return publc;
	}

	public void setPublc(boolean publc) {
		this.publc = publc;
	}

}
