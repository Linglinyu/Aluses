package cn.ruihe.aluses.common;

import cn.ruihe.utils.AppTool;

import java.util.List;
import java.util.Map;

public class Pager<T> {
    private int page = 1;
    private int size = 12;
    private int count;

    private List<T> datas;

    public Pager(int count) {
        this.count = count;
    }

    public Pager(int page, int size, int count){
        this.page = page;
        this.size = size;
        this.count = count;
    }

    public Pager<T> strict() {
        if(page > getPages()) {
            this.page = getPages();
        }
        if(page < 1) {
            page = 1;
        }
        return this;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public int getPages() {
        return (count + size - 1) / size;
    }

    public int getStartIndex() {
        return (page - 1) * size + 1;
    }

    public int getEndIndex() {
        return page * size;
    }

    public boolean hasNext() {
        return page < getPages();
    }

    public boolean hasPrev() {
        return page > 1;
    }

    public String pagesLink(Map<String, String[]> params) {
        StringBuilder sb = new StringBuilder();

        if(hasPrev()) {
            sb.append("<li><a href=\"@url&page=0\">首页</a></li>");
            sb.append(String.format("<li><a href=\"@url&page=%d\">上页</a></li>", page - 1));
        } else {
            sb.append("<li class=\"am-disabled\"><a>首页</a></li>");
            sb.append("<li class=\"am-disabled\"><a>上页</a></li>");
        }

        int links = 10;

        int middle = (links + 1) / 2;
        int min = 1, max = getPages(), pages = getPages();
        if(pages > links) {
            if(page > middle) {
                min = page - middle;
                max = min + links;
                if(pages - page - 1 < middle) {
                    min = pages - links;
                    max = pages;
                }
            } else {
                max = Math.min(links, pages);
            }
        }
        for(int i = min; i <= max; i++){
            if(i == min && i > 1){
                sb.append("<li>……</li>");
            } else if(i == max && i < pages){
                sb.append("<li>……</li>");
            } else if(i == page){
                sb.append(String.format("<li class=\"am-active\"><a>%d</a></li>", i));
            } else {
                sb.append(String.format("<li><a href=\"@url&page=%d\">%d</a></li>", i, i));
            }
        }
        if(hasNext()){
            sb.append(String.format("<li><a href=\"@url&page=%d\">下页</a></li>", page + 1));
            sb.append(String.format("<li><a href=\"@url&page=%d\">尾页</a></li>", pages));
        } else {
            sb.append("<li class=\"am-disabled\"><a>下页</a></li>");
            sb.append("<li class=\"am-disabled\"><a>尾页</a></li>");
        }
        if(params == null || params.isEmpty()){
            return sb.toString().replaceAll("@url&", "?");
        } else {
            return sb.toString().replaceAll("@url", AppTool.genUrlWithParams(params, ""));
        }
    }
}
