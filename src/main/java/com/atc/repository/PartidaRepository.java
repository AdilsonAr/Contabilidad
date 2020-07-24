package com.atc.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atc.model.Partida;
/*
 * author Adilson Arbuez
 */
@Repository
public interface PartidaRepository extends JpaRepository<Partida, Integer>{
public List<Partida> findByFechaAfterAndFechaBefore(LocalDate fecha1, LocalDate fecha2);
}
