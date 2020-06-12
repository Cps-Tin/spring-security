package cn.cps.springsecurity.springsecurity.web;

import cn.cps.springsecurity.springsecurity.utils.VerifyCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Cai Peishen
 * @Date: 2020/6/12 11:07
 * @Description:
 */
@Controller
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/")
    public String showHome() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("当前登陆用户：" + name);

        return "home.html";
    }

    @RequestMapping("/login")
    public String showLogin() {
        return "login.html";
    }

    @RequestMapping("/admin")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String printAdmin() {
        return "如果你看见这句话，说明你有ROLE_ADMIN角色";
    }


    @RequestMapping("/user")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_USER')")
    public String printUser() {
        return "如果你看见这句话，说明你有ROLE_USER角色";
    }


    /**
     * 处理登录失败异常信息
     * @param request
     * @param response
     */
    @RequestMapping("/login/error")
    public void loginError(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        AuthenticationException exception =
                (AuthenticationException)request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        try {
            response.getWriter().write(exception.toString());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 区分是密码登录还是记住我
     * @return
     */
    @RequestMapping("/isRemembermeUser")
    public boolean isRemembermeUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null){
            return false;
        }
        //判断当前用户是否是通过rememberme登录，是返回true,否返回false
        return RememberMeAuthenticationToken.class.isAssignableFrom(authentication.getClass());
    }

    /**
     * 生成前端的图片验证码
     */
    @RequestMapping(value = "/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int width = 120;//宽
        int height = 40;//高
        int verifySize = 4;//验证码个数

        //生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(verifySize);
        //存入会话session
        request.getSession().setAttribute(VerifyCodeUtils.VERIFY_CODE, verifyCode.toLowerCase());
        //生成图片
        VerifyCodeUtils.outputImage(width, height, response.getOutputStream(), verifyCode);
    }

}
