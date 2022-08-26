package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserRepository repo;
	
	
	@Autowired
	private RoleRepository roleRepo;
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<User> listAll() {
		return (List<User>) repo.findAll();
	}
	
	public List<Role> listAllRoles() {
		return (List<Role>) roleRepo.findAll();
	}

	
	public void save(User user) {
		boolean isUpdatingUser = (user.getId() != null);

		if (isUpdatingUser){
			User existingUser = repo.findById(user.getId()).get();

			if (user.getPassword().isEmpty()){
				user.setPassword(existingUser.getPassword());
			}else {
				encodePassword(user);
			}
		}else {
			encodePassword(user);
		}
		repo.save(user);
		
	}
	
	private void encodePassword(User user) {
		String encodePassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodePassword);
	}
	

	public boolean isEmailUnique(Integer id, String email) {
		User userByEmail = repo.getUserByEmail(email);

		if (userByEmail == null) return true;

		boolean isCreatingNew = (id == null);

		if (isCreatingNew){
			return false;
		}

		else {
			if(userByEmail.getId() != id) return false;

		}
		return true;
	}

	public User get(Integer id) throws UserNotFoundException {
		try {
			return repo.findById(id).get();
		}catch (NoSuchElementException ex){
			throw new UserNotFoundException("Could not find any user with ID " + id);
		}

	}

	public void delete(Integer id) throws UserNotFoundException {
		Long countById = repo.countById(id);
		if(countById == null || countById == 0){
			throw new UserNotFoundException("could not find any user with ID " + id);
		}
		repo.deleteById(id);
	}

	public void updateUserEnabledStatus(Integer id, boolean enabled){
		repo.updateEnabledStatus(id, enabled);
	}
}
