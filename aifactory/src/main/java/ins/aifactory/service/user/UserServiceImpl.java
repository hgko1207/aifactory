package ins.aifactory.service.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import ins.aifactory.service.role.Role;
import ins.aifactory.service.userRole.UserRole;
import ins.aifactory.service.userRole.UserRoleService;
import ins.core.entity.LoginInfo;
import ins.core.service.InsBaseServiceImpl;

/**
 * 사용자 서비스 구현  클래스
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
@Service("UserService")
public class UserServiceImpl extends InsBaseServiceImpl<User, UserCriterion> implements UserService{
    
    @Resource(name = "userIdGnrService")
    private EgovIdGnrService idgenService;
    
    @Resource
    private BCryptPasswordEncoder bcryptPasswordEncoder;
    
    @Resource
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserRoleService userRoleService;
    
    public UserServiceImpl() {
        super(User.class);
    }
    
    @Override
    public void insert(User entity) {
        try {
            // 비밀번호 암호화
            String passwd = entity.getUserPwd();
            if(StringUtils.isNoneBlank(passwd)) {
                String encStr = bcryptPasswordEncoder.encode(passwd);
                entity.setUserPwd(encStr);
            }
            
            // 사용자 ID 채번
            entity.setUserId(idgenService.getNextStringId());
            
            dao.insert(domainClass.getName()+".insert", entity);
            
            // ROLE 등록
            try{
                UserRole userRole = new UserRole();
                userRole.setUser(entity);
                userRole.setRole(new Role(Role.ROLE_USER));
                userRoleService.insert(userRole);
            }catch(Exception e){
                e.printStackTrace();
            }
        } catch (FdlException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    
    @Override
    public void update(User entity) {
        // 비밀번호 암호화
        String passwd = entity.getUserPwd();
        if(StringUtils.isNoneBlank(passwd)) {
            String encStr = bcryptPasswordEncoder.encode(passwd);
            entity.setUserPwd(encStr);
        }
        
        dao.update(domainClass.getName()+".update", entity);
    }

    @Override
    public boolean chkDuplicateEmail(User entity) {
        Integer cnt = dao.selectOne(domainClass.getName()+".chkDuplicateEmail", entity);
        return ( cnt > 0 ) ? false : true;
    }
    
    @Override
    public void login(HttpServletRequest request, HttpServletResponse response, String userEmail, String userPwd){
        // 로그인 처리
        boolean isAuth = false;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if( auth != null){
            Object principal = auth.getPrincipal();
            
            if( principal != null && principal instanceof LoginInfo ){
                isAuth = true;
            }
        }
        
        if(!isAuth){
            Authentication authResult = null;
            try {
                AuthenticationDetailsSource<HttpServletRequest,?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
                SessionAuthenticationStrategy sessionStrategy = new NullAuthenticatedSessionStrategy();
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userEmail,userPwd);
                authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
                authResult = authenticationManager.authenticate(authRequest);
                
                if (authResult == null) {
                    // return immediately as subclass has indicated that it hasn't completed authentication
                    throw new RuntimeException();
                }
                sessionStrategy.onAuthentication(authResult, (HttpServletRequest)request, (HttpServletResponse)response);
            }catch(InternalAuthenticationServiceException failed) {
                throw new InternalAuthenticationServiceException(failed.getMessage(), failed);
            }catch (AuthenticationException failed) {
                throw new AuthenticationException(failed.getMessage(), failed) {
                    private static final long serialVersionUID = 1L;};
            }catch(Exception e){
                throw new RuntimeException(e.getMessage(), e);
            }
            
            SecurityContextHolder.getContext().setAuthentication(authResult);
        }
    }

    @Override
    public String getPwd(User entity) {
        String pwd = dao.selectOne(domainClass.getName()+".getPwd", entity);
        return pwd;
    }

}
