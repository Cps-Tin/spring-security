package cn.cps.springsecurity.springsecurity.security;


/**
 * @Author: Cai Peishen
 * @Date: 2020/6/12 11:10
 * @Description: 相关常量
 */
public class SecurityConstants {

    //当没有权限时，被引导跳转的 Url
    public static final String LOGIN_URL = "/login";

    //退出登录的 Url
    public static final String LOGOUT_URL = "/logout";

    //登陆成功后，被引导跳转的 Url
    public static final String LOGIN_SUCCESS_URL = "/home";

    //Session 过期被引导跳转的 Url
    public static final String INVALID_SESSION_URL = "/login/invalid";

    //用户名密码登录请求处理url
    public static final String LOGIN_URL_FORM = "/form/login";

    //手机验证码登录请求处理url
    public static final String LOGIN_URL_MOBILE = "/sms/login";


    /*
     * 图形验证登录
     */

    //验证码登陆表单字段名
    public static final String VERIFY_CODE_PARAMETER = "verifyCode";

    //获取图形验证码Url 包括图形验证码图片
    public static final String VERIFY_CODE_URL = "/getVerifyCode";


    /*
     * 手机验证码登录
     */

    //手机验证码登录手机号表单字段名
    public static final String SMS_MOBILE_PARAMETER = "smsMobile";

    //手机验证码登录手机验证码表单字段名
    public static final String SMS_CODE_PARAMETER = "smsCode";

    //获取短信验证码Url 包括短信验证码接口等等...
    public static final String SMS_CODE_URL = "/getSmsCode";



}
