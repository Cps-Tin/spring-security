package cn.cps.springsecurity.springsecurity.security.common;

import cn.cps.springsecurity.springsecurity.security.SecurityConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Cai Peishen
 * @Date: 2020/6/23 17:32
 * @Description: 退出登录
 */
@Component
public class CommonLogoutSuccessHandler implements LogoutSuccessHandler {

    Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = ((User) authentication.getPrincipal()).getUsername();
        log.info("退出成功，用户名：{}", username);

        // 重定向到登录页
        response.sendRedirect(SecurityConstants.LOGIN_URL);
    }

}
