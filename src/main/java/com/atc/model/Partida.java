package com.atc.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
/*
 * author Adilson Arbuez
 */
@Entity
@Table(name="Partida")
public class Partida {
	
	public Partida(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	public Partida(LocalDate fecha, String enunciado) {
		this.fecha = fecha;
		this.enunciado = enunciado;
	}
	
	public Partida(LocalDate fecha, String enunciado, List<DetallePartida> detallePartida) {
		this.fecha = fecha;
		this.enunciado = enunciado;
		this.detallePartida = detallePartida;
	}

	public Partida() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	public String getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idPartida")
	private int id;
	@Column(name="fecha")
	private LocalDate fecha; 
	private String enunciado;
	
	@OneToMany(mappedBy="partida", cascade=CascadeType.ALL)
	private List<DetallePartida> detallePartida;

	public List<DetallePartida> getDetallePartida() {
		return detallePartida;
	}

	public void setDetallePartida(List<DetallePartida> detallePartida) {
		this.detallePartida = detallePartida;
	}
	
}
