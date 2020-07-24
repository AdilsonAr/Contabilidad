package com.atc.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atc.model.ClaveValor;
import com.atc.model.Cuenta;
import com.atc.model.DetallePartida;
import com.atc.model.Linea;
import com.atc.model.Partida;
/*
 * author Adilson Arbuez
 */
@Service
public class ReportService {
	@Autowired
	private PartidaService partidaService;
	@Autowired
	private DetallePartidaService detalleService;
	@Autowired
	private CuentaService cuentaService;
	
	public ReportService() {
	}
	
	public List<Linea> libro(LocalDate fechaInicio, LocalDate fechaFin) {
		
		List<Partida> listaPartidas=new ArrayList<Partida>();
		if (fechaInicio != null && fechaFin != null) {
			listaPartidas = partidaService.getRank(fechaInicio, fechaFin);
		}else {
			listaPartidas=partidaService.getAll();
		}
		
		List<Linea> lineas = new ArrayList<Linea>();
		List<Cuenta> listaCuentas = cuentaService.getAll();

		for (Partida current : listaPartidas) {
			int id = current.getId();
			LocalDate fecha = current.getFecha();

			List<DetallePartida> detalles = detalleService.readByPartida(current);

			for (DetallePartida currentDetalle : detalles) {
				String codCuenta = currentDetalle.getCodCuenta();
				Linea linea = new Linea(fecha, id, codCuenta, concepto(codCuenta, listaCuentas),
						currentDetalle.getDebe(), currentDetalle.getHaber(), currentDetalle.getId());

				linea.setEnunciado(current.getEnunciado());
				lineas.add(linea);
			}

		}

		return lineas;
	}
	
	public List<Linea> mayorizacion(LocalDate fechaInicio, LocalDate fechaFin) {
		List<Partida> listaPartidas=new ArrayList<Partida>();
		if (fechaInicio != null && fechaFin != null) {
			listaPartidas = partidaService.getRank(fechaInicio, fechaFin);
		}else {
			listaPartidas=partidaService.getAll();
		}
		List<Linea> lineas = new ArrayList<Linea>();
		List<Cuenta> listaCuentas = cuentaService.getAll();

		if (!listaPartidas.isEmpty()) {
			for (Cuenta currentCuenta : listaCuentas) {
				String concepto = currentCuenta.getDescripcion();
				String codigo = currentCuenta.getCodigoCuenta();
				List<DetallePartida> detalles = detalleService.getDetallesByCod(codigo);
				double saldo = 0;

				for (DetallePartida currentDetalle : detalles) {
					int codPartida = currentDetalle.getPartida().getId();
					Partida partida = currentDetalle.getPartida();
					double debe = 0;
					double haber = 0;

					// calculando los valores para cada fila
					debe = currentDetalle.getDebe();
					haber = currentDetalle.getHaber();
					if (currentCuenta.getSaldo().equalsIgnoreCase("Deudor")) {
						saldo += debe - haber;
					} else {
						saldo += haber - debe;
					}
					Linea linea = new Linea(partida.getFecha(), codPartida, codigo, concepto, partida.getEnunciado(),
							debe, haber, saldo);
					lineas.add(linea);
				}

			}
		}
		return lineas;
	}
	
	public List<Linea> balanza() {
		List<Partida> listaPartidas=partidaService.getAll();
		List<Cuenta> listaCuentas = cuentaService.getAll();
		
		return balanzaCore(listaPartidas,listaCuentas);
	}
	
	public List<Linea> balanzaCore(List<Partida> listaPartidas,List<Cuenta> listaCuentas) {
		List<Linea> lineas = new ArrayList<Linea>();
		
		if (!listaPartidas.isEmpty()) {
			// sera un registro por cuenta
			for (Cuenta currentCuenta : listaCuentas) {
				String codigo = currentCuenta.getCodigoCuenta();
				// se recorren todos los detalles de partida referidos a la cuenta actual
				List<DetallePartida> detalles = detalleService.ReadByPartidaCuenta(codigo, listaPartidas);
				if (!detalles.isEmpty()) {

					String concepto = currentCuenta.getDescripcion();
					double cargo = 0;
					double abono = 0;
					double deudor = 0;
					double acreedor = 0;

					for (DetallePartida currentDetalle : detalles) {
						cargo += currentDetalle.getDebe();
						abono += currentDetalle.getHaber();
					}
					
					//cuentas de saldo deudor
					if (currentCuenta.getSaldo().equalsIgnoreCase("Deudor")) {
						deudor = cargo - abono;
					//cuentas de saldo acreedor
					} else {
						acreedor = abono - cargo;
					}

					Linea linea = new Linea(codigo, concepto, cargo, abono, deudor, acreedor);
					lineas.add(linea);
				}
			}
		}
		return lineas;
	}
	
	//suma los saldos de las cuentas ingresadas, es para acumular los datos de subcuentas
	private Linea suma(List<Linea> lineas) {
		Linea linea=new Linea();
		double acreedor=0;
		double deudor=0;
		for(Linea current:lineas) {
			acreedor+=current.getAcreedor();
			deudor+=current.getDeudor();
		}
		linea.setAcreedor(acreedor);
		linea.setDeudor(deudor);
		return linea;
	}
	
