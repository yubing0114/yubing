package org.rrtf.member.and.teacher.service;

import java.sql.Date;

import javax.annotation.Resource;

import org.rrtf.member.and.teacher.dao.MemberRepository;
import org.rrtf.member.and.teacher.entity.Member;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

	@Resource
	MemberRepository memberRepository;

	// 保存会员修改信息
	@Override
	public int saveMember(Member member) {
		int userId = member.getUserId();
		String sex = member.getSex();
		String email = member.getEmail();
		String telephone = member.getTelephone();
		Date birthday = member.getBirthday();
		String signature = member.getSignature();
		memberRepository.modifyMember(sex, email, telephone, birthday, signature, userId);
		return 1;
	}

}
