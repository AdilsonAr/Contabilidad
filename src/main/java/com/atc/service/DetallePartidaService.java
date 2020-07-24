package com.atc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atc.model.DetallePartida;
import com.atc.model.Partida;
import com.atc.repository.DetallePartidaRepository;
/*
 * author Adilson Arbuez
 */
@Service
public class DetallePartidaService {
	@Autowired
	DetallePartidaRepository detallePartidaRepository;
	
	public void save(DetallePartida detallePartida) {
		detallePartidaRepository.save(detallePartida);
	}
	
	public List<DetallePartida> ReadByPartidaCuenta(String codCuenta,List<Partida> partidas){
		return detallePartidaRepository.findByCodCuentaEqualsAndPartidaIn(codCuenta, partidas);
	}
	
	public void update(DetallePartida detallePartida) {
		detallePartidaRepository.save(detallePartida);
	}
	
	public void delete(DetallePartida detallePartida) {
		detallePartidaRepository.delete(detallePartida);
	}
	
	public DetallePartida getDetalle(int id)
	{
		return detallePartidaRepository.getOne(id);
	}
	
	public List<DetallePartida> readByPartida(Partida partida){
		return detallePartidaRepository.findByPartida(partida);
	}
	
	public List<DetallePartida> getDetallesByCod(String codCuenta){
		return detallePartidaRepository.findByCodCuenta(codCuenta);
	}
	
	public void createList(List<DetallePartida> lista) {
		detallePartidaRepository.saveAll(lista);
	}
}