	//true, se maneja como acreedor
	//false, se tratara como deudor
	private double aportePorFamilia(List<ClaveValor> lista,String familia,String prefijo, List<Partida> partidasEnRango, boolean acreedor) {
		double aporte=0;
		List<Cuenta> cuentasAporte=cuentaService.readByFamilia(prefijo);
		//estado de cuentas seleccionadas en las partidas seleccionadas
		List<Linea> saldosCuentas=balanzaCore(partidasEnRango, cuentasAporte);
		//suma de los aportes en conjunto
		Linea resultado=suma(saldosCuentas);
		if(acreedor) {
			aporte=resultado.getAcreedor();
		}else {
			aporte=resultado.getDeudor();
		}
		ClaveValor c1=new ClaveValor(familia,aporte);
		lista.add(c1);
		
		//se añaden las cuentas sumadas como detalles
		for(Linea current:saldosCuentas) {
			ClaveValor  c2=new ClaveValor();
			c2.setClave(current.getConcepto());
			if(acreedor) {
				c2.setValor(current.getAcreedor());
			}else {
				c2.setValor(current.getDeudor());
			}
			lista.add(c2);
		}
		
		return aporte;
	}
	
	private List<ClaveValor> estadoResultados;
	private List<ClaveValor> balanceG;
	
	public List<ClaveValor> estado(LocalDate fechaInicio, LocalDate fechaFin) {
		// acceder a los datos y crear el estado de resultados
		estadoResultados=new ArrayList<ClaveValor>();
		//con las partidas del periodo especificado 
		List<Partida> partidasEnRango=new ArrayList<Partida>();
		if (fechaInicio != null && fechaFin != null) {
			partidasEnRango = partidaService.getRank(fechaInicio, fechaFin);
		}else {
			partidasEnRango=partidaService.getAll();
		}
		
		double ingresosVentas = 0;
		double costoVentas = 0;
		double otrosIngresos = 0;
		//obteniendo las sumas de cuentas clave y sus derivadas
		ingresosVentas=aportePorFamilia(estadoResultados,"Ventas","5101", partidasEnRango, true);
		costoVentas=aportePorFamilia(estadoResultados,"Costo de ventas","4101", partidasEnRango, false);
		
		double utilidadBruta=ingresosVentas-costoVentas;
		ClaveValor c1=new ClaveValor("Utilidad bruta",utilidadBruta);
		estadoResultados.add(c1);
		
		double gastosOperativos=aportePorFamilia(estadoResultados,"Gastos operativos","42", partidasEnRango, false);
		//añadir cuentas con movimientos
		
		double utilidadOperativa=utilidadBruta-gastosOperativos;
		ClaveValor c5=new ClaveValor("Utilidad operativa",utilidadOperativa);
		estadoResultados.add(c5);
		
		otrosIngresos = aportePorFamilia(estadoResultados,"Otros ingresos","5102", partidasEnRango, true);
		//añadir cuentas de la rama 'otros ingresos'
		
		double utilidadAntes=utilidadOperativa+otrosIngresos;
		ClaveValor c7=new ClaveValor("Utilidad antes",utilidadAntes);
		estadoResultados.add(c7);
		
		double isr=isr(utilidadAntes);
		ClaveValor c8=new ClaveValor("Impuesto sobre la renta",isr);
		estadoResultados.add(c8);
		
		double utilidadNeta=utilidadAntes-isr;
		ClaveValor c9=new ClaveValor("Utilidad neta",utilidadNeta);
		estadoResultados.add(c9);
		
		return estadoResultados;
	}
	
	public List<ClaveValor> balance(LocalDate fechaInicio, LocalDate fechaFin) {
		// acceder a los datos y crear el balance general
		balanceG = new ArrayList<ClaveValor>();
		// con las partidas del periodo
		List<Partida> partidasEnRango=new ArrayList<Partida>();
		if (fechaInicio != null && fechaFin != null) {
			partidasEnRango = partidaService.getRank(fechaInicio, fechaFin);
		}else {
			partidasEnRango=partidaService.getAll();
		}
		
		double corriente=aportePorFamilia(balanceG,"Activo corriente","11", partidasEnRango, false);
		double noCorriente=aportePorFamilia(balanceG,"Activo no corriente","12", partidasEnRango, false);
		ClaveValor objeto1=new ClaveValor("Total activo",(corriente+noCorriente));
		balanceG.add(objeto1);
		
		double pasivoCorriente=aportePorFamilia(balanceG,"Pasivo corriente","21", partidasEnRango, true);
		double pasivoNoCorriente=aportePorFamilia(balanceG,"Pasivo no corriente","22", partidasEnRango, true);
		double patrimonio=aportePorFamilia(balanceG,"Patrimonio","31", partidasEnRango, true);
		ClaveValor objeto2=new ClaveValor("Total pasivo y patrimonio",(pasivoCorriente+pasivoNoCorriente+patrimonio));
		balanceG.add(objeto2);
		
		return balanceG;
	}
	
	private double isr(double utilidad) {
		double isr=0;
		if(utilidad>=0.01 && utilidad<=4064) {
			return isr;
		} else if(utilidad>=4064.01 && utilidad<=9142.86) {
			isr= 212.12+0.1*(utilidad-4064);
		}else if(utilidad>=9142.87 && utilidad<=22857.14) {
			isr= 720+0.2*(utilidad-9142.86);
		}else if(utilidad>=22857.15) {
			isr= 3462.86+0.3*(utilidad-22857.14);
		}
		return isr;
	}
	
	private String concepto(String id,List<Cuenta> listaCuentas) {
    	String concepto="";
    	for(Cuenta c:listaCuentas) {
    		if(c.getCodigoCuenta().equalsIgnoreCase(id)) {
    			concepto=c.getDescripcion();
    		}
    	}
    	return concepto;
    }
}
