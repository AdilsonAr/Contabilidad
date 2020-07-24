package com.atc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atc.model.User;
import com.atc.repository.UserRepository;
/*
 * author Adilson Arbuez
 */
@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	
	public User create(User user)
	{		
		return userRepository.save(user);
	}
	
	public User update(User user)
	{
		return userRepository.save(user);
	}
	
	public User read(int id)
	{
		return userRepository.getOne(id);
	}
	
	public List<User> all()
	{
		return userRepository.findAll();
	}
}
