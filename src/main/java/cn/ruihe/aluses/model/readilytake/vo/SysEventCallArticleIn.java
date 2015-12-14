package cn.ruihe.aluses.model.readilytake.vo;

public class SysEventCallArticleIn {
	
	/** 标识 **/
    private int eid;
    
    /** 上一级标识 **/
    private int uid;
    /** 上一级标识 **/
    private int region;
    /** 上一级标识 **/
    private int project;
    
    /** 描述 **/
    private String projectname;
    /** 描述 **/
    private String uname;
    /** 描述 **/
    private String telephone;
    /** 描述 **/
    private String title;
    /** 描述 **/
    private String content;
    /** 描述 **/
    private Long begintime;
    /** 描述 **/
    private Long endtime;
    /** 描述 **/
    private Long enrollendtime;
    
     /** 状态 **/
    private int upperlimit;
    /** 状态 **/
    private double money;
    /** 状态 **/
    private int isonlinemoney;
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
	public int getRegion() {
		return region;
	}
	public void setRegion(int region) {
		this.region = region;
	}
	public int getProject() {
		return project;
	}
	public void setProject(int project) {
		this.project = project;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getBegintime() {
		return begintime;
	}
	public void setBegintime(Long begintime) {
		this.begintime = begintime;
	}
	public Long getEndtime() {
		return endtime;
	}
	public void setEndtime(Long endtime) {
		this.endtime = endtime;
	}
	public Long getEnrollendtime() {
		return enrollendtime;
	}
	public void setEnrollendtime(Long enrollendtime) {
		this.enrollendtime = enrollendtime;
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
	public int getIsonlinemoney() {
		return isonlinemoney;
	}
	public void setIsonlinemoney(int isonlinemoney) {
		this.isonlinemoney = isonlinemoney;
	}
    
    
}
