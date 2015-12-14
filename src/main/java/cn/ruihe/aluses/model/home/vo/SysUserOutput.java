package cn.ruihe.aluses.model.home.vo;

import cn.ruihe.aluses.entity.SysUser;

public class SysUserOutput extends SysUser {
    private String projectname;
    private String positionName;
    private String roleName;
    private String statusName;

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRole(Integer role) {
        super.setRole(role);
        switch (role){
            case 1: this.roleName = "系统管理员"; break;
            case 2: this.roleName = "总部管理员"; break;
            case 3: this.roleName = "片区管理员"; break;
            case 4: this.roleName = "项目管理员"; break;
            default: this.roleName = "-";
        }
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatus(Integer status) {
        switch (status){
            case 0: this.statusName = "未审核"; break;
            case 1: this.statusName = "已审核"; break;
            case 2: this.statusName = "禁用"; break;
            case 3: this.statusName = "删除"; break;
            default: this.statusName = "-";
        }
    }

}


