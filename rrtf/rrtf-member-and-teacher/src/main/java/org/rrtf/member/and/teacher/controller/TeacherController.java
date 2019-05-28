package org.rrtf.member.and.teacher.controller;

import javax.annotation.Resource;

import org.rrtf.member.and.teacher.dao.TeacherRepository;
import org.rrtf.member.and.teacher.entity.Teacher;
import org.rrtf.member.and.teacher.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TeacherController {
	@Resource
	TeacherRepository teacherRepository;

	@Resource
	TeacherService teacherService;

	@RequestMapping("saveTeacher")
	public String saveTeacher(Teacher teacher) {
		teacherService.saveTeacher(teacher);
		return "redirect:/member/userinfo";
	}
}
