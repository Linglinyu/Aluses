package cn.ruihe.aluses.model.home.interceptor;

import cn.ruihe.aluses.entity.SysUser;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author DHC
 * @Date 2015-07-18 17:48:25
 */
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if(o.getClass().isAssignableFrom(HandlerMethod.class)) {
            AllowRole allowRole = ((HandlerMethod) o).getMethodAnnotation(AllowRole.class);

            if (allowRole != null && allowRole.value().length > 0) {
                Object u = request.getSession().getAttribute("user");
                if (u != null) {
                    SysUser user = (SysUser) u;
                    for (int i : allowRole.value()) {
                        if (i == user.getRole()) {
                            return true;
                        }
                    }
                }
                response.sendRedirect("/");
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
