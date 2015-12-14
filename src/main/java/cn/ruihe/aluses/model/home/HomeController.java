package cn.ruihe.aluses.model.home;

import cn.ruihe.aluses.common.MessageException;
import cn.ruihe.aluses.common.message.ApiResult;
import cn.ruihe.aluses.entity.Parameter;
import cn.ruihe.aluses.entity.SysUser;
import cn.ruihe.aluses.model.home.interceptor.AllowRole;
import cn.ruihe.aluses.model.home.service.ParameterService;
import cn.ruihe.aluses.model.home.service.UserService;
import cn.ruihe.utils.Encrypt;

import org.jboss.logging.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("")
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    ParameterService parameterService;

    @RequestMapping("")
    public String index(){
        return "home/index";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult<SysUser> login(HttpSession session, @Param String account, @Param String password) {
    	Encrypt encrypt=new Encrypt();
    	if(password!=null&&password!="")
    	{
    		System.out.println(password);
    		encrypt.setKey(password);
    		encrypt.setEncString(password);
    		password=encrypt.getStrMi();
    		System.out.println(encrypt.getStrMi());
    	}
        SysUser user = userService.getUserByLogin(account, password);
        if(user != null) {
            session.setAttribute("user", user);
            return ApiResult.SUCCESS(user);
        } else {
            throw new MessageException("用户名或密码错误", 401);
        }
    }

    @RequestMapping("logout")
    public void logout(HttpSession session, HttpServletResponse response) {
        session.removeAttribute("user");
        try {
            response.sendRedirect("/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AllowRole({1,4})
    @RequestMapping(value = "repwd", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult changeSetting(HttpSession session, @RequestParam String opwd, @RequestParam String npwd, @RequestParam String rnpwd) {
        if(StringUtils.isEmpty(npwd)) {
            throw new MessageException("新密码不能为空！", 400);
        }
        if(!Objects.equals(npwd, rnpwd)) {
            throw new MessageException("两次输入的密码不一致！", 400);
        }
        Encrypt encrypt=new Encrypt();
    	
        if(npwd!=null&&npwd!="")
    	{
    		System.out.println(npwd);
    		encrypt.setKey(npwd);
    		encrypt.setEncString(npwd);
    		npwd=encrypt.getStrMi();
    		System.out.println(encrypt.getStrMi());
    	}
        if(opwd!=null&&opwd!="")
    	{
    		System.out.println(opwd);
    		encrypt.setKey(opwd);
    		encrypt.setEncString(opwd);
    		opwd=encrypt.getStrMi();
    		System.out.println(encrypt.getStrMi());
    	}
        SysUser user = (SysUser) session.getAttribute("user");
        int result = userService.changePassword(user.getId(), npwd, opwd);
        return ApiResult.SUCCESS(result);
    }

    @AllowRole({1,4})
    @RequestMapping("top")
    public String top(){
        return "home/top";
    }

    @AllowRole(1)
    @RequestMapping(value = "setting", method = RequestMethod.GET)
    public String setting(Model model){
        List<Parameter> parameters = parameterService.findParmeters();
        model.addAttribute("pms", parameters);
        return "admin/setting";
    }

    @AllowRole(1)
    @RequestMapping(value = "setting/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult changeSetting(@PathVariable int id, @RequestParam int value) {
        int result = parameterService.changeParameterValue(id, value);
        return ApiResult.SUCCESS(result);
    }
}
