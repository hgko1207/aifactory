package ins.aifactory.service.role;

import ins.core.entity.EntityInfo;

public class Role extends EntityInfo {

	public static final String ROLE_ADMIN = "ADMN";
	public static final String ROLE_BIZ = "BIZC";
	public static final String ROLE_USER = "USER";

	private String roleCode;
	private String roleNm;

	public Role() {
	}

	public Role(String roleCode) {
		this.setRoleCode(roleCode);
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleNm() {
		return roleNm;
	}

	public void setRoleNm(String roleNm) {
		this.roleNm = roleNm;
	}

}
