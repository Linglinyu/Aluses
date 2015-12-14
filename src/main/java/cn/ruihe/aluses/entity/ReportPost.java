/**
 * 
 */
package cn.ruihe.aluses.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name = "reportPost")
public class ReportPost implements java.io.Serializable{
	/**
	 * 
	 */
	public ReportPost() {
		
	}
	
	private Integer id;
	private Integer pid;
	private Integer uid;
	private String uname;
	private Integer type;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date uptime;
    private String pic;
    private Integer status;
	private Integer category;
	
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReportPost [id=" + id + ", pid=" + pid + ", uid=" + uid
				+ ", type=" + type + ", content=" + content + ", uptime="
				+ uptime + ", pic=" + pic + ", status=" + status
				+ ", category=" + category + "]";
	}

	@Id
	@Column(name = "id",unique = true, nullable = false, precision = 65, scale = 30)
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "pid")
	public Integer getPid() {
		return pid;
	}
	
	
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	
	@Column(name = "uid")
	public Integer getUid() {
		return uid;
	}
	
	
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	
	@Column(name = "type")
	public Integer getType() {
		return type;
	}
	
	
	public void setType(Integer type) {
		this.type = type;
	}
	
	@Column(name = "content")
	public String getContent() {
		return content;
	}
	
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name = "uptime")
	public Date getUptime() {
		return uptime;
	}
	
	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}
	
	@Column(name = "pic")
	public String getPic() {
		return pic;
	}
	
	
	public void setPic(String pic) {
		this.pic = pic;
	}
	
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}
	
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "category")
	public Integer getCategory() {
		return category;
	}
	
	public void setCategory(Integer category) {
		this.category = category;
	}

	/**
	 * @return the uname
	 */
	public String getUname() {
		return uname;
	}

	/**
	 * @param uname the uname to set
	 */
	public void setUname(String uname) {
		this.uname = uname;
	}
	
	
	

}
