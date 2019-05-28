package org.rrtf.user.LoginRegister.ShiroConfig;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.rrtf.user.LoginRegister.entity.User;

public class UserFormAuthenticationFilter extends FormAuthenticationFilter {

	// 将登录用户保存到session中
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		// 获取已登陆的用户信息
		User user = (User) subject.getPrincipal();
		// 获取session
		HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
		HttpSession session = httpServletRequest.getSession();
		// 把用户信息保存到session
		session.setAttribute("user", user);
		return super.onLoginSuccess(token, subject, request, response);
	}

	// 校验验证码
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		// 从session获取正确的验证码
		HttpSession session = ((HttpServletRequest) request).getSession();
		// 页面输入的验证码
		String randomcode = request.getParameter("userCode");
		// 从session中取出验证码
		String validateCode = (String) session.getAttribute("code");
		if (randomcode != null && validateCode != null) {
			if (!randomcode.equals(validateCode)) {
				// kaptchaValidateFailed表示验证码错误
				request.setAttribute("shiroLoginFailure", "kaptchaValidateFailed");
				// 拒绝访问，不再校验账号和密码
				return true;
			}
		}
		return super.onAccessDenied(request, response, mappedValue);
	}
}
