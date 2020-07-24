package com.atc.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
/*
 * author Adilson Arbuez
 */
@Entity
@Table(name="Login")
public class Login implements UserDetails{
/**
	 * 
	 */
	private static final long serialVersionUID = 7731573063040330606L;

@Id
@Column(name="id")
@GeneratedValue(strategy=GenerationType.IDENTITY)
int id;

//identificador del usuario, sustituye el nombre
@Column(name="login")
String login;
@Column(name="password")
String password;

@Column(name="rol")
String rol;

@OneToOne(fetch=FetchType.LAZY,optional=false)
@JoinColumn(name="idUser", nullable=false)
User user;

public Login() {
	super();
	// TODO Auto-generated constructor stub
}

public Login(String login, String password, String rol, User user) {
	super();
	this.login = login;
	this.password = password;
	this.rol = rol;
	this.user = user;
}

public String getLogin() {
	return login;
}

public void setLogin(String login) {
	this.login = login;
}

public String getRol() {
	return rol;
}

public void setRol(String rol) {
	this.rol = rol;
}

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

public void setPassword(String password) {
	this.password = password;
}

@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
	List<GrantedAuthority> lista=new ArrayList<GrantedAuthority>();
	lista.add(new SimpleGrantedAuthority(rol));
	return lista;
}

@Override
public String getPassword() {
	return password;
}

@Override
public String getUsername() {
	// TODO Auto-generated method stub
	return login;
}

@Override
public boolean isAccountNonExpired() {
	// TODO Auto-generated method stub
	return true;
}

@Override
public boolean isAccountNonLocked() {
	// TODO Auto-generated method stub
	return true;
}

@Override
public boolean isCredentialsNonExpired() {
	// TODO Auto-generated method stub
	return true;
}

@Override
public boolean isEnabled() {
	// TODO Auto-generated method stub
	return true;
}
}
