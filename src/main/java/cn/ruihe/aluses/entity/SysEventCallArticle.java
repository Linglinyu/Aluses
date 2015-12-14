package cn.ruihe.aluses.entity;
// Generated 2015-7-15 10:57:13 by Hibernate Tools 3.2.2.GA


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * SysEventCallArticle generated by hbm2java
 */
@Entity
@Table(name = "sysEventCallArticle")
public class SysEventCallArticle implements java.io.Serializable {


    private Integer id;
    private Integer eid;
    private String title;
    private Integer uid;
    private String uname;
    private String telephone;
    private Integer region;
    private Integer project;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date begintime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endtime;
    private String content;
    private Date uptime;
    private Integer status;
    private String address;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date enrollendtime;
    private Integer upperlimit;
    private String phone;
    private String pictures;
    private String smallpic;
    private Integer praisenum;
    private Integer isonlinepay;
    private Double money;
    private Integer enrollnum;

    /** 活动总结信息 **/
    private String result;
    
    
    public SysEventCallArticle() {
    }


    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SysEventCallArticle [id=" + id + ", eid=" + eid + ", title="
				+ title + ", uid=" + uid + ", uname=" + uname + ", telephone="
				+ telephone + ", region=" + region + ", project=" + project
				+ ", begintime=" + begintime + ", endtime=" + endtime
				+ ", content=" + content + ", uptime=" + uptime + ", status="
				+ status + ", address=" + address + ", enrollendtime="
				+ enrollendtime + ", upperlimit=" + upperlimit + ", phone="
				+ phone + ", pictures=" + pictures + ", smallpic=" + smallpic
				+ ", praisenum=" + praisenum + ", isonlinepay=" + isonlinepay
				+ ", money=" + money + ", enrollnum=" + enrollnum + ", result="
				+ result + "]";
	}


	public SysEventCallArticle(Integer id, Integer eid, String title, Integer uid, String uname, Integer project) {
        this.id = id;
        this.eid = eid;
        this.title = title;
        this.uid = uid;
        this.uname = uname;
        this.project = project;
    }

    @Id

    @Column(name = "id", unique = true, nullable = false, precision = 65, scale = 30)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "eid", nullable = false, precision = 65, scale = 30)
    public Integer getEid() {
        return this.eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    @Column(name = "title", nullable = false, length = 128)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "uid", nullable = false, precision = 65, scale = 30)
    public Integer getUid() {
        return this.uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Column(name = "uname", nullable = false, length = 50)
    public String getUname() {
        return this.uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    @Column(name = "telephone", length = 11)
    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "region", precision = 65, scale = 30)
    public Integer getRegion() {
        return this.region;
    }

    public void setRegion(Integer region) {
        this.region = region;
    }

    @Column(name = "project", nullable = false, precision = 65, scale = 30)
    public Integer getProject() {
        return this.project;
    }

    public void setProject(Integer project) {
        this.project = project;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "begintime", length = 19)
    public Date getBegintime() {
        return this.begintime;
    }

    public void setBegintime(Date begintime) {
        this.begintime = begintime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "endtime", length = 19)
    public Date getEndtime() {
        return this.endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    @Column(name = "content")
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "uptime", length = 19)
    public Date getUptime() {
        return this.uptime;
    }

    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }

    @Column(name = "status", precision = 65, scale = 30)
    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "address", length = 100)
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "enrollendtime", length = 19)
    public Date getEnrollendtime() {
        return this.enrollendtime;
    }

    public void setEnrollendtime(Date enrollendtime) {
        this.enrollendtime = enrollendtime;
    }

    @Column(name = "upperlimit", precision = 65, scale = 30)
    public Integer getUpperlimit() {
        return this.upperlimit;
    }

    public void setUpperlimit(Integer upperlimit) {
        this.upperlimit = upperlimit;
    }

    @Column(name = "phone", length = 20)
    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "pictures", length = 65535)
    public String getPictures() {
        return this.pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    @Column(name = "smallpic", length = 65535)
    public String getSmallpic() {
        return this.smallpic;
    }

    public void setSmallpic(String smallpic) {
        this.smallpic = smallpic;
    }

    @Column(name = "praisenum", precision = 65, scale = 30)
    public Integer getPraisenum() {
        return this.praisenum;
    }

    public void setPraisenum(Integer praisenum) {
        this.praisenum = praisenum;
    }

    @Column(name = "isonlinepay", precision = 65, scale = 30)
    public Integer getIsonlinepay() {
        return this.isonlinepay;
    }

    public void setIsonlinepay(Integer isonlinepay) {
        this.isonlinepay = isonlinepay;
    }

    @Column(name = "money", precision = 22, scale = 0)
    public Double getMoney() {
        return this.money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    @Column(name = "enrollnum", precision = 65, scale = 30)
    public Integer getEnrollnum() {
        return this.enrollnum;
    }

    public void setEnrollnum(Integer enrollnum) {
        this.enrollnum = enrollnum;
    }


    @Column(name = "result", length = 65535)
	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}

    
}

