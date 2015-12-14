package cn.ruihe.aluses.model.home.interceptor;

import java.lang.annotation.*;

/**
 * @Author DHC
 * @Date 2015-07-18 17:48:25
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AllowRole {
    int[] value();
}