package ins.aifactory.service.userRole;

import org.springframework.stereotype.Service;

import ins.core.service.InsBaseServiceImpl;

/**
 * 사용자 역할 관리 서비스 구현  클래스
 * @author 전인택
 * @since 2019.10.02
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2019.10.02  전인택          최초 생성
 *
 * </pre>
 */
@Service("UserRoleService")
public class UserRoleServiceImpl extends InsBaseServiceImpl<UserRole, UserRoleCriterion> implements UserRoleService {

	public UserRoleServiceImpl() {
		super(UserRole.class);
	}

}
