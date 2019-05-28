package org.rrtf.user.LoginRegister.service;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.rrtf.user.LoginRegister.dao.UserRepository;
import org.rrtf.user.LoginRegister.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userInfoRepository;

	@Override
	public User findService(String username) {
		User user = userInfoRepository.findByUsername(username);
		System.out.println("bing:" + user);
		return user;
	}

	@Override
	public boolean registerData(String username, String password) {
		// 看数据库中是否存在该账户
		User userInfo = userInfoRepository.findByUsername(username);
		if (userInfo != null) {
			return false;
		}
		String salt = new SecureRandomNumberGenerator().nextBytes().toHex(); // 生成盐值
		String newPassword = new Md5Hash(password, salt, 2).toString(); // 生成的密文
		User user = new User();
		user.setSalt(salt);
		user.setUsername(username);
		user.setPassword(newPassword);
		user.setName("vip");
		Date date = new Date();
		user.setRegtime(new Timestamp(1));
		userInfoRepository.save(user);
		System.out.println("用户注册成功");
		return true;
	}

}
