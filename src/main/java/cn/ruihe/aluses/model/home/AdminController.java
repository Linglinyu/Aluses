package cn.ruihe.aluses.model.home;

import cn.ruihe.aluses.common.MessageException;
import cn.ruihe.aluses.common.Pager;
import cn.ruihe.aluses.common.message.ApiResult;
import cn.ruihe.aluses.entity.SysUser;
import cn.ruihe.aluses.model.home.interceptor.AllowRole;
import cn.ruihe.aluses.model.home.service.ProjectService;
import cn.ruihe.aluses.model.home.service.UserService;
import cn.ruihe.aluses.model.home.vo.SimpleProjectOutput;
import cn.ruihe.aluses.model.home.vo.SysUserOutput;
import cn.ruihe.utils.Encrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author DHC
 * @Date 2015-07-18 17:07:51
 */
@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

//    @AllowRole(1)
//    @RequestMapping(method = RequestMethod.GET)
//    public String getUsers(Model model) {
//        List<SysUserOutput> users = userService.getUsers();
//        List<SimpleProjectOutput> projects = projectService.getSimpleProjects();
//        model.addAttribute("users", users);
//        model.addAttribute("projects", projects);
//        return "admin/admins";
//    }
    
    //added by cwx
    @AllowRole(1)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getUsers(HttpSession session,Model model,@RequestParam(value = "page", required = false) Integer page,
    		@RequestParam(value = "key", required = false) String key) {
    	if(page == null) {
            page = 1;
        }
        Map<String, String[]> pms = new HashMap<>();
        if(!StringUtils.isEmpty(key)) {
            pms.put("key", new String[]{key});
        } else {
            key = null;
        }
        Pager<SysUserOutput> pager = userService.getUsers(page, 12);
        List<SimpleProjectOutput> projects = projectService.getSimpleProjects();
        model.addAttribute("users", pager.getDatas());
        model.addAttribute("pager", pager.pagesLink(pms));
        model.addAttribute("projects", projects);
        model.addAttribute("key", key);
        return "admin/admins";
    }

    @AllowRole(1)
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ApiResult addUser(SysUser user) {
//        if(user.getRegion() < 1) {
//            throw new MessageException("【片区】项不能小于1", 400);
//        }
    	user.setRegion(1);
    	Encrypt encrypt=new Encrypt();
    	
    	if(user.getUserpwd()!=null&&user.getUserpwd()!="")
    	{
    		System.out.println(user.getUserpwd());
    		encrypt.setKey(user.getUserpwd());
    		encrypt.setEncString(user.getUserpwd());
    		user.setUserpwd(encrypt.getStrMi());
    		System.out.println(encrypt.getStrMi());
    	}
        if(user.getId() == null) {
            return ApiResult.SUCCESS(userService.addSysUser(user));
        } else {
            return ApiResult.SUCCESS(userService.updateSysUser(user));
        }
    }

    @AllowRole(1)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getUser(@PathVariable int id) {
        return ApiResult.SUCCESS(userService.getById(id));
    }

//    @AllowRole(1)
//    @RequestMapping(value = "admins/:id", method = RequestMethod.POST)
//    @ResponseBody
//    public ApiResult updateUser(SysUser user, @PathVariable int id) {
//        if(user.getId() != id) {
//            throw new MessageException("错误的请求！", 400);
//        }
//        return ApiResult.SUCCESS(userService.updateSysUser(user));
//    }

//    @AllowRole({1,4})
//    @RequestMapping(method = RequestMethod.POST)
//    @ResponseBody
//    public ApiResult updateProfile(HttpSession session, SysUser user) {
//        SysUser u = (SysUser) session.getAttribute("user");
//        int uid = u.getId();
//        user.setRegion(1);
//        if(user.getId() != uid) {
//            throw new MessageException("错误的请求！", 400);
//        }
//        return ApiResult.SUCCESS(userService.updateSysUser(user));
//    }

    @AllowRole({1, 4})
    @RequestMapping(value = "changepwd", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult changePassword(HttpSession session, @RequestParam String newPassword,
                                    @RequestParam String reNewPassword, @RequestParam String oldPassword) {
        if(Objects.equals(newPassword, "")) {
            throw new MessageException("密码不能为空！", 400);
        }
        if(!newPassword.equals(reNewPassword)) {
            throw new MessageException("两次输入的密码不一致！", 400);
        }
        Encrypt encrypt=new Encrypt();
    	
        if(newPassword!=null&&newPassword!="")
    	{
    		System.out.println(newPassword);
    		encrypt.setKey(newPassword);
    		encrypt.setEncString(newPassword);
    		newPassword=encrypt.getStrMi();
    		System.out.println(encrypt.getStrMi());
    	}
        if(oldPassword!=null&&oldPassword!="")
    	{
    		System.out.println(oldPassword);
    		encrypt.setKey(oldPassword);
    		encrypt.setEncString(oldPassword);
    		oldPassword=encrypt.getStrMi();
    		System.out.println(encrypt.getStrMi());
    	}
        SysUser user = (SysUser) session.getAttribute("user");
        user.setRegion(1);
        int result = userService.changePassword(user.getId(), newPassword, oldPassword);
        return ApiResult.SUCCESS(result);
    }

    @AllowRole(1)
    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult deleteUser(@PathVariable int id) {
        int result = userService.deleteSysUser(id);
        return ApiResult.SUCCESS(result);
    }
}
