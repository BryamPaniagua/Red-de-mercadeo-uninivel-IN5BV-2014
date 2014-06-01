package org.bryampaniagua.abstracta;

import java.util.HashMap;
import java.util.ArrayList;

import org.bryampaniagua.utilidades.Decodificador;
import org.bryampaniagua.utilidades.eventos.Escuchador;
import org.bryampaniagua.manejadores.ManejadorAdministradores;
import org.bryampaniagua.manejadores.ManejadorProductos;
import org.bryampaniagua.manejadores.ManejadorMiembros;
import org.bryampaniagua.beans.beansAdministrador;
import org.bryampaniagua.beans.beansProducto;
import org.bryampaniagua.beans.beansMiembro;
import org.bryampaniagua.beans.beansVentas;
	/**
	*Clase appAdministrador, ejecuta procesos unicos desde el manejador y compartidos con miembro desde la clase abstracta. 
	*@author Bryam Paniagua[B-2012442]
	**/
 
public class appAdministrador extends AbstractRol implements Escuchador{
	
	public appAdministrador(Decodificador decodificar){
		decodificar.agregarEscuchador(this);	
		super.setDecodificador(decodificar);
	}
	public void comandos(){                    
		super.comandos();
	}
	/**
	*EL metodo accionadorProceso que es implementado por la interfaz Escuchador recibe de la clase Decodificador 
	*@param accion
	**/
	public void accionadorProceso(String accion){
		switch(accion){
			case "list":
				System.out.println(" list");
				System.out.println("      [user|members]");
				System.out.println("      [products]");
				System.out.println("      [dowlines (idDownline)]");
			break;
			case "show":
				System.out.println(" show");
				System.out.println("      [product (nombre producto)]");
				System.out.println("      [sales (producto 'nombre producto')]");
				System.out.println("      [downline (idDowline {money})]");
				System.out.println("      [downline (me {money})]");
				System.out.println("      [history (me  [buy] [downline] ['idDownline'])]");
			break;
			case "logout":
				ManejadorAdministradores.getInstancia().desautenticarAdmin();
			break;
			case "search":
				System.out.println(" search");					
				System.out.println("        [products ('nombre') ('tarjeta de credito')]");					
			break;
			case "add":
				System.out.println(" add");					
				System.out.println("     [member ('datos')");					
				System.out.println("     [user 'datos']");					
				System.out.println("     [product 'datos']");								
				System.out.println("     [ofert 'datos']");								
			break;
			case "edit":
				System.out.println(" edit");					
				System.out.println("      [me ('propiedad=nuevoValor']");								
				System.out.println("      [product 'datosNuevos']");								
			break;
			case "remove":
				System.out.println(" remove");					
				System.out.println("        [producto ('nombre producto')");													
			break;
			case "show me":
				ManejadorAdministradores.getInstancia().showMe();												
			break;
			case "list admins":
				ManejadorAdministradores.getInstancia().mostrarAdmins();												
			break;
			case "list downlines":
				listMembers();												
			break;
			case "list products":
				showProducts(1);												
			break;
			case "show sales":
				ManejadorProductos.getInstancia().showSales();
			break;
			case "show status members":
				showStatus();
			break;
			case "cls":
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			break;
			default:
				System.out.println(accion+", no se reconoce como un comando interno o externo.");
			break;
		}
	}
	/**
	*El metodo accionador que es implementado tambien por la interfaz escuchador y esta vez recibe 
	*@param accion
	*@param accionReaccion
	**/
	public void accionador(String accion, HashMap<String, String> accionReaccion){
		switch(accion){
			case "add admin":
				beansAdministrador us = new beansAdministrador();
					try{
						
						us.setNombre(accionReaccion.get("nombre"));
						us.setNick(accionReaccion.get("nick"));
						us.setPassword(accionReaccion.get("password"));
						us.setEdad(Integer.parseInt(accionReaccion.get("edad")));
						beansMiembro unico = ManejadorMiembros.getInstancia().buscarDownline(accionReaccion.get("nick"));
						beansAdministrador unico1 = ManejadorAdministradores.getInstancia().buscarAdmin(accionReaccion.get("nick"));
						if(unico == null || unico1 == null){
							ManejadorAdministradores.getInstancia().addAdmin(us);
							System.out.println("	Usuario agregado satisfactoriamente.");
						}
					}catch(Exception err){
					
					}
			break;
			case "add member":
				beansMiembro miembro = new beansMiembro();
				try{		
					miembro.setNombre(accionReaccion.get("nombre"));
					miembro.setNick(accionReaccion.get("nick"));
					miembro.setPassword(accionReaccion.get("password"));
					miembro.setPin(Integer.parseInt(accionReaccion.get("pin")));
					miembro.setEdad(Integer.parseInt(accionReaccion.get("edad")));
					miembro.setTarjeta(Integer.parseInt(accionReaccion.get("tarjeta")));
					beansMiembro existe = ManejadorMiembros.getInstancia().buscarDownline(accionReaccion.get("nick"));
					beansAdministrador existe1 = ManejadorAdministradores.getInstancia().buscarAdmin(accionReaccion.get("nick"));
					if(existe == null || existe1 == null){
						ManejadorMiembros.getInstancia().addMember(miembro);
						System.out.println("Se ha ingresado exitosamente");
					}else{
						System.out.println("Ya existe.");
					}
				}catch(Exception err){
				}	
			break;
			case "edit me":
				try{
					if(accionReaccion.get("nombre") != null){
						ManejadorAdministradores.getInstancia().getAdmin().setNombre(accionReaccion.get("nombre"));
						System.out.println("Modificacion exitosa");
					}
					if(accionReaccion.get("nick") != null){
						ManejadorAdministradores.getInstancia().getAdmin().setNick(accionReaccion.get("nick"));
						System.out.println("Modificacion exitosa");						
					}
					if(accionReaccion.get("password") != null){				
						ManejadorAdministradores.getInstancia().getAdmin().setPassword(accionReaccion.get("password"));
						System.out.println("Modificacion exitosa");
					}
					if(accionReaccion.get("edad") != null){				
						ManejadorAdministradores.getInstancia().getAdmin().setEdad(Integer.parseInt(accionReaccion.get("edad")));
						System.out.println("Modificacion exitosa");
					}
				}catch(Exception err){
					System.out.println("Se ha producido un error.");
				}
			break;
			case "remove admin":
				if(ManejadorAdministradores.getInstancia().getAdmin().getNick().equals(accionReaccion.get("nick"))){
					System.out.println("No puede borrar tu cuenta mientras este en uso.");
				}else{
					ManejadorAdministradores.getInstancia().removerAdmin(accionReaccion.get("nick"));
				}
			break;
			case "add product":
				beansProducto producto = new beansProducto();
				try{	
					producto.setNombre(accionReaccion.get("nombre"));
					producto.setCategoria(accionReaccion.get("categoria"));
					producto.setPrecio(Integer.parseInt(accionReaccion.get("precio")));
					producto.setExistencias(Integer.parseInt(accionReaccion.get("existencias")));
					ManejadorProductos.getInstancia().addProduct(producto);
					System.out.println("Se ha ingresado existosamente");
				}catch(Exception err){
					System.out.println("No se ha podido ingresar");
				}
			break;
			case "edit product":
				try{
					beansProducto product = ManejadorProductos.getInstancia().searchName(accionReaccion.get("nombre"));
					if(accionReaccion.get("nombreNuevo") != null && product != null){
						product.setNombre(accionReaccion.get("nombreNuevo"));
						System.out.println("Modificacion exitosa");
					}
					if(accionReaccion.get("categoria") != null && product != null){
						product.setNombre(accionReaccion.get("categoria"));
						System.out.println("Modificacion exitosa");
					}
					if(accionReaccion.get("precio") != null && product != null){
						product.setPrecio(Integer.parseInt(accionReaccion.get("precio")));
						System.out.println("Modificacion exitosa");
					}
					if(accionReaccion.get("existencias") != null && product != null){
						product.setExistencias(Integer.parseInt(accionReaccion.get("existencias")));
						System.out.println("Modificacion exitosa");
					}
				}catch(Exception err){
					System.out.println("Se ha producido un error.");
				}
			break;
			case "remove product":
				ManejadorProductos.getInstancia().removeProduct(accionReaccion.get("nombre"));
			break;
			case "show product":
				showProduct(accionReaccion.get("nombre"));
			break;
			case "show sales":
				ManejadorProductos.getInstancia().showSalesProducto(accionReaccion.get("nombre"));
			break;
			case "search downline":
				searchDown(accionReaccion.get("nick"));
			break;
			case "add ofert":
				String nombre = accionReaccion.get("producto");
				String descripcion = accionReaccion.get("descripcion");
				String inicio = accionReaccion.get("inicio");
				String fin = accionReaccion.get("fin");
				String oferta = accionReaccion.get("oferta");
				if(nombre != null && descripcion != null && inicio != null && fin != null && oferta != null){
					ManejadorProductos.getInstancia().addOfert(nombre, descripcion, inicio, fin, oferta);
				}
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
					
					super.setConsulta(campo, lista, clave, condicion, valor);
				}catch(Exception err){
					System.out.println("El error se produjo por "+err);
				}
			break;
			default:
				System.out.println(accion+", no se reconoce como un comando interno o externo.");
			break;
		}
	}
}