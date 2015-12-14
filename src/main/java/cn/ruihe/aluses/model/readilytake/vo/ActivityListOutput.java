package cn.ruihe.aluses.model.readilytake.vo;

import cn.ruihe.aluses.common.JsonOutputDate2Long;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Lob;

import org.hibernate.annotations.Type;

/**
 * <p>Title: ActivityListOutput.java</p>
 * <p>Description: API07 获取活动列表</p>
 * @author try
 * @date 2015年7月16日-下午6:15:41
 * @version 1.0
 */
public class ActivityListOutput {
	
	/**  **/
    private int id;
    
    /** 主题  **/
    private int eid;
    
    /** 作者  **/
    private int uid;
    
    /** 用户名  **/
    private String uname;
    
    /** 标题	  **/
    private String title;
    
    /** 内容	  **/
    private String content;
    
    /** 发布时间	时间戳，精确到妙	  **/
    private Date uptime;
    
    /** 开始时间	  **/
    private Date begintime;
    
    /** 结束时间	  **/
    private Date endtime;
    
    /** 报名截止时间	  **/
    private Date enrollendtime;
    
    /** 电话	  **/
    private String phone;
    
    /** 电话	  **/
    private String telephone;
    
    /** 报名人数上限  **/
    private int upperlimit;
    
    /** 费用  **/
    private double money;
    
    /** 是否需要交费	1不需要   2，需要**/
    private int isonlinepay;
    
    /**图片	多张图片用;隔开 **/
    private String pictures;
    
    /** 点赞数 **/
    private int praisenum;
    
    /** 报名人数	 **/
    private int enrollnum;
    
    /** 活动状态 **/
    private int status;

    /** address **/
    private String address;
    
    /** 是否已经报名 **/
    private int isenroll;
    

    /** 活动总结 **/
    private String result;
    
    
    @Lob
    @Type(type="org.springframework.orm.hibernate4.support.ClobStringType")
    @Column(length=10000)
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEid() {
		return eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
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
	
	public String getUsername() {
		return this.uname;
	}

	public void setUsername(String username) {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}

	@JsonSerialize(using = JsonOutputDate2Long.class)
	public Date getBegintime() {
		return begintime;
	}

	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}

	@JsonSerialize(using = JsonOutputDate2Long.class)
	public Date getEndtime() {
		return endtime;
	}

	@JsonSerialize(using = JsonOutputDate2Long.class)
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public Date getEnrollendtime() {
		return enrollendtime;
	}

	@JsonSerialize(using = JsonOutputDate2Long.class)
	public void setEnrollendtime(Date enrollendtime) {
		this.enrollendtime = enrollendtime;
	}

	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getUpperlimit() {
		return upperlimit;
	}

	public void setUpperlimit(int upperlimit) {
		this.upperlimit = upperlimit;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public int getIsonlinepay() {
		return isonlinepay;
	}

	public void setIsonlinepay(int isonlinepay) {
		this.isonlinepay = isonlinepay;
	}
	
	public int getIsonlinemoney() {
		return this.isonlinepay;
	}

	public void setIsonlinemoney(int isonlinepay) {
	}
	
	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}

	public int getPraisenum() {
		return praisenum;
	}

	public void setPraisenum(int praisenum) {
		this.praisenum = praisenum;
	}

	public int getEnrollnum() {
		return enrollnum;
	}

	public void setEnrollnum(int enrollnum) {
		this.enrollnum = enrollnum;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getIsenroll() {
		return isenroll;
	}

	public void setIsenroll(int isenroll) {
		this.isenroll = isenroll;
	}

}
