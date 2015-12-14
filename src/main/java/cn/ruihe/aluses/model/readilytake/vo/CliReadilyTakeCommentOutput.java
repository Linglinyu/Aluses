package cn.ruihe.aluses.model.readilytake.vo;

import cn.ruihe.aluses.common.JsonOutputDate2Long;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

/**
 * @Author DHC
 * @Date 2015-07-15 13:08:06
 */
public class CliReadilyTakeCommentOutput {
    private int uid;
    private String uname;
    private String content;
    private Date update;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @JsonSerialize(using = JsonOutputDate2Long.class)
    public Date getUpdate() {
        return update;
    }

    public void setUpdate(Date update) {
        this.update = update;
    }
}
