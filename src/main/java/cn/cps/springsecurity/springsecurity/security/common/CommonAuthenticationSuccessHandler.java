package cn.cps.springsecurity.springsecurity.security.common;

import cn.cps.springsecurity.springsecurity.security.SecurityConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Cai Peishen
 * @Date: 2020/6/23 16:23
 * @Description: 用来处理认证成功后逻辑
 */
@Component
public class CommonAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("登录成功,{}", authentication);
        response.sendRedirect(SecurityConstants.LOGIN_SUCCESS_URL);
    }

}
