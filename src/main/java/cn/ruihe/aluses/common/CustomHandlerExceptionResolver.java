package cn.ruihe.aluses.common;


import cn.ruihe.aluses.common.message.ApiResult;
import cn.ruihe.utils.AppTool;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomHandlerExceptionResolver implements HandlerExceptionResolver {
    Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Autowired
    ApplicationContext ctx;

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.error("★发现异常★", ex);
        if (AppTool.isAjax(request)) {
            ApiResult result = new ApiResult();
            result.setResMsg(ex.getMessage());
            if(ex instanceof MessageException){
                MessageException me = (MessageException) ex;
                if(me.getStatus() != null){
                    result.setResCode(me.getStatus());
                } else {
                    result.setResCode(500);
                }
            } else {
                result.setResCode(500);
            }
            try {
                response.setStatus(result.getResCode());
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().print(JSONObject.toJSONString(result));
                response.getWriter().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ModelAndView();
        } else {
            response.setStatus(500);
            ModelAndView view = new ModelAndView("e500");
            if(ex instanceof MessageException){
                view.addObject("message", ex.getMessage());
            }
            return view;
        }
    }
}
