package cn.ruihe.aluses.model.readilytake.vo;

import java.util.Date;

/**
 * <p>Title: TeamChatListOutput.java</p>
 * <p>Description: API05 获取圈子聊天内容 </p>
 * @author try
 * @date 2015年7月16日-下午5:20:19
 * @version 1.0
 */
public class TeamChatListOutput {
	
	/**  **/
    private int id;
    
    /**  **/
    private int groupid;
    
    /** 业主id **/
    private int uid;
    
    /** 圈子描述  **/	
    private Date senddate;
    
    /** 聊天内容  **/
    private String content;
    
    /** 姓名1  **/
    private String uname;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public Date getSenddate() {
		return senddate;
	}

	public void setSenddate(Date senddate) {
		this.senddate = senddate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
    
    

    
}
