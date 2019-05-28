package org.rrtf.member.and.teacher.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.rrtf.member.and.teacher.dao.UserRepository;
import org.rrtf.member.and.teacher.entity.Member;
import org.rrtf.member.and.teacher.entity.Teacher;
import org.rrtf.member.and.teacher.entity.User;
import org.rrtf.member.and.teacher.entity.UserInfo;
import org.rrtf.member.and.teacher.fileupload.FileUploadService;
import org.rrtf.member.and.teacher.service.MemberService;
import org.rrtf.member.and.teacher.service.TeacherService;
import org.rrtf.member.and.teacher.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("user")
public class UserController {

	User user;// 保存当前用户的信息

	@Resource
	UserRepository userRepository;

	@Resource
	MemberService memberService;

	@Resource
	TeacherService teacherService;

	@Resource
	FileUploadService fileUploadService;

	@Resource
	UserService userService;

	// 首页
	@RequestMapping("homepage")
	public String home(HttpSession session) {
		session.setAttribute("user", user);
		return "homepage";
	}

	// 登录
	@GetMapping("login")
	public String toLogin(HttpSession session, String username, String password, Model model) {
		user = userRepository.findByUsername(username);
		user.setPicture(user.getPicture());
		session.setAttribute("user", user);
		return "redirect:homepage";
	}
	
	@PostMapping("login")
	public String login(HttpSession session, String username, String password, Model model) {
		user = userRepository.findByUsername(username);
		user.setPicture(user.getPicture());
		session.setAttribute("user", user);
		return "redirect:homepage";
	}

	// 用户中心
	@RequestMapping("user-center")
	public String userCenter(HttpSession session) throws Exception {
		session.setAttribute("userinfo", userService.getUserInfo(user));
		return "personal-profile/general-user-home-page";
	}

	// 用户个人资料修改
	@RequestMapping("modify")
	public String modify(HttpSession session) {
		UserInfo userInfo = userService.getUserInfo(user);
		System.out.println(userInfo);
		session.setAttribute("userinfo", userInfo);
		return "personal-profile/user-center-userinfo";
	}

	// 保存会员修改信息
	@PostMapping("saveMember")
	public String saveMember(Member member) {
		member.setUserId(user.getUserId());
		memberService.saveMember(member);
		return "redirect:modify";
	}

	// 保存讲师修改信息
	@PostMapping("saveTeacher")
	public String saveTeacher(Teacher teacher) {
		teacher.setUserId(user.getUserId());
		teacherService.saveTeacher(teacher);
		return "redirect:modify";
	}

	// 保存用户修改头像
	@RequestMapping("modifyPicture")
	public String modiyPicture(MultipartFile file, RedirectAttributes attributes, HttpSession session) {
		try {
			fileUploadService.uploadFile(user.getUsername(), file, attributes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		user = userRepository.findByUsername(user.getUsername());
		return "redirect:modify";
	}

	// 保存用户修改的密码
	@PostMapping("modifyPassword")
	public String modifyPassword(String password, String newpassword, Model model) {
		/*
		 * 如果用户输入的旧密码与数据库中存储的相同， 进行修改密码，否则提示用户重新输入旧密码
		 */
		if (password.equals(user.getPassword())) {
			userRepository.updatePassword(newpassword, user.getUsername());
		} else {
			model.addAttribute("message", "您输入的当前密码不正确，请重新输入!");
		}
		return "redirect:modify";
	}

	// 查看我的公开课
	@RequestMapping("myOpenClass")
	public String myOpenClass() {
		return "personal-profile/user-center-myOpenClass";
	}

	// 查看我的群组
	@RequestMapping("myGroup")
	public String myGroup() {
		return "personal-profile/user-center-myGroup";
	}

	// 查看我的福利城堡
	@RequestMapping("myWelfareCastle")
	public String myWelfareCastle() {
		return "personal-profile/user-center-myWelfareCastle";
	}

	// 查看我的托福人
	@RequestMapping("myTOEFL")
	public String myTOEFL() {
		return "personal-profile/user-center-myTOEFL";
	}

}
