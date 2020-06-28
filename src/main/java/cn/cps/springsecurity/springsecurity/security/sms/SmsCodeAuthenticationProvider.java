package cn.cps.springsecurity.springsecurity.security.sms;

import cn.cps.springsecurity.springsecurity.security.SecurityConstants;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: Cai Peishen
 * @Date: 2020/6/24 13:35
 * @Description: 短信登陆鉴权 Provider，要求实现 AuthenticationProvider 接口
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private SmsCodeUserDetailsService smsCodeUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;

        String mobile = (String) authenticationToken.getPrincipal();

        checkSmsCode(mobile);

        UserDetails userDetails = smsCodeUserDetailsService.loadUserByUsername(mobile);

        // 此时鉴权成功后，应当重新 new 一个拥有鉴权的 authenticationResult 返回
        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(userDetails, userDetails.getAuthorities());

        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    private void checkSmsCode(String smsMobile) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String inputCode = request.getParameter(SecurityConstants.SMS_CODE_PARAMETER);

        //map在实战中会被Redis代替 -> 所以这里参数并没有使用常量替换
        Map<String, Object> map = (Map<String, Object>) request.getSession().getAttribute("smsCodeSession");
        if(map == null) {
            throw new BadCredentialsException("未检测到申请验证码");
        }

        String applyMobile = (String) map.get("mobile");

        int code = (int) map.get("code");

        if(!applyMobile.equals(smsMobile)) {
            throw new BadCredentialsException("申请的手机号码与登录手机号码不一致");
        }
        if(code != Integer.parseInt(inputCode)) {
            throw new BadCredentialsException("验证码错误");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 判断 authentication 是不是 SmsCodeAuthenticationToken 的子类或子接口
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public SmsCodeUserDetailsService getSmsCodeUserDetailsService() {
        return smsCodeUserDetailsService;
    }

    public void setSmsCodeUserDetailsService(SmsCodeUserDetailsService smsCodeUserDetailsService) {
        this.smsCodeUserDetailsService = smsCodeUserDetailsService;
    }
}
