package com.atc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.atc.model.Login;
import com.atc.repository.LoginRepository;
/*
 * created 28/06/2020
 * author Adilson Arbuez
 */
@Service
public class LoginService implements UserDetailsService{
@Autowired
LoginRepository loginRepository;

public Login create(Login login)
{		
	return loginRepository.save(login);
}

public Login update(Login login)
{
	return loginRepository.save(login);
}

public Login readOne(String login)
{
	return loginRepository.findByLogin(login);
}

public List<Login> readAll()
{
	return loginRepository.findAll();
}

@Override
public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
	return readOne(login);
}
}
