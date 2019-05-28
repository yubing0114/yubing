package org.rrtf.member.and.teacher.service;

import java.sql.Date;

import javax.annotation.Resource;

import org.rrtf.member.and.teacher.dao.TeacherRepository;
import org.rrtf.member.and.teacher.entity.Teacher;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {
	@Resource
	TeacherRepository teacherRepository;

	// 保存教师修改信息
	@Override
	public int saveTeacher(Teacher teacher) {
		int userId = teacher.getUserId();
		String realname = teacher.getRealname();
		String sex = teacher.getSex();
		String edu = teacher.getEdu();
		String email = teacher.getEmail();
		String tel = teacher.getTel();
		Date birth = teacher.getBirth();
		String introduce = teacher.getIntroduce();
		teacherRepository.modifyTeacher(realname, sex, edu, email, tel, birth, introduce, userId);
		return 1;
	}

}
