package ins.core.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.CaseFormat;

import ins.aifactory.service.user.User;

public class MybatisMapperFactory {
    
    // 대문자 문자열을 UnderScore로 연결 
    private List<String> upperUnderScoreList = new ArrayList<>();
    
    // 소문자로 시작하는 CamelCasel
    private List<String> lowerCamelList = new ArrayList<>();
    
    // 포함시키지 않을 Field Name
    private String[] excludeFieldNames = {"loginInfo"};
    
    public static void main(String[] args) {
        Class<?> clazz = User.class;
        MybatisMapperFactory mapperFactory = new MybatisMapperFactory();
        mapperFactory.makeColumnList(clazz);
        
        // print resultMap 
        mapperFactory.printResultMap(clazz);
        
        // print column
        mapperFactory.printColumns();
        
        // print insert values
        mapperFactory.printInsertValues();
        
        // print update statement
        mapperFactory.printUpdateSetStatement();
        
    }
    
    private void printUpdateSetStatement() {
        System.out.println("# printUpdateSetStatement ########################################################################");
        for( int i=0; i<this.lowerCamelList.size(); i++ ) {
            if(i==0)
                System.out.println(this.getDbColumnName(i) + "=" + this.getDbColumnValue(i));
            else
                System.out.println("            ,"+this.getDbColumnName(i) + "=" + this.getDbColumnValue(i));
            
            //GROUP_NM = #{groupNm}
        }
    }

    private void printInsertValues() {
        System.out.println("# insert values ########################################################################");
        for( int i=0; i<this.lowerCamelList.size(); i++ ) {
            if(i==0)
                System.out.println(this.getDbColumnValue(i));
            else
                System.out.println("            ,"+this.getDbColumnValue(i));
        }
    }

    // DB Column Value
    private String getDbColumnValue(int i) {
        String name = this.lowerCamelList.get(i);
        if( name.equals("register") || name.equals("updusr") )
            name = "#{loginInfo.id}";
        else if( name.equals("registDttm") )
            name = "CURRENT_TIMESTAMP";
        else if( name.equals("updtDttm") )
            name = "CURRENT_TIMESTAMP";
        else
            name = "#{" + name + "}";
            
        
        return name;
    }

    private void printColumns() {
        System.out.println("# columns ########################################################################");
        for( int i=0; i<this.upperUnderScoreList.size(); i++ ) {
            if(i==0)
                System.out.println(this.getDbColumnName(i));
            else
                System.out.println("        ,"+this.getDbColumnName(i));
        }
    }

    // resultMap 출력
    private void printResultMap(Class<?> clazz) {
        System.out.println("# resultMap ########################################################################");
        System.out.println("<resultMap id=\"resultMap\" type=\"" + clazz.getCanonicalName() + "\">");
        
        for(int i=0; i<this.lowerCamelList.size(); i++) {
            System.out.println("        <result property=\"" + this.getJavaFieldName(i) + "\"              column=\"" + this.getDbColumnName(i) + "\"/>");
        }
        System.out.println("    </resultMap>");
    }
    
    // DB Column Name 구하기
    private String getDbColumnName(int i) {
        String name = this.upperUnderScoreList.get(i);
        if(name.equals("REGISTER"))
            name = "REGISTER_ID";
        else if(name.equals("UPDUSR"))
            name = "UPDUSR_ID";
        return name;
    }

    // Java Filed Name 구하기
    private String getJavaFieldName(int i) {
        String name = this.lowerCamelList.get(i);
        if(name.equals("register"))
            name = "register.id";
        else if(name.equals("updusr"))
            name = "updusr.id";
        return name;
    }

    // class에서 lowerCamelList, upperUnderScoreList 구하기
    private void makeColumnList(Class<?> clazz) {
        List<Field> fieldList = getFieldList(clazz);
        String item;
        for( Field field : fieldList ) {
            item = field.getName();
            this.lowerCamelList.add(item);
            this.upperUnderScoreList.add(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, item));
        }
    }

    // Class의 Field List 구하기
    private List<Field> getFieldList(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        
        Class<?> superClazz = clazz.getSuperclass();
        
        // 현재 Class의 fieldList 구하기
        Field[] fields = clazz.getDeclaredFields();
        String fieldName;
        for( int i=0; i<fields.length; i++ ) {
            fieldName = fields[i].getName();
            if( !Arrays.asList(excludeFieldNames).contains(fieldName) ) {
                fieldList.add(fields[i]);
            }
        }
        
        // SuperClass가 Object가 아닐 경우 재귀호출
        if( !(superClazz.getCanonicalName().equals(Object.class.getCanonicalName())) ) {
            fieldList.addAll(this.getFieldList(superClazz));
        }
            
        return fieldList;
    }
}
