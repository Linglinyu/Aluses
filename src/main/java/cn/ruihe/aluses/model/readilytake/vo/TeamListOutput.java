package cn.ruihe.aluses.model.readilytake.vo;

/**
 * <p>Title: TeamListOutput.java</p>
 * <p>Description: API04 获取圈子列表</p>
 * @author try
 * @date 2015年7月16日-下午1:21:25
 * @version 1.0
 */
public class TeamListOutput {
	
	/**  **/
    private int id;
    
    /** leancloud标识 **/
    private String avosid;
    
    /** 创建者id **/
    private int groupowner;
    
     /** 状态 **/
    private String groupname;
    
    /** 圈子描述  **/
    private String groupdescribe;
    
    /** 图片  **/
    private String grouppic;
    
    /** 姓名1  **/
    private String uname;
    
    /** telephone1  **/
    private String telephone;
    
    
    
    //added by cwx 20150826
    /** 圈子类型  **/
    private int grouptype;
    
    /** 圈子类型名称 **/
    private String grouptypename;
    
    /** 圈子人数  **/
    private int memnum;

    /** 成员昵称  **/
    private String memname;
    
    /** 成员昵称  **/
    private int memstatus;
    
    /** 是否审核  **/
    private int isaudit;
    
    
    /**
	 * @return the isaudit
	 */
	public int getIsaudit() {
		return isaudit;
	}

	/**
	 * @param isaudit the isaudit to set
	 */
	public void setIsaudit(int isaudit) {
		this.isaudit = isaudit;
	}

	private int status;
    
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getAvosid() {
		return avosid;
	}

	public void setAvosid(String avosid) {
		this.avosid = avosid;
	}

	public int getGroupowner() {
		return groupowner;
	}

	public void setGroupowner(int groupowner) {
		this.groupowner = groupowner;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getGroupdescribe() {
		return groupdescribe;
	}

	public void setGroupdescribe(String groupdescribe) {
		this.groupdescribe = groupdescribe;
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

	public String getGrouppic() {
		return grouppic;
	}

	public void setGrouppic(String grouppic) {
		this.grouppic = grouppic;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return the grouptype
	 */
	public int getGrouptype() {
		return grouptype;
	}

	/**
	 * @param grouptype the grouptype to set
	 */
	public void setGrouptype(int grouptype) {
		this.grouptype = grouptype;
	}

	/**
	 * @return the grouptypename
	 */
	public String getGrouptypename() {
		return grouptypename;
	}

	/**
	 * @param grouptypename the grouptypename to set
	 */
	public void setGrouptypename(String grouptypename) {
		this.grouptypename = grouptypename;
	}

	/**
	 * @return the memnum
	 */
	public int getMemnum() {
		return memnum;
	}

	/**
	 * @param memnum the memnum to set
	 */
	public void setMemnum(int memnum) {
		this.memnum = memnum;
	}

	/**
	 * @return the memname
	 */
	public String getMemname() {
		return memname;
	}

	/**
	 * @param memname the memname to set
	 */
	public void setMemname(String memname) {
		this.memname = memname;
	}

	/**
	 * @return the memstatus
	 */
	public int getMemstatus() {
		return memstatus;
	}

	/**
	 * @param memstatus the memstatus to set
	 */
	public void setMemstatus(int memstatus) {
		this.memstatus = memstatus;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	

    
}
