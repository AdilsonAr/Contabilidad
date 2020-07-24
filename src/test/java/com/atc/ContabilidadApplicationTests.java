package com.atc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.atc.model.Login;
import com.atc.model.User;
import com.atc.service.LoginService;
import com.atc.service.UserService;

@SpringBootTest
class ContabilidadApplicationTests {
	
	@Autowired
	BCryptPasswordEncoder encoder;
	@Autowired
	LoginService loginService;
	@Autowired
	UserService userService;
	@Test
	void contextLoads() {
		User user=new User("Adilson Arbuez","ognyom@gmail.com");
		userService.create(user);
		Login login=new Login("Adilson",encoder.encode("armath"),"ADMIN",user);
		Login me=loginService.create(login);
		assertEquals(me.getLogin(), login.getLogin());
		assertEquals(me.getPassword(), login.getPassword());
	}

}
