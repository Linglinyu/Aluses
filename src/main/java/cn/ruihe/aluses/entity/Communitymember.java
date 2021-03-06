package cn.ruihe.aluses.entity;
// Generated 2015-7-15 10:57:13 by Hibernate Tools 3.2.2.GA


import javax.persistence.*;
import java.util.Date;

/**
 * Communitymember generated by hbm2java
 */
@Entity
@Table(name = "communitymember")
public class Communitymember implements java.io.Serializable {


    private Integer id;
    private Integer groupid;
    private Integer uid;
    private String uname;
    private String telephone;
    private Integer region;
    private Integer project;
    private Integer role;
    private Integer managerid;
    private Integer status;
    private Date applydate;
    private Date auditdate;
    private Integer auditorid;
    private Integer auditorrole;
    private String groupname;

    public Communitymember() {
    }


    public Communitymember(int id, Integer groupid, Integer uid, String uname, Integer project, Integer role, Integer status, Date applydate) {
        this.id = id;
        this.groupid = groupid;
        this.uid = uid;
        this.uname = uname;
        this.project = project;
        this.role = role;
        this.status = status;
        this.applydate = applydate;
    }

    public Communitymember(int id, Integer groupid, Integer uid, String uname, String telephone, Integer region, Integer project, Integer role, Integer managerid, Integer status, Date applydate, Date auditdate, Integer auditorid, Integer auditorrole) {
        this.id = id;
        this.groupid = groupid;
        this.uid = uid;
        this.uname = uname;
        this.telephone = telephone;
        this.region = region;
        this.project = project;
        this.role = role;
        this.managerid = managerid;
        this.status = status;
        this.applydate = applydate;
        this.auditdate = auditdate;
        this.auditorid = auditorid;
        this.auditorrole = auditorrole;
    }

    @Id

    @Column(name = "id", unique = true, nullable = false, precision = 65, scale = 30)
    public Integer getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "groupid", nullable = false, precision = 65, scale = 30)
    public Integer getGroupid() {
        return this.groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    @Column(name = "uid", nullable = false, precision = 65, scale = 30)
    public Integer getUid() {
        return this.uid;
    }

    public void setUid(int uid) {
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

    public void setRegion(int region) {
        this.region = region;
    }

    @Column(name = "project", nullable = false, precision = 65, scale = 30)
    public Integer getProject() {
        return this.project;
    }

    public void setProject(int project) {
        this.project = project;
    }

    @Column(name = "role", nullable = false, precision = 65, scale = 30)
    public Integer getRole() {
        return this.role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Column(name = "managerid", precision = 65, scale = 30)
    public Integer getManagerid() {
        return this.managerid;
    }

    public void setManagerid(int managerid) {
        this.managerid = managerid;
    }

    @Column(name = "status", nullable = false, precision = 65, scale = 30)
    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "applydate", nullable = false, length = 19)
    public Date getApplydate() {
        return this.applydate;
    }

    public void setApplydate(Date applydate) {
        this.applydate = applydate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "auditdate", length = 19)
    public Date getAuditdate() {
        return this.auditdate;
    }

    public void setAuditdate(Date auditdate) {
        this.auditdate = auditdate;
    }

    @Column(name = "auditorid", precision = 65, scale = 30)
    public Integer getAuditorid() {
        return this.auditorid;
    }

    public void setAuditorid(int auditorid) {
        this.auditorid = auditorid;
    }

    @Column(name = "auditorrole", precision = 65, scale = 30)
    public Integer getAuditorrole() {
        return this.auditorrole;
    }

    public void setAuditorrole(int auditorrole) {
        this.auditorrole = auditorrole;
    }

    @Column(name = "groupname")
	public String getGroupname() {
		return groupname;
	}


	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

    

}


