package cn.ruihe.aluses.entity;


public class CommunityOut implements java.io.Serializable {


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
    private Integer isaudit;
    private String avosid;
    private Integer grouptype;
    private Integer reportnum;

    public CommunityOut() {
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
				+ ", status=" + status + ", isaudit=" + isaudit
				+ ", avosid=" + avosid + ", grouptype=" + grouptype
				+ ", reportnum=" + reportnum + "]";
	}


	public CommunityOut(int id, String groupname, Integer groupowner, Integer project, Integer ownerrole, Integer status) {
        this.id = id;
        this.groupname = groupname;
        this.groupowner = groupowner;
        this.project = project;
        this.ownerrole = ownerrole;
        this.status = status;
    }

    public CommunityOut(int id, String groupname, String groupdescribe, String grouppic, Integer groupowner, String uname, String telephone, Integer region, Integer project, Integer ownerrole, Integer status, Integer isaudit, String avosid,Integer grouptype,Integer reportnum) {
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
        this.isaudit = isaudit;
        this.avosid = avosid;
        this.grouptype = grouptype;
        this.reportnum = reportnum;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupname() {
        return this.groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getGroupdescribe() {
        return this.groupdescribe;
    }

    public void setGroupdescribe(String groupdescribe) {
        this.groupdescribe = groupdescribe;
    }

    public String getGrouppic() {
        return this.grouppic;
    }

    public void setGrouppic(String grouppic) {
        this.grouppic = grouppic;
    }

    public Integer getGroupowner() {
        return this.groupowner;
    }

    public void setGroupowner(int groupowner) {
        this.groupowner = groupowner;
    }

    public String getUname() {
        return this.uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getRegion() {
        return this.region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public Integer getProject() {
        return this.project;
    }

    public void setProject(int project) {
        this.project = project;
    }

    public Integer getOwnerrole() {
        return this.ownerrole;
    }

    public void setOwnerrole(int ownerrole) {
        this.ownerrole = ownerrole;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getIsaudit() {
        return this.isaudit;
    }

    public void setIsaudit(int isaudit) {
        this.isaudit = isaudit;
    }

    public String getAvosid() {
        return this.avosid;
    }

    public void setAvosid(String avosid) {
        this.avosid = avosid;
    }
    
    public Integer getGrouptype() {
        return this.grouptype;
    }

    public void setGrouptype(int grouptype) {
        this.grouptype = grouptype;
    }
    
    public Integer getReportnum() {
        return this.reportnum;
    }

    public void setReportnum(int reportnum) {
        this.reportnum = reportnum;
    }
    
    

}


