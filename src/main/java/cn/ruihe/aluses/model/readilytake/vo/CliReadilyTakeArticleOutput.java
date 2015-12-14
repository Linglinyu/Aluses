package cn.ruihe.aluses.model.readilytake.vo;

import cn.ruihe.aluses.common.JsonOutputDate2Long;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

/**
 * <p>Title: CliReadilyTakeArticleOutput.java</p>
 * <p>Description:  API02 获取随手拍文章列表</p>
 * @author try
 * @date 2015年7月16日-下午1:18:54
 * @version 1.0
 */
public class CliReadilyTakeArticleOutput {
	
	/** 帖子编号 **/
    private int id;
    
	/** 标识  **/
    private int rid;
	
    /** 作者 **/
    private int uid;
    
    /** 用户名 **/
    private String uname;
    
    /** 电话 **/
    private String telephone;
    
    /** 标题 **/
    private String title;
    
     /** 点击次数 **/
    private int hit;
    
    /** 评论 **/
    private int cnmu;
    
    /** 内容 **/
    private String content;
    
    /** 发布时间 **/
    private Date uptime;
    
    /** 状态 **/
    private int status;
    
    /** 图片	多张图片用;隔开 **/
    private String pictures;
    
    /** 缩略图	分辨率256*256  **/
    private String smallpic;
    
    /** 点赞数 **/
    private int praisenum;
    
    /** 发帖者头像 **/
    private String headpic;
    
    /** 发帖者用户等级 **/
    private String ulevel;
    
    /** 发帖者用户等级头像 **/
    private String levelpic;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public int getCnmu() {
		return cnmu;
	}

	public void setCnmu(int cnmu) {
		this.cnmu = cnmu;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	public Date getUptime() {
		return uptime;
	}

	@JsonSerialize(using = JsonOutputDate2Long.class)
	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}

	public String getSmallpic() {
		return smallpic;
	}

	public void setSmallpic(String smallpic) {
		this.smallpic = smallpic;
	}

	public int getPraisenum() {
		return praisenum;
	}

	public void setPraisenum(int praisenum) {
		this.praisenum = praisenum;
	}

	
	public String getHeadpic() {
		return headpic;
	}

	
	public void setHeadpic(String headpic) {
		this.headpic = headpic;
	}

	
	public String getUlevel() {
		return ulevel;
	}

	
	public void setUlevel(String ulevel) {
		this.ulevel = ulevel;
	}

	
	public String getLevelpic() {
		return levelpic;
	}

	
	public void setLevelpic(String levelpic) {
		this.levelpic = levelpic;
	}
    
    
    
}
