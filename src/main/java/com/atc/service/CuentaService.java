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
	private boolean loaded=false;
	List<Cuenta> listaCuentas=new ArrayList<Cuenta>();
	@Autowired
	CuentaRepository cuentaRepository;
	
	public void save(Cuenta cuenta) {
		cuentaRepository.save(cuenta);
		load();
	}
	
	public void update(Cuenta cuenta){
		cuentaRepository.save(cuenta);
		load();
	}
	
	public Cuenta getOne(int codigo) {
		return cuentaRepository.getOne(codigo);
	}
	
	private void load() {
		listaCuentas=cuentaRepository.findAll();
	}
	
	public List<Cuenta> readByFamilia(String prefijo){
		return cuentaRepository.findByCodigoCuentaStartingWith(prefijo);
	}
	
	public List<Cuenta> getAll(){
		if(loaded) {
			return listaCuentas;
		}
		else {
			listaCuentas=cuentaRepository.findAll();
			loaded=true;
		}
		return listaCuentas;
	}
	
	public void delete(Cuenta cuenta) {
		cuentaRepository.delete(cuenta);
		load();
	}
}
