package org.bryampaniagua.abstracta;

import java.util.HashMap;

import org.bryampaniagua.abstracta.AbstractRol;
import org.bryampaniagua.utilidades.Decodificador;
import org.bryampaniagua.utilidades.eventos.Escuchador;
import org.bryampaniagua.manejadores.ManejadorMiembros;
import org.bryampaniagua.manejadores.ManejadorProductos;
import org.bryampaniagua.manejadores.ManejadorHistorial;
import org.bryampaniagua.beans.beansMiembro;
	/**
	*Clase appMiembro ejecuta los procesos propios de un miembro
	*@author Bryam Paniagua[B-2012442]
	 **/
public class appMiembro extends AbstractRol implements Escuchador{
	/**
	*En el constructor se recibe el decodificador y se agrega la clase abstracta
	**/
	public appMiembro(Decodificador decodificar){
		decodificar.agregarEscuchador(this);	
		super.setDecodificador(decodificar);
	}
	/**
	*Metodo comandos hace el envio al metodo con mismo nombre en la clase abstracta
	**/
	public void comandos(){
		super.comandos();
	}
	/**
	*El metodo accionador recibe una accion y parametros y ejecuta segun los valores recibidos}
	*@param accion
	*@param accionReaccion
	**/
	public void accionador(String accion, HashMap<String, String> accionReaccion){
		switch(accion){
			case "add downline":
				beansMiembro downline = new beansMiembro();
				int cod= 1;
				try{
					downline.setNombre(accionReaccion.get("nombre"));
					downline.setNick(accionReaccion.get("nick"));
					downline.setPassword(accionReaccion.get("password"));
					downline.setPin(Integer.parseInt(accionReaccion.get("pin")));
					downline.setEdad(Integer.parseInt(accionReaccion.get("edad")));
					downline.setTarjeta(Long.parseLong(accionReaccion.get("tarjeta")));
					downline.setEstado("nuevo");
					beansMiembro unico = ManejadorMiembros.getInstancia().buscarDownline(accionReaccion.get("nick"));
					beansMiembro upLine;
					if(accionReaccion.get("down") != null){
						upLine = ManejadorMiembros.getInstancia().buscarDownline(accionReaccion.get("down"));
					}else{
						upLine = ManejadorMiembros.getInstancia().getMiembro();
					}
					if(unico == null ){
						if(upLine != null){
							ManejadorMiembros.getInstancia().addDownline(downline, upLine);
							System.out.println("Se ha ingresado exitosamente");
							ManejadorHistorial.getInstancia().extraerDatos(cod++, downline, upLine, ManejadorMiembros.getInstancia().getMiembro());
						}else{
							System.out.println("No existe el miembro, "+accionReaccion.get("down"));
						}
					}else{
						System.out.println("Ya existe.");
					}
				}catch(Exception err){
				}	
			break;
			case "buy product":
				if(accionReaccion.get("nombre") != null && accionReaccion.get("cantidad") != null){
					ManejadorProductos.getInstancia().buyProduct(accionReaccion.get("nombre"), Integer.parseInt(accionReaccion.get("cantidad")));
					ManejadorMiembros.getInstancia().payMembers();	
				}
			break;
			case "show product":
				showProduct(accionReaccion.get("nombre"));
			break;
			case "edit me":
				try{
					if(accionReaccion.get("nombre") != null){
						ManejadorMiembros.getInstancia().getMiembro().setNombre(accionReaccion.get("nombre"));
						System.out.println("Modificacion exitosa");
					}
					if(accionReaccion.get("nick") != null){
						ManejadorMiembros.getInstancia().getMiembro().setNick(accionReaccion.get("nick"));
						System.out.println("Modificacion exitosa");						
					}
					if(accionReaccion.get("password") != null){				
						ManejadorMiembros.getInstancia().getMiembro().setPassword(accionReaccion.get("password"));
						System.out.println("Modificacion exitosa");
					}
					if(accionReaccion.get("edad") != null){				
						ManejadorMiembros.getInstancia().getMiembro().setEdad(Integer.parseInt(accionReaccion.get("edad")));
						System.out.println("Modificacion exitosa");
					}
					if(accionReaccion.get("tarjeta") != null){				
						ManejadorMiembros.getInstancia().getMiembro().setTarjeta(Integer.parseInt(accionReaccion.get("tarjeta")));
						System.out.println("Modificacion exitosa");
					}
					if(accionReaccion.get("pin") != null){				
						ManejadorMiembros.getInstancia().getMiembro().setPin(Integer.parseInt(accionReaccion.get("pin")));
						System.out.println("Modificacion exitosa");
					}
				}catch(Exception err){
				}
			break;
			case "search downline":
				super.searchDown(accionReaccion.get("nick"));
			break;
			case "show history downline":
				ManejadorHistorial.getInstancia().show(accionReaccion.get("nick"));
			break;
			case "show history buy":
				ManejadorProductos.getInstancia().showSales(accionReaccion.get("nick"));
			break;
			case "select":
				try{
					String campos = "null";
					for(int i = 0; i < accionReaccion.size() ; i++){
						campos = campos+"-"+accionReaccion.get("Dato"+i);
					}
					String campo[] = campos.trim().substring(campos.indexOf("-")+1 ,campos.length()).split("-");
					String otros[] = accionReaccion.get("Otros").trim().split(",");
					String lista = otros[0];
					String clave = otros[1];
					String condicion = otros[2];
					String valor = otros[3];
					System.out.println();
						
					if(lista.equals("administradores")){
						System.out.println("No puede ver estos datos.");
					}else{
						super.setConsulta(campo, lista, clave, condicion, valor);
					}
				}catch(Exception err){
					System.out.println("El error se produjo por "+err);
				}
			break;
			default:
				System.out.println(accion+", no se reconoce como un comando interno o externo.");
			break;
		}
	}
	/**
	*Metodo accionadorProceso recibe solo la accion y ejecuta segun su valor
	*@param accion
	**/
	public void accionadorProceso(String accion){
		switch(accion){
			case "logout":
				ManejadorMiembros.getInstancia().desautenticarMiembro();
			break;
			case "show me money":
				ManejadorMiembros.getInstancia().showMeMoney(ManejadorMiembros.getInstancia().getMiembro(), 1);
			break;
			case "show history downline":
				ManejadorHistorial.getInstancia().show(ManejadorMiembros.getInstancia().getMiembro().getNick());
			break;
			case "show history buy":
				ManejadorProductos.getInstancia().showSales(ManejadorMiembros.getInstancia().getMiembro().getNick());
			break;
			case "show me":
				ManejadorMiembros.getInstancia().showMe();
			break;
			case "show status members":
				showStatus();
			break;
			case "list products":
				showProducts(2);												
			break;
			case "list downlines":
				listMembers();	
			break;
			case "buy":
				System.out.println(" buy");					
				System.out.println("     [dowlines ('datos')]");					
			break;
			case "add":
				System.out.println(" add");					
				System.out.println("     [dowlines ('datos') (down) ('idDownline') 'producto']");		
			break;
			case "search":
				System.out.println(" search");					
				System.out.println("        [products ('nombre') ('tarjeta de credito')]");	
			break;
			case "show":
				System.out.println(" show");
				System.out.println("      [product (nombre producto)]");
				System.out.println("      [downline (idDowline {money})]");
				System.out.println("      [downline (me {money})]");
			break;
			case "list":
				System.out.println(" list");
				System.out.println("      [members]");
				System.out.println("      [products]");
				System.out.println("      [dowlines (idDownline)]");
			break;
			case "cls":
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			break;
			default:
				System.out.println(accion+", no se reconoce como un comando interno o externo.");
			break;
		}	
	}
}