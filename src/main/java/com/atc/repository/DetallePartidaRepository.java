package com.atc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atc.model.DetallePartida;
import com.atc.model.Partida;
/*
 * author Adilson Arbuez
 */
@Repository
public interface DetallePartidaRepository extends JpaRepository<DetallePartida, Integer>{
	public List<DetallePartida> findByPartida(Partida partida);
	public List<DetallePartida> findByCodCuenta(String codCuenta);
	public List<DetallePartida> findByCodCuentaEqualsAndPartidaIn(String codCuenta,List<Partida> partidas);
}
