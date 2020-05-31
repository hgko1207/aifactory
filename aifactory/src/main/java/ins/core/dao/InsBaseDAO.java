package ins.core.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import ins.core.entity.LoginInfo;
import ins.core.util.ClassUtil;

/**
 * 공통 데이터 접근  클래스
 * @author 전인택
 * @since 2017.10.13
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2017.10.13  전인택          최초 생성
 *
 * </pre>
 */

@Repository
public class InsBaseDAO extends EgovAbstractMapper{
    
    @Autowired(required=false)
    private HttpServletRequest request;
    
    /**
     * 입력 처리 SQL mapping 을 실행한다.
     *
     * @param queryId -  입력 처리 SQL mapping 쿼리 ID
     *
     * @return DBMS가 지원하는 경우 insert 적용 결과 count
     */
    @Override
    public int insert(String queryId) {
        int ret = getSqlSession().insert(queryId);
        
        // CUD Logging 
//        insertLog(queryId, null);
        
        return ret;
    }

    /**
     * 입력 처리 SQL mapping 을 실행한다.
     *
     * @param queryId -  입력 처리 SQL mapping 쿼리 ID
     * @param parameterObject - 입력 처리 SQL mapping 입력 데이터를 세팅한 파라메터 객체(보통 VO 또는 Map)
     *
     * @return DBMS가 지원하는 경우 insert 적용 결과 count
     */
    @Override
    public int insert(String queryId, Object parameterObject) {
        int ret = getSqlSession().insert(queryId, parameterObject);
        
        // CUD Logging 
//        insertLog(queryId, parameterObject);
        
        return ret;
    }

    /**
     * 수정 처리 SQL mapping 을 실행한다.
     *
     * @param queryId - 수정 처리 SQL mapping 쿼리 ID
     *
     * @return DBMS가 지원하는 경우 update 적용 결과 count
     */
    @Override
    public int update(String queryId) {
        int ret = getSqlSession().update(queryId);
        
        // CUD Logging 
//        insertLog(queryId, null);
        
        return ret;
    }

    /**
     * 수정 처리 SQL mapping 을 실행한다.
     *
     * @param queryId - 수정 처리 SQL mapping 쿼리 ID
     * @param parameterObject - 수정 처리 SQL mapping 입력 데이터(key 조건 및 변경 데이터)를 세팅한 파라메터 객체(보통 VO 또는 Map)
     *
     * @return DBMS가 지원하는 경우 update 적용 결과 count
     */
    @Override
    public int update(String queryId, Object parameterObject) {
        int ret = getSqlSession().update(queryId, parameterObject);
        
        // CUD Logging 
//        insertLog(queryId, parameterObject);
        
        return ret;
    }

    /**
     * 삭제 처리 SQL mapping 을 실행한다.
     *
     * @param queryId - 삭제 처리 SQL mapping 쿼리 ID
     *
     * @return DBMS가 지원하는 경우 delete 적용 결과 count
     */
    @Override
    public int delete(String queryId) {
        int ret = getSqlSession().delete(queryId);
        
        // CUD Logging 
//        insertLog(queryId, null);
        
        return ret;
    }

    /**
     * 삭제 처리 SQL mapping 을 실행한다.
     *
     * @param queryId - 삭제 처리 SQL mapping 쿼리 ID
     * @param parameterObject - 삭제 처리 SQL mapping 입력 데이터(일반적으로 key 조건)를  세팅한 파라메터 객체(보통 VO 또는 Map)
     *
     * @return DBMS가 지원하는 경우 delete 적용 결과 count
     */
    @Override
    public int delete(String queryId, Object parameterObject) {
        int ret = getSqlSession().delete(queryId, parameterObject);
        
        // CUD Logging 
//        insertLog(queryId, parameterObject);
        
        return ret;
    }
    
    @Override
    public <E> List<E> selectList(String queryId, Object parameterObject) {
        List<E> list = getSqlSession().selectList(queryId, parameterObject);
        return list;
    }

    /**
     * CUD Logging 
     * @param queryId
     * @param parameterObject
     */
    private void insertLog(String queryId, Object parameterObject) {
        // 로깅이 트랜잭션에 영향이 없게 try/catch 처리
        try{
            // Query Statement 구하기
            Configuration configuration = getSqlSession().getConfiguration();
            MappedStatement ms = configuration.getMappedStatement(queryId);
            BoundSql boundSql = ms.getBoundSql(parameterObject);
            String sql = boundSql.getSql();
            String paramString = null;
            
            // Query에 파라미터 셋팅
            if( parameterObject != null ){
                ObjectMapper objectMapper = new ObjectMapper();
                paramString = objectMapper.writeValueAsString(parameterObject);
                
                // 해당 파라미터의 클래스가 Integer, Long, Float, Double 클래스일 경우
                if (parameterObject instanceof Integer || parameterObject instanceof Long || parameterObject instanceof Float || parameterObject instanceof Double) {
                    sql = sql.replaceFirst("\\?", parameterObject.toString());
                }
                // 해당 파라미터의 클래스가 String인 경우
                else if (parameterObject instanceof String) {
                    sql = sql.replaceFirst("\\?", "'" + parameterObject + "'");
                }
                // 해당 파라미터의 클래스가 Map인 경우
                else if (parameterObject instanceof Map) {
                    List<ParameterMapping> paramMapping = boundSql.getParameterMappings();
                    for (ParameterMapping mapping : paramMapping) {
                        String propValue = mapping.getProperty();
                        Object value = ((Map<?, ?>) parameterObject).get(propValue);
                        if (value == null) {
                            continue;
                        }
    
                        if (value instanceof String) {
                            sql = sql.replaceFirst("\\?", "'" + value + "'");
                        } else {
                            sql = sql.replaceFirst("\\?", value.toString());
                        }
                    }
                }
                // 해당 파라미터의 클래스가 사용자 정의 클래스인 경우
                else {
                    List<ParameterMapping> paramMapping = boundSql.getParameterMappings();
                    for (ParameterMapping mapping : paramMapping) {
                        try{
                            String propValue = mapping.getProperty();
                            
                            Object val = ClassUtil.getFieldValue(parameterObject, propValue);
                            Class<?> javaType = mapping.getJavaType();
                            if (String.class == javaType) {
                                sql = sql.replaceFirst("\\?", "'" + Matcher.quoteReplacement(val+"") + "'");
                            } else {
                                sql = sql.replaceFirst("\\?", val.toString());
                            }
                        }catch(RuntimeException e){
                            logger.error(e.getMessage());
                        }
                    }
                }
            }
            
            String userId = null;
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            LoginInfo principal = (LoginInfo) auth.getPrincipal();
            
            if (principal instanceof UserDetails) {
                userId = principal.getId();
            } else {
                userId = "anonymous";
            }
            Map<String, String> map = new HashMap<String, String>();
            map.put("qurNm", queryId);
            map.put("qurCn", sql);
            map.put("paramtr", paramString);
            map.put("conectIp", request.getRemoteAddr());
            map.put("registId", userId);
            
            getSqlSession().insert("ins.comp.service.sysLog.SysLog.insertByMap", map);
        }catch(Exception e){
            logger.error(e.getMessage());
        }
    }
    
}