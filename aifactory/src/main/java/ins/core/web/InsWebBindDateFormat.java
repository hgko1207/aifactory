package ins.core.web;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Date Type Binding용 날짜 포맷팅 클래스
 * @author itjeon
 */
public class InsWebBindDateFormat extends DateFormat{
    
    private static final long serialVersionUID = 8688049671181765079L;
    
    private static final List<? extends DateFormat> DATE_FORMATS = Arrays.asList(
            new SimpleDateFormat("yyyy-MM-dd HH:mm")
            , new SimpleDateFormat("yyyy-MM-dd")
            , new SimpleDateFormat("yyyyMMdd"));

    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        throw new UnsupportedOperationException("This date formatter can only be used to *parse* Dates.");
    }

    @Override
    public Date parse(String source, ParsePosition pos) {
        Date res = null;
        for (int i = 0; i < DATE_FORMATS.size(); i++) {
            DateFormat dateFormat = (DateFormat)DATE_FORMATS.get(i).clone();
            res = dateFormat.parse(source, pos);
            
            if (res != null) {
                return res;
            }
        }
        return null;
    }
    
    /**
     * Parses text from the beginning of the given string to produce a date.
     * The method may not use the entire text of the given string.
     * <p>
     * See the {@link #parse(String, ParsePosition)} method for more information
     * on date parsing.
     *
     * @param source A <code>String</code> whose beginning should be parsed.
     * @return A <code>Date</code> parsed from the string.
     * @exception ParseException if the beginning of the specified string
     *            cannot be parsed.
     */
    @Override
    public Date parse(String source) throws ParseException
    {
        ParsePosition pos = new ParsePosition(0);
        Date result = parse(source, pos);
        if (result != null && pos.getIndex() == 0)
            throw new ParseException("Unparseable date: \"" + source + "\"" , pos.getErrorIndex());
        
        return result;
    }
    
}