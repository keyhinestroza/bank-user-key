package com.vobi.bank.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vobi.bank.domain.Customer;
import com.vobi.bank.domain.Users;
import com.vobi.bank.dto.UsersDTO;
import com.vobi.bank.mapper.UserMapper;
import com.vobi.bank.service.UsersService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	UsersService userService;
	@Autowired
	UserMapper userMapper;
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") String id) throws Exception{
		userService.deleteById(id);
	}
	
	@PutMapping
	public UsersDTO update(@Valid @RequestBody UsersDTO userDTO)throws Exception{
		Users user=userMapper.userDTOToUser(userDTO);
		user=userService.update(user);
		userDTO=userMapper.userToUserDTO(user);

		return userDTO;
	}
	
	@PostMapping
	public UsersDTO save(@Valid @RequestBody UsersDTO customerDTO)throws Exception{
		Users user=userMapper.userDTOToUser(customerDTO);
		user=userService.save(user);
		customerDTO=userMapper.userToUserDTO(user);

		return customerDTO;
	}
	
	@GetMapping("/{id}")
	public UsersDTO findById(@PathVariable("id") String id) throws Exception{
		//Customer customer=(customerService.findById(id).isPresent()==true)?customerService.findById(id).get():null;

		Users user=null;
		if(userService.findById(id).isPresent()==true)
			user=userService.findById(id).get();

		return userMapper.userToUserDTO(user);
	}

	
	@GetMapping
	public List<UsersDTO> findAll() throws Exception{
		List<Users> user= userService.findAll();
		List<UsersDTO> customerDTOs= userMapper.userListToUserDTOList(user);
		return customerDTOs;
	}
}
