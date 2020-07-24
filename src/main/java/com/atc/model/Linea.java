package com.atc.model;

import java.time.LocalDate;

public class Linea {
//linea de libro diario
private LocalDate fecha;
private int partida;
private String codigo;
private String concepto;
private double debe;
private double haber;
//linea de mayorizacion
private double saldo;
private String enunciado;
//lineas del balance
private double deudor;
private double acreedor;

private int idDetalle;

	public Linea() {
	}
	
	public int getIdDetalle() {
		return idDetalle;
	}

	public void setIdDetalle(int idDetalle) {
		this.idDetalle = idDetalle;
	}
	

	

	public Linea(LocalDate fecha, int partida, String codigo) {
		super();
		this.fecha = fecha;
		this.partida = partida;
		this.codigo = codigo;
	}

	//linea de libro diario
	public Linea(LocalDate fecha, int partida, String codigo, String concepto, double debe, double haber,int idDetalle) {
		this.fecha = fecha;
		this.partida = partida;
		this.codigo = codigo;
		this.concepto = concepto;
		this.debe = debe;
		this.haber = haber;
		this.idDetalle=idDetalle;
	}
	
	//linea de mayorizacion
	public Linea(LocalDate fecha, int partida, String codigo,String concepto, String enunciado, double debe, double haber, double saldo) {
		this.fecha = fecha;
		this.partida = partida;
		this.codigo = codigo;
		this.debe = debe;
		this.haber = haber;
		this.saldo = saldo;
		this.enunciado = enunciado;
		this.concepto = concepto;
	}
	
	//lineas del balance
	public Linea(String codigo, String concepto, double debe, double haber, double deudor, double acreedor) {
		this.codigo = codigo;
		this.concepto = concepto;
		this.debe = debe;
		this.haber = haber;
		this.deudor = deudor;
		this.acreedor = acreedor;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public int getPartida() {
		return partida;
	}

	public void setPartida(int partida) {
		this.partida = partida;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public double getDebe() {
		return debe;
	}

	public void setDebe(double debe) {
		this.debe = debe;
	}

	public double getHaber() {
		return haber;
	}

	public void setHaber(double haber) {
		this.haber = haber;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public String getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	public double getDeudor() {
		return deudor;
	}

	public void setDeudor(double deudor) {
		this.deudor = deudor;
	}

	public double getAcreedor() {
		return acreedor;
	}

	public void setAcreedor(double acreedor) {
		this.acreedor = acreedor;
	}

}
