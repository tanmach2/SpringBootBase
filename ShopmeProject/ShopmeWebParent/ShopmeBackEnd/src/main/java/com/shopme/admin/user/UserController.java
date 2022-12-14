package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class UserController {
	@Autowired
	private UserService service;
	
	@GetMapping("/users")
	public String listAll(Model model) {
		List<User> listUser = service.listAll();
		model.addAttribute("listUser", listUser);
		return "users";
	}
	
	
	@GetMapping("/users/new")
	public String newUser(Model model) {
		List<Role> listRoles = service.listAllRoles();
		model.addAttribute("listRole", listRoles);// "listRole" truyen ra giao dien
		model.addAttribute("pageTitle", "Create new user");
		User user = new User();
		user.setEnabled(true);
		model.addAttribute("user", user);
		
		
		return "user_form";
		
	}
	
	@PostMapping("/users/save")
		public String saveUser(User user, RedirectAttributes redirectAttributes) {
			System.out.println(user);
			service.save(user);
			redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");
			return "redirect:/users";
	}

	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes){

		try {
			List<Role> listRoles = service.listAllRoles();
			User user = service.get(id);

			model.addAttribute("user", user);
			model.addAttribute("pageTitle", "Edit user (Id: " + id + ")");
			model.addAttribute("listRole", listRoles);

			return "user_form";
		}catch (UserNotFoundException ex){
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/users";
		}

	}

	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes){
		try {
			service.delete(id);
			redirectAttributes.addFlashAttribute("message", "The user ID " + id + "has been deleted successfully.");

//			return "user_form";
		}catch (UserNotFoundException ex){
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}
		return "redirect:/users";
	}

	@GetMapping("/users/{id}/enabled/{status}")
	public String updateUserEnabledStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes){
		service.updateUserEnabledStatus(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message = "The User ID " + id + "has been" + status;
		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/users";
	}



}
