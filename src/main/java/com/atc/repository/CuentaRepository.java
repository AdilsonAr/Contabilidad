package com.atc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atc.model.Cuenta;
/*
 * author Adilson Arbuez
 */
@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Integer>{
	public Cuenta findByCodigoCuenta(int codigoCuenta);
	public List<Cuenta> findByCodigoCuentaStartingWith(String prefijo);
}
