package cn.cps.springsecurity.springsecurity.security.def;

import cn.cps.springsecurity.springsecurity.utils.VerifyCodeUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Cai Peishen
 * @Date: 2020/6/12 16:41
 * 至此我们通过自定义WebAuthenticationDetails和AuthenticationDetailsSource将验证码和用户名、密码一起带入了Spring Security中，下面我们需要将它取出来。
 * 这里需要我们自定义AuthenticationProvider，需要注意的是，如果是我们自己实现AuthenticationProvider，那么我们就需要自己做密码校验了。
 */
@Component
public class DefaultAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private DefaultUserDetailsService defaultUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取用户输入的用户名和密码
        String inputName = authentication.getName();
        String inputPassword = authentication.getCredentials().toString();

        DefaultWebAuthenticationDetails details = (DefaultWebAuthenticationDetails) authentication.getDetails();

        String verifyCode = details.getVerifyCode();
        if(!validateVerify(verifyCode)) {
            throw new DisabledException("验证码输入错误");
        }

        // userDetails为数据库中查询到的用户信息
        UserDetails userDetails = defaultUserDetailsService.loadUserByUsername(inputName);

        // 如果是自定义AuthenticationProvider，需要手动密码校验
        if(!userDetails.getPassword().equals(inputPassword)) {
            throw new BadCredentialsException("密码错误");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, inputPassword, userDetails.getAuthorities());
    }

    private boolean validateVerify(String inputVerify) {
        if("".equals(inputVerify) || inputVerify==null){
            return false;
        }
        //获取当前线程绑定的request对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 不分区大小写
        // 这个verifyCode是在servlet中存入session的名字
        String verifyCode = ((String) request.getSession().getAttribute(VerifyCodeUtils.VERIFY_CODE_KEY));

        System.out.println("图形验证码：" + verifyCode + "用户输入：" + inputVerify);

        return inputVerify.equalsIgnoreCase(verifyCode);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 这里不要忘记，和UsernamePasswordAuthenticationToken比较
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

