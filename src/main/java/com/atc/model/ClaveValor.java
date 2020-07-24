package com.atc.model;
/*
 * created 28/06/2020
 * author Adilson Arbuez
 */
public class ClaveValor {
	private String clave;
	private double valor;
	
	public ClaveValor(String clave, double valor) {
		this.clave = clave;
		this.valor = valor;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public ClaveValor() {
	}
	
	
}
