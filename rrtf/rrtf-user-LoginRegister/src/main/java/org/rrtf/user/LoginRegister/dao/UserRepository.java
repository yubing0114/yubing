package org.rrtf.user.LoginRegister.dao;



import org.rrtf.user.LoginRegister.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

	public User findByUsername(String username);

}
