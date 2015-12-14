package cn.ruihe.aluses.model.home.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.ruihe.aluses.common.MessageException;
import cn.ruihe.aluses.common.Pager;
import cn.ruihe.aluses.entity.SysUser;
import cn.ruihe.aluses.model.home.repository.UserRepository;
import cn.ruihe.aluses.model.home.vo.SysUserOutput;
import cn.ruihe.aluses.model.readilytake.repository.CommonRepository;

/**
 * @Author DHC
 * @Date 2015-07-18 14:50:01
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommonRepository commonRepository;

    public SysUser getUserByLogin(String account, String password) {
        return userRepository.getByLogin(account, password);
    }

    public Pager<SysUserOutput> getUsers(int page, int size) {
        return userRepository.getUsers(page,size);
    }

    /**
     * 获取单个信息
     * @param id
     * @return
     */
    public SysUser getById(int id) {
        return userRepository.getById(id);
    }

    /**
     * 获取密码
     * @param id
     * @return
     */
    public String getPasswordById(int id) {
        return userRepository.getPasswordById(id);
    }

    /**
     * 修改密码
     * @param id
     * @param newPwd
     * @param oldPwd
     * @return
     */
    public int changePassword(int id, String newPwd, String oldPwd) {
        String opwd = getPasswordById(id);
        if(!oldPwd.equals(opwd)) {
            throw new MessageException("原密码输入错误!", 400);
        }
        return userRepository.changePassword(id, newPwd);
    }

    /**
     * 改变管理员状态
     * @param id
     * @param status
     * @return
     */
    public int changeStatus(int id, int status) {
        return userRepository.changeStatus(id, status);
    }

    /**
     * 添加管理员
     * @param user
     * @return
     */
    @Transactional
    public SysUser addSysUser(SysUser user) {
        String errMsg = "";
        if(user.getUsername() == null || Objects.equals(user.getUsername(), "")) {
            errMsg += "账号不能为空!\n";
        }
        if(user.getUserpwd() == null || Objects.equals(user.getUserpwd(), "")) {
            errMsg += "密码不能为空!\n";
        }
        if(user.getTruename() == null || Objects.equals(user.getTruename(), "")) {
            errMsg += "Truename不能为空!\n";
        }
        
        if(!Objects.equals(errMsg, "")) {
            throw new MessageException(errMsg, 400);
        }
        
        if(userRepository.hasUser(user.getUsername(), 0)) {
            throw new MessageException("用户账号已经存在！", 400);
        }
        int id = commonRepository.getNewId("sysUser");
        user.setId(id);
        return userRepository.addSysUser(user);
    }

    /**
     * 更新管理员资料
     * @param user
     * @return
     */
    public SysUser updateSysUser(SysUser user) {
        if(userRepository.hasUser(user.getUsername(), user.getId())) {
            throw new MessageException("用户账号已经存在！", 400);
        }
        if(user.getUserpwd() == null || Objects.equals(user.getUserpwd(), "")) {
            user.setUserpwd(getPasswordById(user.getId()));
        }
        return userRepository.updateSysUser(user);
    }
    
    /**
     * 删除管理员资料
     * @param id
     * @return
     */
    public int deleteSysUser(int id) {
    	return userRepository.deleteSysUser(id);
    }
}
