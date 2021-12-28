package cn.gface.face.api.interceptor;

import cn.gface.face.api.interceptor.annotation.Authorization;
import cn.gface.face.api.utils.TokenUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;

public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //如果注明了@authorization，需要进行验证,进行验证返回401错误
        if (method.getAnnotation(Authorization.class) != null) {
            //从header中得到token
            String authorization = request.getHeader("G-Token");
            //验证token
            boolean verify = TokenUtil.verifyToken(authorization);
            System.out.println("Token 验证结果: = " + verify);
            // 访问的不是下的，直接跳过
            // String name = handlerMethod.getBeanType().getPackage().getName();

            if (verify) {
                //如果token验证成功，将token对应的用户id存在request中，便于之后注入
                request.setAttribute("username", Objects.requireNonNull(TokenUtil.getToken(authorization)).get("username"));
                request.setAttribute("id", Objects.requireNonNull(TokenUtil.getToken(authorization)).get("id"));
                request.setAttribute("pid", Objects.requireNonNull(TokenUtil.getToken(authorization)).get("pid"));
                request.setAttribute("role", Objects.requireNonNull(TokenUtil.getToken(authorization)).get("role"));
                return true;
            } else {
                response.addHeader("Access-Control-Allow-Origin", "*");
                response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
                response.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
                response.addHeader("Access-Control-Max-Age", "3600");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

        }
        return true;
    }


}
