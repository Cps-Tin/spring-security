package cn.cps.springsecurity.springsecurity.security.sms;

import cn.cps.springsecurity.springsecurity.security.common.CommonAuthenticationFailureHandler;
import cn.cps.springsecurity.springsecurity.security.common.CommonAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: Cai Peishen
 * @Date: 2020/6/24 13:41
 * @Description: 短信验证码登录的配置文件：每种登录方式都建议一个专属于它的配置文件 再把这个配置文件加入到 WebSecurityConfig 中，进行解耦
 */
@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    //这里注入的验证码userDetailsService
    @Resource
    private SmsCodeUserDetailsService smsCodeUserDetailsService;

    @Autowired
    private CommonAuthenticationSuccessHandler commonAuthenticationSuccessHandler;
    @Autowired
    private CommonAuthenticationFailureHandler commonAuthenticationFailureHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
        smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        //自定义了登录配置，登录成功失败也需要重新指定
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(commonAuthenticationSuccessHandler);
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(commonAuthenticationFailureHandler);

        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
        smsCodeAuthenticationProvider.setSmsCodeUserDetailsService(smsCodeUserDetailsService);

        http.authenticationProvider(smsCodeAuthenticationProvider)
                .addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
