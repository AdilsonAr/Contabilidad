package com.atc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/*
 * author Adilson Arbuez
 */
@Entity
@Table(name="DetallePartida")
public class DetallePartida {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="codCuenta")
	private String codCuenta;
	@Column(name="debe")
	private double debe;
	@Column(name="haber")
	private double haber;
	@ManyToOne(fetch = FetchType.LAZY, optional=false)
	@JoinColumn(name="idPartida",nullable=false)
	private Partida partida;
	
	public DetallePartida(int id, String codCuenta, double debe, double haber) {
		this.id = id;
		this.codCuenta = codCuenta;
		this.debe = debe;
		this.haber = haber;
	}

	public DetallePartida(String codCuenta, double debe, double haber) {
		this.codCuenta = codCuenta;
		this.debe = debe;
		this.haber = haber;
	}

	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}

	public DetallePartida() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodCuenta() {
		return codCuenta;
	}

	public void setCodCuenta(String codCuenta) {
		this.codCuenta = codCuenta;
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
	
}
