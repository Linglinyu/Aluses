package cn.ruihe.aluses.entity;
// Generated 2015-7-15 10:57:13 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SysUser generated by hbm2java
 */
@Entity
@Table(name = "sysUser")
public class SysUser implements java.io.Serializable {


    private Integer id;
    private String username;
    private String userpwd;
    private Integer region;
    private Integer project;
    private String truename;
    private String position;
    private Integer role;
    private String telephone;
    private Integer status;
    /**  用于表示该用户是否具有删除随手拍帖子的权限 **/
    private Integer hasdelpost;
    public SysUser() {
    }


    public SysUser(Integer id, String username, String userpwd, String truename) {
        this.id = id;
        this.username = username;
        this.userpwd = userpwd;
        this.truename = truename;
    }

    public SysUser(Integer id, String username, String userpwd, Integer region, Integer project, String truename, String position, Integer role, String telephone, Integer status) {
        this.id = id;
        this.username = username;
        this.userpwd = userpwd;
        this.region = region;
        this.project = project;
        this.truename = truename;
        this.position = position;
        this.role = role;
        this.telephone = telephone;
        this.status = status;
    }

    @Id

    @Column(name = "id", unique = true, nullable = false, precision = 65, scale = 30)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "username", nullable = false, length = 20)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "userpwd", nullable = false, length = 100)
    public String getUserpwd() {
        return this.userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    @Column(name = "region", precision = 65, scale = 30)
    public Integer getRegion() {
        return this.region;
    }

    public void setRegion(Integer region) {
        this.region = region;
    }

    @Column(name = "project", precision = 65, scale = 30)
    public Integer getProject() {
        return this.project;
    }

    public void setProject(Integer project) {
        this.project = project;
    }

    @Column(name = "truename", nullable = false, length = 20)
    public String getTruename() {
        return this.truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    @Column(name = "position", length = 20)
    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Column(name = "role", precision = 65, scale = 30)
    public Integer getRole() {
        return this.role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    @Column(name = "telephone", length = 11)
    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "status", precision = 65, scale = 30)
    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "hasdelpost", precision = 65, scale = 30)
	public Integer getHasdelpost() {
		return hasdelpost;
	}


	public void setHasdelpost(Integer hasdelpost) {
		this.hasdelpost = hasdelpost;
	}

    
}


