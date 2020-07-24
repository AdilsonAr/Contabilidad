package com.atc.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atc.model.Partida;
import com.atc.repository.PartidaRepository;
/*
 * author Adilson Arbuez
 */
@Service
public class PartidaService {
	@Autowired
	PartidaRepository partidaRepository;
	
	public Partida Save(Partida partida) {
		return partidaRepository.save(partida);
	}
	
	public void update(Partida partida) {
		partidaRepository.save(partida);
	}
	
	public List<Partida> getAll(){
		return partidaRepository.findAll();
	}
	
	public Partida getOne(int id) {
		return partidaRepository.getOne(id);
	}
	
	public List<Partida> getRank(LocalDate fecha1, LocalDate fecha2)
	{
		return partidaRepository.findByFechaAfterAndFechaBefore(fecha1, fecha2);
	}
}
