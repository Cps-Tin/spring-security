package cn.cps.springsecurity.springsecurity.web;

import cn.cps.springsecurity.springsecurity.utils.VerifyCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: Cai Peishen
 * @Date: 2020/6/12 11:07
 * @Description:
 */
@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private SessionRegistry sessionRegistry;

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


    @ResponseBody
    @RequestMapping("/admin/r")
    @PreAuthorize("hasPermission('/admin','r')")
    public String printAdminR() {
        return "如果你看见这句话，说明你访问/admin路径具有r权限";
    }


    @ResponseBody
    @RequestMapping("/admin/c")
    @PreAuthorize("hasPermission('/admin','c')")
    public String printAdminC() {
        return "如果你看见这句话，说明你访问/admin路径具有c权限";
    }


    @ResponseBody
    @RequestMapping("/login/invalid")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String invalid() {
        return "Session 已过期，请重新登录";
    }

    /**
     * 获取登录信息
     * @return
     */
    @ResponseBody
    @RequestMapping("/me1")
    public Object me1(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取登录信息
     * @return
     */
    @ResponseBody
    @RequestMapping("/me2")
    public Object me2(Authentication authentication){
        return authentication;
    }

    /**
     * 获取登录信息
     * @return
     */
    @GetMapping("/me3")
    @ResponseBody
    public Object me3(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
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
     * 踢出用户
     * @param username
     * @return
     */
    @GetMapping("/kick")
    @ResponseBody
    public String removeUserSessionByUsername(@RequestParam String username) {
        int count = 0;

        // 获取session中所有的用户信息
        List<Object> users = sessionRegistry.getAllPrincipals();
        for (Object principal : users) {
            if (principal instanceof User) {
                String principalName = ((User)principal).getUsername();
                if (principalName.equals(username)) {
                    // 参数二：是否包含过期的Session
                    List<SessionInformation> sessionsInfo = sessionRegistry.getAllSessions(principal, false);
                    if (null != sessionsInfo && sessionsInfo.size() > 0) {
                        for (SessionInformation sessionInformation : sessionsInfo) {
                            //使session过期
                            sessionInformation.expireNow();
                            count++;
                        }
                    }
                }
            }
        }

        return "操作成功，清理session共" + count + "个";
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

    /**
     * 现在已经使用实现类 实现该方法了
     * 处理登录失败异常信息
     * @param request
     * @param response
     */
//    @RequestMapping("/login/error")
//    public void loginError(HttpServletRequest request, HttpServletResponse response) {
//        response.setContentType("text/html;charset=utf-8");
//        AuthenticationException exception =
//                (AuthenticationException)request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
//        try {
//            response.getWriter().write(exception.toString());
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
