package cn.cps.springsecurity.springsecurity.security.sms;

import cn.cps.springsecurity.springsecurity.security.SecurityConstants;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Cai Peishen
 * @Date: 2020/6/24 11:28
 * @Description: 短信登录的鉴权过滤器，模仿 UsernamePasswordAuthenticationFilter 实现
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    /**
     * 是否仅 POST 方式
     */
    private boolean postOnly = true;

    public SmsCodeAuthenticationFilter() {
        // 短信登录的请求 post 方式的 /sms/login
        super(new AntPathRequestMatcher(SecurityConstants.LOGIN_URL_MOBILE, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String smsMobile = request.getParameter(SecurityConstants.SMS_MOBILE_PARAMETER);

        if (smsMobile == null) {
            smsMobile = "";
        }

        smsMobile = smsMobile.trim();

        SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(smsMobile);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }
}
