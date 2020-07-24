package com.atc.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atc.model.Cuenta;
import com.atc.model.DetallePartida;
import com.atc.model.Partida;
import com.atc.service.CuentaService;
import com.atc.service.DetallePartidaService;
import com.atc.service.PartidaService;
/*
 * author Adilson Arbuez
 */
@Controller
public class DetallePartidaController {
	@Autowired
	DetallePartidaService  detallePartidaService;
	@Autowired
	PartidaService partidaService; 
	@Autowired
	CuentaService  cuentaService;
	@Autowired
	MainController main;
	
	@GetMapping("/EditarDetalle/{idDetalle}")
	public String editar(Model model,@PathVariable(name="idDetalle") int id){
		DetallePartida detalle=detallePartidaService.getDetalle(id);
		
		List<Cuenta> listaCuentas=cuentaService.getAll();
		model.addAttribute("listaCuentas", listaCuentas);
		model.addAttribute("detalle", detalle);
		model.addAttribute("idPartida", detalle.getPartida().getId());
		return"EdicionDetalle";
	}
	
	//actualizar modificacion
	@PostMapping("/Edicion")
	public String editar(Model model,@RequestParam("id")String id,@RequestParam("descripcion") String descripcion,
			@RequestParam("debe") String debe, @RequestParam("haber") String haber) {
		boolean error=false;
		double debeInt=0;
		double haberInt=0;
		int idV=0;
		try {
			Integer.parseInt(descripcion);//descripcion es el numero de cuenta
			idV=Integer.parseInt(id);
			debeInt=Double.parseDouble(debe);
			haberInt=Double.parseDouble(haber);
		}
		
		catch(Exception e) {
			error=true;
		}
		
		if(!error) {
			Partida partida=detallePartidaService.getDetalle(idV).getPartida();
			DetallePartida detallePartida=new DetallePartida(idV,descripcion,debeInt,haberInt);
			detallePartida.setPartida(partida);
			detallePartidaService.update(detallePartida);
		}
		
		model.addAttribute("error", error);
		return main.libroInicio(model);
	}
	
	@GetMapping("/EliminarDetalle/{idDetalle}")
	public String eliminar(@PathVariable(name="idDetalle") int id,Model model){
		boolean error=false;
		try {
			DetallePartida detalle=detallePartidaService.getDetalle(id);
			detallePartidaService.delete(detalle);
		}catch(Exception e) { 
			error=true;
		}
		
		model.addAttribute("error", error);
		return main.libroInicio(model);
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/NuevoDetalle")
	public String AgregarDetalle(Model model,HttpServletRequest request,@RequestParam("descripcion") String descripcion,
			@RequestParam("debe") String debe, @RequestParam("haber") String haber) {
		
		boolean error=false;
		double debeInt=0;
		double haberInt=0;
		HttpSession session=request.getSession();
		
		List<DetallePartida> lista=new ArrayList<DetallePartida>();
		if(session.getAttribute("lista")!=null) {
			lista=(List<DetallePartida>)session.getAttribute("lista");
		}
		
		try {
		    Integer.parseInt(descripcion);//numero de cuenta afectada, solo s eomprueba que sea un numero entero
			debeInt=Double.parseDouble(debe);
			haberInt=Double.parseDouble(haber);
		}
		
		catch(Exception e) {
			error=true;
		}
		
		if(!error) {
			DetallePartida detallePartida=new DetallePartida(descripcion,debeInt,haberInt);
			lista.add(detallePartida);
			session.setAttribute("lista", lista);
		}
		Partida partida=(Partida)session.getAttribute("partida");
	    List<Cuenta> listaCuentas=cuentaService.getAll();
		
		model.addAttribute("lista", lista);
		model.addAttribute("idPartida", partida.getId());
		model.addAttribute("listaCuentas", listaCuentas);
		model.addAttribute("error", error);
		return "DetallePartida";
	}
	
	@PostMapping("/IniciaPartida")
	public String nuevaPartida(Model model,HttpServletRequest request, @RequestParam("fecha") String fecha,@RequestParam("enunciado") String enunciado) {
		HttpSession session=request.getSession();
		boolean errorAdd=false;
		Partida partida =new Partida();
		
		try {
			LocalDate localDate = LocalDate.parse(fecha);
			partida.setFecha(localDate);
			partida.setEnunciado(enunciado);
			//guardar partida
			session.setAttribute("partida", partida);
		}catch(Exception e) {
			errorAdd=true;
			model.addAttribute("errorAdd", errorAdd);
			return "ControladorPartida";
		}
		List<Cuenta> listaCuentas=cuentaService.getAll();
		model.addAttribute("listaCuentas", listaCuentas);
		model.addAttribute("error", false);
		model.addAttribute("partida", partida);
		return "DetallePartida";
	}
	
	@GetMapping("/AgregaPartida")
	@SuppressWarnings("unchecked")
	public String controladorPartida(Model model,HttpServletRequest request) {
		//hacer verificacion que la partida este cuadrada
		HttpSession session=request.getSession();
		Partida partida=(Partida)session.getAttribute("partida");
		
		List<DetallePartida> lista=(List<DetallePartida>)session.getAttribute("lista");
		
		double abonos=0;
		double cargos=0;
		for(DetallePartida actual:lista) {
			abonos+=actual.getDebe();
			cargos+=actual.getHaber();
		}
		
		if(abonos==cargos) {
			partidaService.Save(partida);
			
			lista.forEach(d->d.setPartida(partida));
			detallePartidaService.createList(lista);
			session.removeAttribute("partida");
			session.removeAttribute("lista");
			
			return "ControladorPartida";
		}
		
		else {
			boolean denegado=true;
			List<Cuenta> listaCuentas=cuentaService.getAll();
			model.addAttribute("lista", lista);
			model.addAttribute("idPartida", partida.getId());
			model.addAttribute("listaCuentas", listaCuentas);
			model.addAttribute("denegado", denegado);
			return "DetallePartida";
		}
	}
	@GetMapping("/Cancelar")
	public String cancelar(HttpServletRequest request) {
		HttpSession session=request.getSession();
		session.removeAttribute("partida");
		session.removeAttribute("lista");
		return "ControladorPartida";
	}
	
	
}
