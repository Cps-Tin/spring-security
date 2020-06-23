package cn.cps.springsecurity.springsecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

/**
 * @Author: Cai Peishen
 * @Date: 2020/6/12 11:10
 * @Description:
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    //请求配置
    @Autowired
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;

    //自定义校验
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    //认证成功
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    //认证失败
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    /**
     * 注入自定义PermissionEvaluator
     */
    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler(){
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(new CustomPermissionEvaluator());
        return handler;
    }

    /**
     * 验证密码配置
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //使用自定义密码校验
        auth.authenticationProvider(customAuthenticationProvider);

        //密码校验(不加密)
//        auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder() {
//            @Override
//            public String encode(CharSequence charSequence) {
//                return charSequence.toString();
//            }
//
//            @Override
//            public boolean matches(CharSequence charSequence, String s) {
//                return s.equals(charSequence.toString());
//            }
//        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 如果有允许匿名的url，填在下面
                .antMatchers("/verifyCode","/login/invalid","/login").permitAll()
                .anyRequest().authenticated()

                // 设置登陆页
                .and().formLogin().loginPage("/login")
                // 登陆成功页
                //.defaultSuccessUrl("/").permitAll() //现在准备用代码实现，改逻辑只能存在一个实例
                .successHandler(customAuthenticationSuccessHandler)
                // 登录失败Url
                //.failureUrl("/login/error") //现在准备用代码实现，改逻辑只能存在一个实例
                .failureHandler(customAuthenticationFailureHandler)

                // 自定义登陆用户名和密码参数，默认为username和password
                // .usernameParameter("username")
                // .passwordParameter("password")

                // 指定authenticationDetailsSource
                .authenticationDetailsSource(authenticationDetailsSource)
                .and().logout().permitAll()

                //自动登录
                .and().rememberMe()
                //记住我 有效时间：单位s
                .tokenValiditySeconds(60)
                //从数据库中获取【记住我】的数据
                .tokenRepository(persistentTokenRepository())

                //用户认证处理实现类
                .userDetailsService(userDetailsService)

                //session 过期退出时 配置处理逻辑
                .and().sessionManagement()
                .invalidSessionUrl("/login/invalid");

        // 关闭CSRF跨域
        http.csrf().disable();
    }



    /**
     * 过滤拦截URL
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置拦截忽略文件夹，可以对静态资源放行
        web.ignoring().antMatchers("/css/**", "/js/**");
    }

    /**
     * 记住我
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // 如果token表不存在，使用下面语句可以初始化该表；若存在，请注释掉这条语句，否则会报错。
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }


}

