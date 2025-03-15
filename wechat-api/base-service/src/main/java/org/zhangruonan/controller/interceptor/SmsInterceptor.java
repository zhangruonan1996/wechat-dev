package org.zhangruonan.controller.interceptor;

import org.zhangruonan.base.BaseInfoProperties;
import org.zhangruonan.exceptions.GraceException;
import org.zhangruonan.grace.result.ResponseStatusEnum;
import org.zhangruonan.utils.IPUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-15 18:56:07
 */
@Slf4j
public class SmsInterceptor extends BaseInfoProperties implements HandlerInterceptor {

    /**
     * 拦截请求，在Controller调用方法之前
     *
     * @param request
     * @param response
     * @param handler
     * @throws Exception
     * @return
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-15 18:56:45
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获得用户的IP地址
        String userIP = IPUtil.getRequestIp(request);
        boolean isExist = redis.keyIsExist(MOBILE_SMSCODE + ":" + userIP);
        if (isExist) {
            log.error("短信发送频率太高");
            GraceException.display(ResponseStatusEnum.SMS_NEED_WAIT_ERROR);
            return false;
        }
        /**
         * false：请求被拦截
         * true：请求放行，正常通过，验证通过
         */
        return true;
    }

    /**
     * 请求Controller之后，渲染视图之后
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-15 18:57:12
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 请求Controller之后，渲染视图之后
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-15 18:57:27
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
