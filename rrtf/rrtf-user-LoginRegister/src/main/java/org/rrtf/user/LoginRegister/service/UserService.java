package org.rrtf.user.LoginRegister.service;

import org.rrtf.user.LoginRegister.entity.User;

public interface UserService {
	
	User findService(String username);
	
	boolean registerData(String username, String password);
}
