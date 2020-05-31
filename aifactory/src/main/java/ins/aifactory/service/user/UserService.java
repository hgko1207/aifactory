package ins.aifactory.service.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ins.core.service.InsBaseService;

public interface UserService extends InsBaseService<User, UserCriterion> {

    boolean chkDuplicateEmail(User entity);

    void login(HttpServletRequest request, HttpServletResponse response, String userEmail, String userPwd);

    public String getPwd(User user);
    
}
