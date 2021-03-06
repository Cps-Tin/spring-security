package cn.cps.springsecurity.springsecurity.security.def;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Cai Peishen
 * @Date: 2020/6/12 16:38
 * @Description: 该接口用于在Spring Security登录过程中对用户的登录信息的详细信息进行填充
 */
@Component("authenticationDetailsSource")
public class DefaultAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {
    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest request) {
        return new DefaultWebAuthenticationDetails(request);
    }
}
