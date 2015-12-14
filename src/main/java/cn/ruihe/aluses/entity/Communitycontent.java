package cn.ruihe.aluses.entity;
// Generated 2015-7-15 10:57:13 by Hibernate Tools 3.2.2.GA


import javax.persistence.*;
import java.util.Date;

/**
 * Communitycontent generated by hbm2java
 */
@Entity
@Table(name = "communitycontent")
public class Communitycontent implements java.io.Serializable {


    private int id;
    private int groupid;
    private int uid;
    private String uname;
    private String telephone;
    private String content;
    private Date senddate;
    private int status;

    public Communitycontent() {
    }


    public Communitycontent(int id, int groupid, int uid, String uname, Date senddate) {
        this.id = id;
        this.groupid = groupid;
        this.uid = uid;
        this.uname = uname;
        this.senddate = senddate;
    }

    public Communitycontent(int id, int groupid, int uid, String uname, String telephone, String content, Date senddate, int status) {
        this.id = id;
        this.groupid = groupid;
        this.uid = uid;
        this.uname = uname;
        this.telephone = telephone;
        this.content = content;
        this.senddate = senddate;
        this.status = status;
    }

    @Id

    @Column(name = "id", unique = true, nullable = false, precision = 65, scale = 30)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "groupid", nullable = false, precision = 65, scale = 30)
    public int getGroupid() {
        return this.groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    @Column(name = "uid", nullable = false, precision = 65, scale = 30)
    public int getUid() {
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

    @Column(name = "content")
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "senddate", nullable = false, length = 19)
    public Date getSenddate() {
        return this.senddate;
    }

    public void setSenddate(Date senddate) {
        this.senddate = senddate;
    }

    @Column(name = "status", precision = 65, scale = 30)
    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}


