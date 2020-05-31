package ins.aifactory.service.userRole;

import ins.aifactory.service.role.Role;
import ins.aifactory.service.user.User;
import ins.core.entity.EntityInfo;

public class UserRole extends EntityInfo {

	private User user;
	private Role role;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
