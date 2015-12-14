/**
 * 
 */
package cn.ruihe.aluses.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Parameter generated by hbm2java
 */
@Entity
@Table(name = "reporttype")
public class Reporttype implements java.io.Serializable{

	private Integer id;
	private String typename;
	private Integer category;
	private String describe;
	private Integer status;
	
	public Reporttype(){
		
	}

	@Id
	@Column(name = "id",unique = true, nullable = false, precision = 65, scale = 30)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "typename")
	public String getTypename() {
		return typename;
	}

	
	public void setTypename(String typename) {
		this.typename = typename;
	}

	@Column(name = "category")
	public Integer getCategory() {
		return category;
	}

	
	public void setCategory(Integer category) {
		this.category = category;
	}

	@Column(name = "describe")
	public String getDescribe() {
		return describe;
	}

	
	public void setDescribe(String describe) {
		this.describe = describe;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	
	public void setStatus(Integer status) {
		this.status = status;
	} 
	
	
}