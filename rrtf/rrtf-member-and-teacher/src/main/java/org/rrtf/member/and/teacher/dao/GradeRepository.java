package org.rrtf.member.and.teacher.dao;

import org.rrtf.member.and.teacher.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long> {
	// 根据等级ID查询等级相关信息
	Grade findByGradeId(int gradeId);
}
