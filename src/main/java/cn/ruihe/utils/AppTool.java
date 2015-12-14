package cn.ruihe.utils;


import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class AppTool {
    public static boolean isAjax(HttpServletRequest req) {
        String rType = req.getHeader("X-Requested-With");
        return rType != null && rType.equals("XMLHttpRequest");
    }

    public static String genUrlWithParams(Map<String, String[]> params, String baseUrl) {
        if(params != null && !params.isEmpty()){
            StringBuilder sb = new StringBuilder(baseUrl + "?");
            for(String key : params.keySet()){
                for(String v : params.get(key)){
                    try {
                        sb.append(key).append("=").append(URLEncoder.encode(v, "utf-8")).append("&");
                    } catch (UnsupportedEncodingException e) {
                        sb.append(key).append("=").append(v).append("&");
                    }
                }
            }
            String url = sb.toString();
            return url.substring(0, url.length() - 1);
        }
        return baseUrl;
    }

    public static String genPageHtml(Page page, Map<String, String[]> params) {
        StringBuilder sb = new StringBuilder();
        if(page.hasPrevious()){
            sb.append("<li><a href=\"@url&page=0\">首页</a></li>");
            sb.append(String.format("<li><a href=\"@url&page=%d\">上页</a></li>", page.previousPageable().getPageNumber()));
        } else {
            sb.append("<li class=\"am-disabled\"><a>首页</a></li>");
            sb.append("<li class=\"am-disabled\"><a>上页</a></li>");
        }
        int count = 9;
        int middle = (count + 1) / 2;
        int number = page.getNumber() + 1;
        int min = 0, max = page.getTotalPages();
        if(page.getTotalPages() > count){
            if(number > middle){
                min = number - middle;
                max = min + count;
                if(page.getTotalPages() - page.getNumber() < middle) {
                    min = page.getTotalPages() - count;
                    max = page.getTotalPages();
                }
            } else {
                max = Math.min(count, page.getTotalPages());
            }
        }
        max--;
        for(int i = min; i <= max; i++){
            if(i == min && i > 0){
                sb.append("<li>……</li>");
            } else if(i == max && i < page.getTotalPages() - 1){
                sb.append("<li>……</li>");
            } else if(i == page.getNumber()){
                sb.append(String.format("<li class=\"am-active\"><a>%d</a></li>", i + 1));
            } else {
                sb.append(String.format("<li><a href=\"@url&page=%d\">%d</a></li>", i, i + 1));
            }
        }
        if(page.hasNext()){
            sb.append(String.format("<li><a href=\"@url&page=%d\">下页</a></li>", page.nextPageable().getPageNumber()));
            sb.append(String.format("<li><a href=\"@url&page=%d\">尾页</a></li>", page.getTotalPages() - 1));
        } else {
            sb.append("<li class=\"am-disabled\"><a>下页</a></li>");
            sb.append("<li class=\"am-disabled\"><a>尾页</a></li>");
        }
        if(params == null || params.isEmpty()){
            return sb.toString().replaceAll("@url&", "?");
        } else {
            return sb.toString().replaceAll("@url", genUrlWithParams(params, ""));
        }
    }
}
