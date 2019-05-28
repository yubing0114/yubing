package org.rrtf.member.and.teacher.dao;

import javax.transaction.Transactional;

import org.rrtf.member.and.teacher.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
	// 通过用户名查询用户
	User findByUsername(String username);

	// 保存用户头像方法
	@Transactional
	@Modifying
	@Query("update User set picture = ?1 where username = ?2")
	int savePicture(String picture, String username);

	// 更新用户密码方法
	@Transactional
	@Modifying
	@Query("update User set password = ?1 where username = ?2")
	int updatePassword(String password, String username);
}
