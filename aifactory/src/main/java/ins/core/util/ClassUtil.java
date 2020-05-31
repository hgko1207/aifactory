package ins.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassUtil {

    /**
     * Class의 SimpleName을 CamelCase형식으로 변환해서 반환
     * 
     * @param clazz
     * @return
     */
    public static String getCamelcaseSimpleName(Class<?> clazz){
        String clazzName = clazz.getSimpleName();
        String camelcaseSimpleName = clazzName.substring(0, 1).toLowerCase() + clazzName.substring(1, clazzName.length());
        return camelcaseSimpleName;
    }
    
    /**
     * Class의 getMethod명 반환
     * 
     * @param clazz
     * @return
     */
    public static String getGetterMethodName(String fieldName){
        String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
        return getMethodName;
    }
    
    /**
     * Reflection을 이용한 대상 오브젝트에서 해당 필드의 값 구하기
     * 
     * @param parameterObject   대상 오브젝트
     * @param fieldName         값을 구할 필드명
     * @return
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws Exception
     */
    public static Object getFieldValue(Object parameterObject, String fieldName) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Object retObj = null;
        Class<? extends Object> paramClass = parameterObject.getClass();
        
        if( fieldName.indexOf(".") > -1 ){
            String parentFieldName = fieldName.substring(0, fieldName.indexOf("."));
            String childFieldName = fieldName.substring(fieldName.indexOf(".")+1, fieldName.length());
            
            Field field = null;
            
            try{
                field = paramClass.getDeclaredField(parentFieldName);
            }catch(NoSuchFieldException e){
                // 필드가 없을 경우 상위 클래스에서 검색
                Class<?> superClass = paramClass.getSuperclass();
                field = superClass.getDeclaredField(parentFieldName);
            }
            
            field.setAccessible(true);
            Object parentObj = field.get(parameterObject);
            retObj = getFieldValue(parentObj, childFieldName);
            
        }else{
//            Field field = paramClass.getDeclaredField(fieldName);
//            field.setAccessible(true);
//            retObj = field.get(parameterObject);
            
            Method method = paramClass.getMethod(ClassUtil.getGetterMethodName(fieldName));
            retObj = method.invoke(parameterObject);
        }
        
        return retObj;
    }
}
