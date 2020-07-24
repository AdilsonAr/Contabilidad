package com.atc.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
/*
 * author Adilson Arbuez
 */
@Entity
@Table(name="User")
public class User {
@Id
@Column(name="idUser")
@GeneratedValue(strategy=GenerationType.IDENTITY)
int id;
@Column(name="nombre")
String nombre;
@Column(name="correo")
String correo;

@OneToOne(mappedBy="user", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
private Login login;

public User(String nombre, String correo) {
	super();
	this.nombre = nombre;
	this.correo = correo;
}

public User() {
	super();
	// TODO Auto-generated constructor stub
}

public String getNombre() {
	return nombre;
}

public void setNombre(String nombre) {
	this.nombre = nombre;
}

public String getCorreo() {
	return correo;
}

public void setCorreo(String correo) {
	this.correo = correo;
}

public Login getLogin() {
	return login;
}

public void setLogin(Login login) {
	this.login = login;
}

}
