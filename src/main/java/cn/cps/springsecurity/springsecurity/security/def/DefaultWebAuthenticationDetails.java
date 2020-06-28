package cn.cps.springsecurity.springsecurity.security.def;

import cn.cps.springsecurity.springsecurity.security.SecurityConstants;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Cai Peishen
 * @Date: 2020/6/12 16:36
 * @Description: 获取用户登录时携带的额外信息
 */
public class DefaultWebAuthenticationDetails extends WebAuthenticationDetails {

    private final String verifyCode;

    public DefaultWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        // verifyCode为页面中验证码的name
        verifyCode = request.getParameter(SecurityConstants.VERIFY_CODE_PARAMETER);
    }

    public String getVerifyCode() {
        return this.verifyCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("; VerifyCode: ").append(this.getVerifyCode());
        return sb.toString();
    }
}
