package com.atc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atc.model.Cuenta;
import com.atc.service.CuentaService;
/*
 * author Adilson Arbuez
 */
@Controller
public class CuentaController {
	
	@Autowired
	CuentaService cuentaService;
	@PostMapping("/NuevaCuenta")
	public String nuevaCuenta(Model model,@RequestParam("codigo") String codigo,@RequestParam("descripcion") String descripcion,
			@RequestParam("saldo") String saldo,@RequestParam("tipo") String tipo,@RequestParam("nivel") String nivel) {
	    int nivelInt=0;
	    boolean error=false;
		try {
			Integer.parseInt(codigo);
			nivelInt=Integer.parseInt(nivel);
		}
		catch(Exception e) {
			error=true;
		}
		if(!error) {
			Cuenta cuenta = new Cuenta(codigo,descripcion,saldo,tipo,nivelInt);
			cuentaService.save(cuenta);
		}
		List<Cuenta> lista=cuentaService.getAll();
		model.addAttribute("lista", lista);
		model.addAttribute("result", error);
		return "Cuentas";
	}
	
	@GetMapping("/verCuentas")
	public String verCuentas(Model model) {
		List<Cuenta> lista=cuentaService.getAll();
		model.addAttribute("lista", lista);
		return "Cuentas";
	}
}
