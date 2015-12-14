package cn.ruihe.aluses.entity;
// Generated 2015-7-15 10:57:13 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * BonusId generated by hbm2java
 */
@Embeddable
public class BonusId implements java.io.Serializable {


    private String ename;
    private String job;
    private int sal;
    private int comm;

    public BonusId() {
    }

    public BonusId(String ename, String job, int sal, int comm) {
        this.ename = ename;
        this.job = job;
        this.sal = sal;
        this.comm = comm;
    }


    @Column(name = "ENAME", length = 10)
    public String getEname() {
        return this.ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    @Column(name = "JOB", length = 9)
    public String getJob() {
        return this.job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Column(name = "SAL", precision = 65, scale = 30)
    public int getSal() {
        return this.sal;
    }

    public void setSal(int sal) {
        this.sal = sal;
    }

    @Column(name = "COMM", precision = 65, scale = 30)
    public int getComm() {
        return this.comm;
    }

    public void setComm(int comm) {
        this.comm = comm;
    }

}


