package ins.aifactory.service.teamConstnt;

import ins.aifactory.service.team.Team;
import ins.aifactory.service.user.User;
import ins.core.entity.EntityInfo;

public class TeamConstnt extends EntityInfo {
	
	private Team team;
	private User user;
	private String timhderYn;

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

	public String getTimhderYn() {
		return timhderYn;
	}

	public void setTimhderYn(String timhderYn) {
		this.timhderYn = timhderYn;
	}
}
