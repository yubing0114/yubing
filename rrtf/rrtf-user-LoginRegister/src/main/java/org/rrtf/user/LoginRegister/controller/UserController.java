package org.rrtf.user.LoginRegister.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.rrtf.user.LoginRegister.dao.UserRepository;
import org.rrtf.user.LoginRegister.entity.User;
import org.rrtf.user.LoginRegister.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.util.StringUtils;

@Controller
public class UserController {

	@Resource
	UserRepository userRepository;

	@Autowired
	UserService userInfoService;

	@Autowired
	UserRepository userInfoRepository;

	// 首页
	@RequestMapping({ "/", "/index" })
	public String index(HttpSession session) {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		session.setAttribute("user", user);
		return "homepage";
	}

	// 404页面
	@RequestMapping("404")
	public String unAuth() {
		return "404";
	}

	// 登录页
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String toLogin() {
		return "login-register/login";
	}

	// 登录
	@RequestMapping("/login")
	public String login(HttpServletRequest request, Model model) {
		// 获取用户登录时的错误
		String exeption = (String) request.getAttribute("shiroLoginFailure");
		String msg = "";
		if (exeption != null) {
			if (UnknownAccountException.class.getName().equals(exeption)) {
				msg = "提示：账号不存在";
			} else if (IncorrectCredentialsException.class.getName().equals(exeption)) {
				msg = "提示：密码错误";
			} else if ("kaptchaValidateFailed".equals(exeption)) {
				msg = "提示：验证码输入错误";
			} else {
				msg = "提示：用户名或密码错误：";
			}
			model.addAttribute("loginErrorMsg", msg);
		}
		return "login-register/login";
	}

	// 注册页
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String toRegister() {
		return "login-register/register";
	}

	// 注册
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String saveUser(HttpServletRequest request, String username, String password, String userCode, Model model) {
		// 获取注册页面的验证码
		String code = (String) request.getSession().getAttribute("code");
		// 当验证码输入正确时，再进行判断是否能注册
		if (StringUtils.equalsIgnoreCase(code, userCode)) {
			boolean flag = userInfoService.registerData(username, password);
			if (flag) {// 如果数据库中没有该用户名，进行用户注册
				model.addAttribute("success", "恭喜您注册成功，请登录后到用户中心完善个人信息");
			} else {// 否则提示重新输入用户名
				model.addAttribute("password", password);
				model.addAttribute("tip", "提示：您输入的用户名已被注册，请重新输入");
			}
		} else {// 否则提示用户验证码输入错误
			model.addAttribute("tipCode", "提示：验证码输入错误");
		}
		return "login-register/register";
	}
}
