package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userTan = new User("pvtan@gmail.com", "tanmach2", "tan", "pham");
		userTan.addRole(roleAdmin);
		
		User savedUser = repo.save(userTan);
		assertThat(savedUser.getId()).isGreaterThan(0);
		
	}
	@Test
	public void testCreateUserWithTwoRoles() {
		User userNam = new User("lvNam@gmail.com", "tanmach2", "Nam", "le");
		Role roleEditor = new Role(4);
		Role roleAssistant = new Role(6);
		
		userNam.addRole(roleAssistant);
		userNam.addRole(roleEditor);
		
		User savedUser = repo.save(userNam);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUser() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testListUserById() {
		User userTan = repo.findById(1).get();
		System.out.println(userTan);
		assertThat(userTan).isNotNull();
	}
	
	@Test
	public void testUpdateUser() {
		User userTan = repo.findById(1).get();
		userTan.setEnabled(false);
		userTan.setEmail("tanmach2@gmail.com");
		
		repo.save(userTan);
	}
	
	@Test
	public void testUpdateUserRole() {
		User userNam = repo.findById(5).get();
		
		Role roleEditor = new Role(4);
		Role roleSalesperson = new Role(3);
		
		userNam.getRoles().remove(roleEditor);
		userNam.addRole(roleSalesperson);
		
		repo.save(userNam);
		
	}
	
	@Test
	public void testDeleteUser() {
		Integer userId = 5;
		repo.deleteById(userId);
	}
	
	@Test
	public void testGetUserByEmail() {
		String email = "pvtan1@cmcglobal.vn";
		User user = repo.getUserByEmail(email);
		
		assertThat(user).isNotNull();
		
		
	}
}

