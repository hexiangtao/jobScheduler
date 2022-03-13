package com.ykn.jobscheduler.admin.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

/**
 * @author hexiangtao
 * @date 2022/3/12 14:06
 */
public class ConsoleAuthInterceptor implements HandlerInterceptor {

    private String username;
    private String password;

    public ConsoleAuthInterceptor(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String auth = request.getHeader("Authorization");
        if (auth == null || auth.length() <= 6) {
            toAuth(request, response);
            return false;
        }
        auth = auth.substring(6);
        auth = new String(Base64.getDecoder().decode(auth));
        String authAccount = username + ":" + password;
        boolean success = auth.equals(authAccount);
        if (!success) {
            toAuth(request, response);
            return false;
        }
        return true;
    }

    private void toAuth(HttpServletRequest request, HttpServletResponse response) {
        String serverName = request.getServerName();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("Cache-Control", "no-store");
        response.setDateHeader("Expires", 0);
        response.setHeader("WWW-authenticate", "Basic Realm=\"" + serverName + "\"");
    }


}
