package ins.core.util;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

public class StringUtil {

    public static String escapeHtml(String html){
        return StringEscapeUtils.escapeHtml4(html);
    }

    public static String convertTuiEditorSave(String cn) {
        String rslt = null;
        if( !StringUtils.isEmpty(cn) ){
            //rslt = cn.replaceAll(System.getProperty("line.separator"), "");
            rslt = cn.replaceAll("(\r\n|\r|\n|\n\r)", "");
            rslt = rslt.replaceAll("'", "%27");
        }
        return rslt;
    }
}