package cn.ruihe.aluses.entity;
// Generated 2015-7-15 10:57:13 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Community generated by hbm2java
 */
@Entity
@Table(name = "community")
public class Community implements java.io.Serializable {


    private Integer id;
    private String groupname;
    private String groupdescribe;
    private String grouppic;
    private Integer groupowner;
    private String uname;
    private String telephone;
    private Integer region;
    private Integer project;
    private Integer ownerrole;
    private Integer status;
    private Integer ismemberaudit;
    private String avosid;
    private Integer grouptype;
    private Integer reportnum;

    public Community() {
    }


    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Community [id=" + id + ", groupname=" + groupname
				+ ", groupdescribe=" + groupdescribe + ", grouppic=" + grouppic
				+ ", groupowner=" + groupowner + ", uname=" + uname
				+ ", telephone=" + telephone + ", region=" + region
				+ ", project=" + project + ", ownerrole=" + ownerrole
				+ ", status=" + status + ", ismemberaudit=" + ismemberaudit
				+ ", avosid=" + avosid + ", grouptype=" + grouptype
				+ ", reportnum=" + reportnum + "]";
	}


	public Community(int id, String groupname, Integer groupowner, Integer project, Integer ownerrole, Integer status) {
        this.id = id;
        this.groupname = groupname;
        this.groupowner = groupowner;
        this.project = project;
        this.ownerrole = ownerrole;
        this.status = status;
    }

    public Community(int id, String groupname, String groupdescribe, String grouppic, Integer groupowner, String uname, String telephone, Integer region, Integer project, Integer ownerrole, Integer status, Integer ismemberaudit, String avosid,Integer grouptype,Integer reportnum) {
        this.id = id;
        this.groupname = groupname;
        this.groupdescribe = groupdescribe;
        this.grouppic = grouppic;
        this.groupowner = groupowner;
        this.uname = uname;
        this.telephone = telephone;
        this.region = region;
        this.project = project;
        this.ownerrole = ownerrole;
        this.status = status;
        this.ismemberaudit = ismemberaudit;
        this.avosid = avosid;
        this.grouptype = grouptype;
        this.reportnum = reportnum;
    }

    @Id

    @Column(name = "id", unique = true, nullable = false, precision = 65, scale = 30)
    public Integer getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "groupname", nullable = false, length = 20)
    public String getGroupname() {
        return this.groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @Column(name = "groupdescribe", length = 65535)
    public String getGroupdescribe() {
        return this.groupdescribe;
    }

    public void setGroupdescribe(String groupdescribe) {
        this.groupdescribe = groupdescribe;
    }

    @Column(name = "grouppic", length = 100)
    public String getGrouppic() {
        return this.grouppic;
    }

    public void setGrouppic(String grouppic) {
        this.grouppic = grouppic;
    }

    @Column(name = "groupowner", nullable = false, precision = 65, scale = 30)
    public Integer getGroupowner() {
        return this.groupowner;
    }

    public void setGroupowner(int groupowner) {
        this.groupowner = groupowner;
    }

    @Column(name = "uname", length = 50)
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

    @Column(name = "ownerrole", nullable = false, precision = 65, scale = 30)
    public Integer getOwnerrole() {
        return this.ownerrole;
    }

    public void setOwnerrole(int ownerrole) {
        this.ownerrole = ownerrole;
    }

    @Column(name = "status", nullable = false, precision = 65, scale = 30)
    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Column(name = "ismemberaudit", precision = 65, scale = 30)
    public Integer getIsmemberaudit() {
        return this.ismemberaudit;
    }

    public void setIsmemberaudit(int ismemberaudit) {
        this.ismemberaudit = ismemberaudit;
    }

    @Column(name = "avosid", length = 32)
    public String getAvosid() {
        return this.avosid;
    }

    public void setAvosid(String avosid) {
        this.avosid = avosid;
    }
    
    @Column(name = "grouptype", precision = 65, scale = 30)
    public Integer getGrouptype() {
        return this.grouptype;
    }

    public void setGrouptype(int grouptype) {
        this.grouptype = grouptype;
    }
    
    @Column(name = "reportnum", precision = 65, scale = 30)
    public Integer getReportnum() {
        return this.reportnum;
    }

    public void setReportnum(int reportnum) {
        this.reportnum = reportnum;
    }
    
    

}


