package com.atc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atc.model.Cuenta;
import com.atc.repository.CuentaRepository;
/*
 * author Adilson Arbuez
 */
@Service
public class CuentaService{
	List<Cuenta> listaCuentas=new ArrayList<Cuenta>();
	@Autowired
	CuentaRepository cuentaRepository;
	
	public void save(Cuenta cuenta) {
		cuentaRepository.save(cuenta);
	}
	
	public void update(Cuenta cuenta){
		cuentaRepository.save(cuenta);
	}
	
	public Cuenta getOne(int codigo) {
		return cuentaRepository.getOne(codigo);
	}
	
	public List<Cuenta> readByFamilia(String prefijo){
		return cuentaRepository.findByCodigoCuentaStartingWith(prefijo);
	}
	
	public List<Cuenta> getAll() {
		return cuentaRepository.findAll();
	}
	
	public void delete(Cuenta cuenta) {
		cuentaRepository.delete(cuenta);
	}
}
