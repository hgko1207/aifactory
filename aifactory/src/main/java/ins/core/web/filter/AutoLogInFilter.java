/* Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ins.core.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;

import ins.core.aspect.ApiConstants;
import ins.core.entity.EntityError;
import ins.core.entity.LoginInfo;


public class AutoLogInFilter extends GenericFilterBean {

    @Value("#{globalProp['Auto.Login.Enable']}")
    private boolean autoLoginEnable = true;
    
    @Value("#{globalProp['Auto.Login.UserId']}")
    private String autoLoginUserId;
    
    @Value("#{globalProp['Auto.Login.UserPwd']}")
    private String autoLoginUserPwd;
    
    private AuthenticationManager authenticationManager;
    private AuthenticationDetailsSource<HttpServletRequest,?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
    private SessionAuthenticationStrategy sessionStrategy = new NullAuthenticatedSessionStrategy();
    private String profile;
    
    /**
     * 로그인 여부 체크하여 로그인 안되어 있으면 주어진 계정으로 자동 로그인
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        // 운영환경이 아닐경우 
		if (!profile.equalsIgnoreCase("prod")) {
			boolean isAuth = false;
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			if (auth != null) {
				Object principal = auth.getPrincipal();

				if (principal != null && principal instanceof LoginInfo) {
					isAuth = true;
				}
			}

			if (!isAuth) {
                // API Call 인 경우 JSON 포멧으로 오류  Return
                String apiPath = ((HttpServletRequest)request).getContextPath() + "/api/";
                String requestURI = ((HttpServletRequest)request).getRequestURI();
                
				if (requestURI.indexOf(apiPath) == 0) {
					EntityError errorResponse = new EntityError();
					errorResponse.setCode(ApiConstants.CODE_ERR_NOT_LOGIN);
					errorResponse.setMessage("세션이 종료되었습니다.\n다시 로그인하여 주십시오.");

					byte[] responseToSend = new ObjectMapper().writeValueAsBytes(errorResponse);
					((HttpServletResponse) response).setHeader("Content-Type", "application/json");
					((HttpServletResponse) response).setStatus(401);
					response.getOutputStream().write(responseToSend);

					return;
				}
                
                // 자동 로그인 활성화시 
				if (autoLoginEnable) {
					Authentication authResult = null;
					try {
						authResult = attemptAuthentication((HttpServletRequest) request,
								(HttpServletResponse) response);
						if (authResult == null) {
							// return immediately as subclass has indicated that
							// it hasn't completed authentication
							return;
						}
						sessionStrategy.onAuthentication(authResult, (HttpServletRequest) request,
								(HttpServletResponse) response);
					} catch (InternalAuthenticationServiceException failed) {
						return;
					} catch (AuthenticationException failed) {
						return;
					}

					successfulAuthentication((HttpServletRequest) request, (HttpServletResponse) response, authResult);
				}
			}
        }
        
        chain.doFilter(request, response);
        
    }
    
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(autoLoginUserId, autoLoginUserPwd);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }
    
    /**
     * Provided so that subclasses may configure what is put into the authentication request's details
     * property.
     *
     * @param request that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details set
     */
    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
    
    /**
     * The session handling strategy which will be invoked immediately after an authentication request is
     * successfully processed by the <tt>AuthenticationManager</tt>. Used, for example, to handle changing of the
     * session identifier to prevent session fixation attacks.
     *
     * @param sessionStrategy the implementation to use. If not set a null implementation is
     * used.
     */
    public void setSessionAuthenticationStrategy(SessionAuthenticationStrategy sessionStrategy) {
        this.sessionStrategy = sessionStrategy;
    }
    
    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			Authentication authResult) throws IOException, ServletException {

		if (logger.isDebugEnabled()) {
			logger.debug("Authentication success. Updating SecurityContextHolder to contain: " + authResult);
		}

		SecurityContextHolder.getContext().setAuthentication(authResult);
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}
    
}
