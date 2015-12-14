package cn.ruihe.utils;

import org.springframework.web.util.HtmlUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JspUtil {
    public static String escapeXml(String s){
        return HtmlUtils.htmlEscape(s);
    }

    public static String date(String format){
        return date(new Date(), format);
    }

    public static String date(Date date, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String summay(String s, int len) {
        if(s.length() <= len) {
            return s;
        } else {
            return s.substring(0, len) + "…";
        }
    }
}
