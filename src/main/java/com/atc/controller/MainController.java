package com.atc.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atc.model.ClaveValor;
import com.atc.model.Linea;
import com.atc.service.CuentaService;
import com.atc.service.ReportService;
/*
 * author Adilson Arbuez
 */
@Controller
public class MainController{
	@Autowired
	CuentaService cuentaService;
	@GetMapping("/ControladorPartida")
	public String controladorPartida() {
		return "ControladorPartida";
	}
	
	@GetMapping("/intro")
	public String intro() {
		return "ControladorPartida";
	}
	
	@Autowired
	ReportService reportService;
	
	@GetMapping("/LibroDiario")
	public String libroInicio(Model model) {
		boolean empty=true;
		model.addAttribute("empty", empty);
		return "LibroDiario";
	}
	 
    @GetMapping("/LibroDiario/detalle")
    public String libro(Model model, @RequestParam("fecha1") String fecha1, @RequestParam("fecha2") String fecha2) {
    	List<Linea> lineas =new ArrayList<Linea>();
    	
		LocalDate fechaInicio=null;
		LocalDate fechaFin=null;
		if (!fecha1.isBlank() && !fecha2.isBlank()) {
			fechaInicio = LocalDate.parse(fecha1);
			fechaFin = LocalDate.parse(fecha2);
			
			model.addAttribute("fecha1", fecha1);
			model.addAttribute("fecha2", fecha2);
		}
    	
    	lineas=reportService.libro(fechaInicio,fechaFin);
    	boolean empty=false;
    
    	if(lineas.isEmpty()) {
    		empty=true;
    	}
    	
    	double debe=0;
    	double haber=0;
    	for(Linea current:lineas) {
    		debe+=current.getDebe();
    		haber+=current.getHaber();
    	}
    	
    	model.addAttribute("debe", debe);
    	model.addAttribute("haber", haber);
    	model.addAttribute("lineas", lineas);
    	model.addAttribute("empty", empty);
    	model.addAttribute("error", false);
    	return "LibroDiario";
    }
    
    @GetMapping("/Mayorizacion")
	public String mayorizacionD(Model model) {
		boolean empty=true;
		model.addAttribute("empty", empty);
		return "Mayorizacion";
	}
    
    @GetMapping("/Mayorizacion/detalle")
    public String mayorizacion(Model model, @RequestParam("fecha1") String fecha1, @RequestParam("fecha2") String fecha2){	
		LocalDate fechaInicio=null;
		LocalDate fechaFin=null;
		if (!fecha1.isBlank() && !fecha2.isBlank()) {
			fechaInicio = LocalDate.parse(fecha1);
			fechaFin = LocalDate.parse(fecha2);
			
			model.addAttribute("fecha1", fecha1);
			model.addAttribute("fecha2", fecha2);
		}
   
    	boolean empty=false;
    	List<Linea> lineas=reportService.mayorizacion(fechaInicio,fechaFin);
    	
    	if(lineas.isEmpty()) {
    		empty=true;
    	}
    	model.addAttribute("lineas", lineas);
    	model.addAttribute("empty", empty);
    	return "Mayorizacion";
    }
    
    @GetMapping("/BalanzaComprobacion")
    public String balanza(Model model) {
    	boolean empty=false;
    	List<Linea> lineas=reportService.balanza();
    	if(lineas.isEmpty()) {
    		empty=true;
    	}
    	
    	double debe=0;
    	double haber=0;
    	double deudor=0;
    	double acreedor=0;
    	
    	//totales de la ultima fila
    	for(Linea current:lineas) {
    		debe+=current.getDebe();
    		haber+=current.getHaber();
    		deudor+=current.getDeudor();
    		acreedor+=current.getAcreedor();
    	}
    	model.addAttribute("debe", debe);
    	model.addAttribute("haber", haber);
    	model.addAttribute("deudor", deudor);
    	model.addAttribute("acreedor", acreedor);
    	model.addAttribute("lineas", lineas);
    	model.addAttribute("empty", empty);
    	return "BalanzaComprobacion";
    }
    
    @GetMapping("/Resultados")
	public String ResultadosInicio(Model model) {
		boolean empty=true;
		model.addAttribute("empty", empty);
		return "Resultados";
	}
    
    @GetMapping("/Resultados/detalle")
    public String estado(Model model, @RequestParam("fecha1") String fecha1, @RequestParam("fecha2") String fecha2) {
    	LocalDate fechaInicio=null;
		LocalDate fechaFin=null;
		if (!fecha1.isBlank() && !fecha2.isBlank()) {
			fechaInicio = LocalDate.parse(fecha1);
			fechaFin = LocalDate.parse(fecha2);
			
			model.addAttribute("fecha1", fecha1);
			model.addAttribute("fecha2", fecha2);
		}
    	boolean empty=false;
    	List<ClaveValor> estado=reportService.estado(fechaInicio,fechaFin);
    	if(estado.isEmpty()) {
    		empty=true;
    	}
    	model.addAttribute("estado", estado);
    	model.addAttribute("empty", empty);
    	return "Resultados";
    }
    
	@GetMapping("/Balance")
	public String balanceInicio(Model model) {
		boolean empty=true;
		model.addAttribute("empty", empty);
		return "Balance";
	}
    
    @GetMapping("/Balance/detalle")
    public String balance(Model model, @RequestParam("fecha1") String fecha1, @RequestParam("fecha2") String fecha2) {
    	LocalDate fechaInicio=null;
		LocalDate fechaFin=null;
		if (!fecha1.isBlank() && !fecha2.isBlank()) {
			fechaInicio = LocalDate.parse(fecha1);
			fechaFin = LocalDate.parse(fecha2);
			
			model.addAttribute("fecha1", fecha1);
			model.addAttribute("fecha2", fecha2);
		}
    	List<ClaveValor> balance=reportService.balance(fechaInicio,fechaFin);
    	boolean error=false;
    	if(balance.isEmpty()) {
    		error=true;
    	}
    	model.addAttribute("balance", balance);
    	model.addAttribute("empty", error);
    	return "Balance";
    }

	// Balance/detalle?fecha1=2020-11-30&fecha2=2020-12-30
	@GetMapping("/demo")
	public String balanceDemo(Model model) {

		LocalDate fechaInicio = LocalDate.parse("2020-11-30");
		LocalDate fechaFin = LocalDate.parse("2020-12-30");

		model.addAttribute("fecha1", fechaInicio);
		model.addAttribute("fecha2", fechaFin);

		List<ClaveValor> balance = reportService.balance(fechaInicio, fechaFin);
		boolean error = false;
		if (balance.isEmpty()) {
			error = true;
		}
		model.addAttribute("balance", balance);
		model.addAttribute("empty", error);
		return "Balance";
	}
}
