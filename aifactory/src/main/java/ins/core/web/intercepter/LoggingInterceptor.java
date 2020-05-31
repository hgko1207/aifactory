package ins.core.web.intercepter;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoggingInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        Map<String, String[]> paramMap = request.getParameterMap();
        String param = getParammetersString(paramMap); // 파라메타 조합

        StringBuffer url = request.getRequestURL(); // URL 주소 가져오기
        url.append(param);
        LOGGER.info(url.toString() + " [METHOD=" + request.getMethod() + "]");

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    /**
     * <PRE>
     * Comment : 파라미터 KEY, VALUE를 정렬한다.
     * </PRE>
     * 
     * @return String
     * @param map
     * @return
     */
    public static String getParammetersString(Map<String, String[]> map) {
        SortedMap<String, String[]> smap = Collections.synchronizedSortedMap(new TreeMap<String, String[]>(map));

        Iterator<String> it = smap.keySet().iterator();
        String key = null;
        String[] value = null;
        StringBuffer param = new StringBuffer();
        int count = 0;
        while (it.hasNext()) {
            key = (String) it.next();
            value = (String[]) smap.get(key);
            for (int i = 0; i < value.length; i++) {
                if (count > 0)
                    param.append("&");
                else
                    param.append("?");

                // if("id".equals(key) || "pwd".equals(key) ||
                // "acctPassword2".equals(key) || "nAcctPassword".equals(key)
                // || "loginId".equals(key) || "loginPw".equals(key)
                // || "projectUserRel.user.acctPassword".equals(key)
                // )
                // param.append(key+"=");
                // else
                param.append(key + "=" + value[i]);
                count++;
            }
        }
        return param.toString();
    }
}
