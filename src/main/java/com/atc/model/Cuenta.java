package com.atc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/*
 * author Adilson Arbuez
 */
@Entity
@Table(name="Cuenta")
public class Cuenta {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name="codCuenta")
	private String codigoCuenta;
	@Column(name="descripcion")
	private String descripcion;
	@Column(name="saldo")
	private String saldo;
	@Column(name="tipo")
	private String tipo;
	@Column(name="nivel")
	private int nivel;
	
	public Cuenta(String codigoCuenta, String descripcion, String saldo, String tipo, int nivel) {
		this.codigoCuenta = codigoCuenta;
		this.descripcion = descripcion;
		this.saldo = saldo;
		this.tipo = tipo;
		this.nivel = nivel;
	}
	
	public Cuenta(String descripcion, String saldo, String tipo, int nivel) {
		this.descripcion = descripcion;
		this.saldo = saldo;
		this.tipo = tipo;
		this.nivel = nivel;
	}
	
	public Cuenta() {
		
	}

	public String getCodigoCuenta() {
		return codigoCuenta;
	}

	public void setCodigoCuenta(String codigoCuenta) {
		this.codigoCuenta = codigoCuenta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getSaldo() {
		return saldo;
	}

	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	
}
