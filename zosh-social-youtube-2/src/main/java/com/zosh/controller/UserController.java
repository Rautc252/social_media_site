package com.zosh.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zosh.Repository.UserRepository;
import com.zosh.models.User;

@RestController
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/users")
	public User createUser(@RequestBody User user) {
		User newUser= new User();
		newUser.setEmail(user.getEmail());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setId(user.getId());
		newUser.setPassword(user.getPassword());
		
		User savedUser = userRepository.save(newUser);
		
		return savedUser;
	}
	
	
	
	@GetMapping("/users")
	public List<User> getUsers() {
		
	List<User> users=userRepository.findAll();
	
	
	
	return users;
	}
	
	@GetMapping("/users/{userid}")
	public User getUsersById(@PathVariable("userid")Integer id) throws Exception {
		
	 Optional<User> user= userRepository.findById(id);
	
	if(user.isPresent()) {
		return user.get();
	}
	
	throw new Exception("user not found with this id "+id);

	
	
	}
	
	
	@PutMapping("/users/{userid}")
	public User updateUser(@RequestBody User user,@PathVariable Integer userid) throws Exception{
		
		Optional<User> user1=userRepository.findById(userid);
		
		
		if(user1.isEmpty()) {
			throw new Exception("not found");
		}
		
		User Olduser=user1.get();
		
		if(user.getFirstName()!=null) {
			Olduser.setFirstName(user.getFirstName());
		}
		if(user.getLastName()!=null) {
			Olduser.setLastName(user.getLastName());
		}
		if(user.getEmail()!=null) {
			Olduser.setEmail(user.getEmail());
		}
		if(user.getPassword()!=null) {
			Olduser.setPassword(user.getPassword());
		}
		
		User updateuser =userRepository.save(Olduser);
		
		return updateuser ;
	}
	
	
	@DeleteMapping("users/{userId}")
	public String deleteUser(@PathVariable("userId") Integer userId) throws Exception{
		
		Optional<User> user = userRepository.findById(userId);
		if(user.isEmpty()) {
			throw new Exception("the data is not found on this id "+userId);
	   
		}
		 userRepository.deleteById(userId);
			return "user deleted successfully"+userId ;
		
	}
}
