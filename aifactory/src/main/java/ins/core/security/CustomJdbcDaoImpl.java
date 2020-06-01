package ins.core.security;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import ins.core.entity.LoginInfo;

public class CustomJdbcDaoImpl extends JdbcDaoImpl {
    
    protected Log logger = LogFactory.getLog(getClass());

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetails> users = loadUsersByUsername(username);
		if (users.size() == 0) {
            logger.debug("Query returned no results for user '" + username + "'");
            UsernameNotFoundException ue = new UsernameNotFoundException(
                    messages.getMessage("JdbcDaoImpl.notFound", new Object[]{username}, "Username {0} not found"));
            throw ue;
        }
        
        LoginInfo user = (LoginInfo) users.get(0);    // contains no GrantedAuthority[]
        Set<GrantedAuthority> dbAuthSet = new HashSet<GrantedAuthority>();
		if (getEnableAuthorities()) {
			dbAuthSet.addAll(loadUserAuthorities(user.getUsername()));
		}
        
        List<GrantedAuthority> dbAuths = new ArrayList<GrantedAuthority>(dbAuthSet);
        user.setAuthorities(dbAuths);
        
		if (dbAuths.size() == 0) {
            logger.debug("Uesr '" + username + "' has no authorities and will be treated as 'not found'");
            UsernameNotFoundException ue = new UsernameNotFoundException(
                    messages.getMessage("JdbcDaoImpl.notAuthority", new Object[]{username}, "User {0} has no GrantedAuthority"));
            throw ue;
        }
        
        return user;
    }
    
    @Override
    protected List<UserDetails> loadUsersByUsername(String username) {
		return getJdbcTemplate().query(getUsersByUsernameQuery(), new String[] { username },
				new RowMapper<UserDetails>() {
					public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
						String username = rs.getString(1);
						String password = rs.getString(2);
						String name = rs.getString(3);
						String email = rs.getString(4);
						return new LoginInfo(username, password, name, email, AuthorityUtils.NO_AUTHORITIES);
					}
				});
    }
    
    @Override
	protected List<GrantedAuthority> loadUserAuthorities(String username) {
		return getJdbcTemplate().query(getAuthoritiesByUsernameQuery(), new String[] { username },
				new RowMapper<GrantedAuthority>() {
					public GrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
						String roleName = getRolePrefix() + rs.getString(1);
						return new SimpleGrantedAuthority(roleName);
					}
				});
	}

    @Override
    protected List<GrantedAuthority> loadGroupAuthorities(String username) {
        return super.loadGroupAuthorities(username);
    }
}
