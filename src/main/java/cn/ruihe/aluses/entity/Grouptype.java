/**
 * 
 */
package cn.ruihe.aluses.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name = "grouptype")
public class Grouptype  implements java.io.Serializable{
	
    public Grouptype(){
    	 
    }
    private Integer id;
    private String typename;
    private String describe;
	
     
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
	
	@Column(name = "describe")
	public String getDescribe() {
		return describe;
	}
	
	public void setDescribe(String describe) {
		this.describe = describe;
	}
     
     

}
