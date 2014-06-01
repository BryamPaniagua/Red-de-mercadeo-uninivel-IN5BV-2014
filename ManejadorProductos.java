package org.bryampaniagua.manejadores;

import java.util.ArrayList;
import java.util.Calendar;

import org.bryampaniagua.beans.beansProducto;
import org.bryampaniagua.beans.beansVentas;
import org.bryampaniagua.manejadores.ManejadorMiembros;
import org.bryampaniagua.abstracta.AbstractRol;
import org.bryampaniagua.abstracta.appMiembro;
import org.bryampaniagua.utilidades.Decodificador;
import org.bryampaniagua.beans.beansOferta;
	/**
	*Clase ManejadorProductos contiene los procesos de los productos
	*@author Bryam Paniagua[B-2012442]
	 **/
public class ManejadorProductos{
	ArrayList<beansProducto> productos;
	ArrayList<beansVentas> ventas;
	beansProducto search;
	AbstractRol vars = null;
	static int cod = 1;
	
	private static ManejadorProductos instancia;
	/**
	*En el constructor se crea la lista que guardara los objetos con los datos de un nuevo producto
	*Y hace el ingreso de tres productos por defecto
	**/
	private ManejadorProductos(){
		vars = new appMiembro(new Decodificador());
		ventas = new ArrayList<beansVentas>();
		
		productos = new ArrayList<beansProducto>();
		
		productos.add(new beansProducto(1,"Televisor","Electrodomesticos",2500,25));
		productos.add(new beansProducto(2,"Lavadora","Linea Blanca",3000,40));
		productos.add(new beansProducto(3,"Computadora","Tecnologia",3000,40));
		productos.add(new beansProducto(4,"SmartPhone","SmatrPhones",5000,200));
	}
	/**
	*Metodo listaProductos devuelve la lista que contiene los productos
	*@return productos
	**/
	public ArrayList<beansProducto> listaProductos(){
		return this.productos;
	}
	/**
	*Metodo getInstancia devuelve una instancia
	*Se aplica SINGLETON
	*@return instancia
	**/
	public static ManejadorProductos getInstancia(){
		if(instancia == null)
			instancia = new ManejadorProductos();
		return instancia;
	}
	/**
	*Metodo addProduct recibe un objeto con los datos de un nuevo producto y lo agrega a la lista
	*@param producto
	**/
	public void addProduct(beansProducto producto){
		int codigo = 4;
		producto.setId(codigo+1);
		productos.add(producto);
	}
	/**
	*Metodo searchName recibe un nombre y lo busca en la lista y lo devuelve si la busqueda es exitosa
	*@return producto
	**/
	public beansProducto searchName(String nombre){
		for(beansProducto producto : productos){
			if(producto.getNombre().equals(nombre)){
				return producto;
			}
		}
		System.out.println("No se encuentra este producto.");
		return null;
	}
	/**
	*Metodo removeProduct recibe un nombre, hace la busqueda y si es exitosa, borra el producto de la lista
	*@param nombre
	**/
	public void removeProduct(String nombre){
		search = searchName(nombre);
		if(search != null){
			productos.remove(search);
			System.out.println("Se ha borrado exitosamente");
		}	
	}
	/**
	*Metodo buyProduct recibe un nombre y una cantidad hace la busqueda, manda los datos para el registro y tambien suma 
	*en la cuenta de egresos del miembro
	*@param nombre
	*@param cantidad
	**/
	public void buyProduct(String nombre, int cantidad){
		beansVentas compras = new beansVentas();
		
		int descuento = 0, totalConDescuento = 0, cantidadProductosAdicional = 0;
		if(productos.size() >= 0){
			search = searchName(nombre);
			if(search != null){
				if(cantidad <= search.getExistencias()){
				
					int total = search.getPrecio() * cantidad;
					try{
						if(search.getListaDeOfertas().size() > 0){
							for(beansOferta oferta : search.getListaDeOfertas()){
								int fecha[] = vars.getFechas(oferta);
								if(fecha[0] <= fecha[1] && fecha[1] >= fecha[2]){
									if(oferta.getAccion().equals("Descuento")){
										descuento = oferta.getDescuento();
										totalConDescuento = (total * descuento) / 100 ;
										total = total - totalConDescuento;
										compras.setAccion("Descuento");
										compras.setDescripcion(oferta.getDescripcion());
									}else{
										String ofer[] = oferta.getOferta().split("x");
										int bonif = Integer.parseInt(ofer[1]);
										int prod = Integer.parseInt(ofer[0]);
										
										if(prod <= cantidad && cantidad < (bonif*cantidad)){
											cantidadProductosAdicional =(cantidad / prod)*(bonif - prod);
											
											compras.setAccion("Oferta");
											compras.setProductoAdicional(cantidadProductosAdicional + cantidad);
											compras.setDescripcion(oferta.getDescripcion());
										}							
									}
								}
							}
						}else{
							compras.setAccion("Normal");
						}
						
						
						long gastos = ManejadorMiembros.getInstancia().getMiembro().getEgresos() + Long.parseLong(""+total+"");
						search.setExistencias(search.getExistencias() - (cantidad + cantidadProductosAdicional));
						ManejadorMiembros.getInstancia().getMiembro().setEgresos(gastos);
						
						System.out.println("");
						System.out.println("Su compra fue un exito. Se le cargara a la tarjeta.");
						System.out.println("---------------------------------");
						System.out.println("Nombre           "+search.getNombre());
						System.out.println("Precio           "+"Q."+search.getPrecio()+".00");
						System.out.println("Cantidad         "+cantidad+" "+search.getNombre()+"(s)");
						System.out.println("Existencias      "+search.getExistencias());
						System.out.println("---------------------------------");
						if(compras.getAccion().equals("Descuento")){
							System.out.println("Descuento         "+"Q."+totalConDescuento+".00");         
							System.out.println("Se le aplicara el "+descuento+"% de descuento");
							System.out.println("Total             "+"Q."+total+".00");
							System.out.println("---------------------------------");
							
							compras.setDescuento(descuento);
							compras.setTotalSinDescuento(total);
						}else if(compras.getAccion().equals("Oferta")){
							System.out.println("Total            "+"Q."+total+".00");
							System.out.println("Recibira "+(cantidad + cantidadProductosAdicional)+" "+search.getNombre()+"(s) por la oferta");
							System.out.println("---------------------------------");
						}else if(compras.getAccion().equals("Normal")){
							System.out.println("Total            "+"Q."+total+".00");
							System.out.println("---------------------------------");
						}
						compras.setNick(ManejadorMiembros.getInstancia().getMiembro().getNick());
						compras.setNombre(search.getNombre());
						compras.setCantidad(cantidad);
						compras.setPrecio(search.getPrecio());
						compras.setTotal(total);
						compras.setId(cod++);
						String dia = Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
						String mes = Integer.toString(Calendar.getInstance().get(Calendar.MONTH)+1);
						String anio = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
						compras.setFecha(dia+"-"+mes+"-"+anio);
						ventas.add(compras);
					
					}catch(Exception err){
						System.out.println("No se ha podido realizar la compra."+err);
					}
				}else{
					System.out.println("No hay productos a la venta.");
				}
			}else{
				System.out.println("Verifique el nombre.");
				vars.showProducts(2);
			}	
		}
	}
	/**
	*Metodo getListaVentas devuelve la lista de ventas realizadas
	*@return ventas
	**/
	public ArrayList<beansVentas> getListaVentas(){
		return this.ventas;
	}
	/**
	*Metodo showSales muestra todas las ventas realizadas
	*No recibe parametros
	**/
	public void mostrarDatos(beansVentas registros){
		if(registros.getAccion().equals("Descuento")){
			System.out.println("Descripcion         "+registros.getDescripcion());
			System.out.println("Descuento           "+registros.getDescuento()+"%");
			System.out.println("TotalSinDescuento   "+registros.getTotalSinDescuento());
			System.out.println("------------------------------------------");
		}else if(registros.getAccion().equals("Oferta")){
			System.out.println("Oferta              "+registros.getDescripcion());
			System.out.println("ProductoTotal       "+registros.getProductoAdicional());
			System.out.println("------------------------------------------");
		}
		System.out.println("Total               Q."+registros.getTotal()+".00");
		System.out.println("------------------------------------------");
	}	
	public void showSales(){
		if(ventas.size() > 0){
			for(beansVentas registros : ventas){
				System.out.println("");
				System.out.println("------------------------------------------");
				System.out.println("Nick                "+registros.getNick());
				System.out.println("------------------------------------------");
				System.out.println("Codigo              "+registros.getId());
				System.out.println("Producto            "+registros.getNombre());
				System.out.println("Cantidad            "+registros.getCantidad());
				System.out.println("Precio            Q."+registros.getPrecio()+".00");
				System.out.println("Fecha               "+registros.getFecha());
				System.out.println("------------------------------------------");
				mostrarDatos(registros);
			}
		}else{
			System.out.println("No hay ventas registradas.");
		}
	}
	/**
	*Metodo showSales recibe un nick y muestra todas las ventas que coincidan con el nick del comprador
	*@param nick
	**/
	public void showSales(String nick){
		if(ManejadorProductos.getInstancia().getListaVentas().size() > 0){
			for(beansVentas registros : ManejadorProductos.getInstancia().getListaVentas()){
				if(registros.getNick().equals(nick)){
					System.out.println("");
					System.out.println("------------------------------------------");
					System.out.println("Nick                "+registros.getNick());
					System.out.println("------------------------------------------");
					System.out.println("Codigo              "+registros.getId());
					System.out.println("Producto            "+registros.getNombre());
					System.out.println("Cantidad            "+registros.getCantidad());
					System.out.println("Fecha               "+registros.getFecha());
					System.out.println("------------------------------------------");
					mostrarDatos(registros);
				}
			}
		}else{
			System.out.println("No hay registros.");
		}
	}
	/**
	*Metodo showSalesProducto recibe un nombre de un producto y muestra todas las coincidencias
	*@param nombre
	**/
	public void showSalesProducto(String nombre){
		if(ManejadorProductos.getInstancia().getListaVentas().size() > 0){
			for(beansVentas registros : ManejadorProductos.getInstancia().getListaVentas()){
				if(registros.getNombre().equals(nombre)){
					System.out.println("");
					System.out.println("------------------------------------------");
					System.out.println("Nick                "+registros.getNick());
					System.out.println("------------------------------------------");
					System.out.println("Codigo              "+registros.getId());
					System.out.println("Producto            "+registros.getNombre());
					System.out.println("Cantidad            "+registros.getCantidad());
					System.out.println("Fecha               "+registros.getFecha());
					System.out.println("------------------------------------------");
					mostrarDatos(registros);
				}
			}
		}else{
			System.out.println("No tiene registros.");
		}
	}
	public boolean buscarCoincidenciasEnOferta(beansProducto prod, String descripcion, String inicio, String accion){
		boolean repetido = false;
		for(beansOferta oferta : prod.getListaDeOfertas()){
			if(descripcion.equals(oferta.getDescripcion()) && inicio.equals(oferta.getFechaDeInicio()) && accion.equals(oferta.getAccion())){
				repetido = true;
			}
		}
		if(repetido == true)
			return true;
		return false;
	}
	public void addOfert(String nombre, String descripcion, String inicio, String fin, String accion){
		search = searchName(nombre);
		int idOferta = 1;
		if(search != null){
			try{	
				if(buscarCoincidenciasEnOferta(search, descripcion, inicio, accion) != true){
					String fechaInicio[] = inicio.replace('-', ' ').split(" ");
					int anioInicio = Integer.parseInt(fechaInicio[2]);
					int mesInicio = Integer.parseInt(fechaInicio[1]);
					int diaInicio = Integer.parseInt(fechaInicio[0]);
					
					String fechaFin[] = fin.replace('-', ' ').split(" ");
					int anioFin = Integer.parseInt(fechaFin[2]);
					int mesFin = Integer.parseInt(fechaFin[1]);
					int diaFin = Integer.parseInt(fechaFin[0]);
					int porcentaje = 0, bonificacion = 0, producto = 0;
					String ofertaODescuento = "Nada";
					if(accion.indexOf("%") >= 0){
						String porcentajeString = accion.trim().substring(0, accion.trim().indexOf("%"));
						porcentaje = Integer.parseInt(porcentajeString);
						ofertaODescuento = "Des";
					}else{
						if(accion.indexOf("*") > -1){
							accion = accion.replace('*', 'x');
						}else if(accion.indexOf("X") > -1){
							accion = accion.replace('X', 'x');
						}
						String oferton[] = accion.trim().split("x");
						producto = Integer.parseInt(oferton[0]);
						bonificacion = Integer.parseInt(oferton[1]);
						ofertaODescuento = "Of";
					}
					if(mesInicio <= mesFin && anioInicio <= anioFin && false == (ofertaODescuento.equals("Nada"))){
						beansOferta oferta = new beansOferta();
						oferta.setId(idOferta+1);
						oferta.setDescripcion(descripcion);
						oferta.setFechaDeInicio(""+diaInicio+"-"+mesInicio+"-"+anioInicio+"");
						oferta.setFechaDeFinalizacion(""+diaFin+"-"+mesFin+"-"+anioFin+"");
						if(ofertaODescuento.equals("Des")){
							oferta.setDescuento(porcentaje);
							oferta.setAccion("Descuento");
						}else if(ofertaODescuento.equals("Of")){
							oferta.setOferta(""+bonificacion+"x"+producto+"");
							oferta.setAccion("Oferta");
						}
						
						search.getListaDeOfertas().add(oferta);
						System.out.println("Se ha ingresado exitosamente");
					}else{
						System.out.println("Escriba bien los datos.");
					}
				}
			}catch(Exception err){
				System.out.println("No se ha podido ingresar la oferta. "+err);
			}
		}else{
			System.out.println("Este producto no se encuentra.");
		}
	}
	public String getCampoProducto(String clave, beansProducto prod){
		if(clave.trim().equals("nombre"))
			return prod.getNombre();
		if(clave.trim().equals( "precio"))
			return Integer.toString(prod.getPrecio());
		if(clave.trim().equals("categoria"))
			return prod.getCategoria();
		if(clave.trim().equals("existencias"))
			return Integer.toString(prod.getExistencias());
		if(clave.trim().equals("codigo"))
			return Integer.toString(prod.getId());
		return "null";
	}
	public boolean operar(int var, String cond, int var2){
		if(cond.equals("<") && var < var2)
			return true;
		if(cond.equals(">") && var > var2)
			return true;
		if(cond.equals(">=") && var >= var2)
			return true;
		if(cond.equals("=") && var == var2)
			return true;
		if(cond.equals("<=") && var <= var2)
			return true;
		return false;
	}
	public void mostrarDatoDelProducto(String[] param, String clave, String condicion, String valor){
		try{
			if(valor.indexOf("'") > -1){
				valor = valor.substring(1 , valor.length() - 1).replace('-' , ' ');
			}
			if(param[1].equals("*")){
				vars.showProducts(2);
			}else{
				System.out.println("");
				for(beansProducto prod : productos){
					if(param[1].equals("*")){
							param = ("codigo,nombre,categoria,precio,existencias").split(",");
					}
					if(clave.equals("existencias") || clave.equals("codigo") || clave.equals("precio")){
						if(true == operar(Integer.parseInt(getCampoProducto(clave, prod)) ,condicion, Integer.parseInt(valor))){
							System.out.println("-----------------------------------------");
							for(int i = 1; i < param.length ; i++){
								if(param[i].trim().length() > 4){
									getDatos(param[i].trim(), prod);
								}
							}
							System.out.println("-----------------------------------------");
						}
					}
					if(condicion.toLowerCase().equals("like")){
						if(getCampoProducto(clave, prod).toLowerCase().startsWith(valor.toLowerCase())){
							System.out.println("-----------------------------------------");
							for(int i = 1; i < param.length ; i++){
								getDatos(param[i], prod);
							}
							System.out.println("-----------------------------------------");
						}
					}
					if(clave.equals("null") && condicion.equals("null") && valor.equals("null")){
							System.out.println("-----------------------------------------");
							for(int i = 1; i < param.length ; i++){
								getDatos(param[i].trim(), prod);
							}
							System.out.println("-----------------------------------------");
					}
				}
				System.out.println("");
			}	
		}catch(Exception err){
			System.out.println(err);
		}
	}
	public void getDatos(String campo, beansProducto producto){
		String descripcion = null, parametro = null;
		switch(campo.trim()){
			case "nombre":
				descripcion = "Nombre del producto         ";
				parametro = producto.getNombre();
			break;
			case "codigo":
				descripcion = "Codigo del producto         ";
				parametro = Integer.toString(producto.getId());
			break;
			case "precio":
				descripcion = "Precio unitario             ";
				parametro = "Q."+producto.getPrecio()+".00";
			break;
			case "existencias":
				descripcion = "Existencias del producto    ";
				parametro = producto.getExistencias()+" "+producto.getNombre()+"(s)";
			break;
			case "categoria":
				descripcion = "Categoria del producto      ";
				parametro = producto.getCategoria();
			break;
			default:
				descripcion = "null";
				parametro = "null";
			break;
		}
		if(true != (descripcion.equals("null")) && true != (parametro.equals("null"))){
			System.out.println(descripcion + parametro);
		}
	}
	public void mostrarDatoDeVenta(String campo, beansVentas venta){
		String descripcion = null, parametro = null;
		switch(campo.trim()){
			case "nombre":
				descripcion = "Nombre del producto ";
				parametro = venta.getNombre();
			break;
			case "nick":
				descripcion = "Comprado por        ";
				parametro = venta.getNick();
			break;
			case "codigo":
				descripcion = "Codigo              ";
				parametro = Integer.toString(venta.getId());
			break;
			case "cantidad": 
				descripcion = "Compro               ";
				parametro = venta.getCantidad()+" "+venta.getNombre()+"(s)";
			break;
			case "precio":
				descripcion = "Precio unitario     ";
				parametro = "Q."+venta.getPrecio()+".00";
			break;
			case "fecha":
				descripcion = "Fecha de la venta   ";
				parametro = venta.getFecha();
			break;
			case "total":
				descripcion = "Total de la venta   ";
				parametro = "Q."+venta.getTotal()+".00";
			break;
			default:
				descripcion = "null";
				parametro = "null";
			break;
		}
		if(true != (descripcion.equals("null")) && true != (parametro.equals("null"))){
			System.out.println(descripcion + parametro);
		}
	}
	public void mostrarDatosDeVentas(String[] param, String clave, String condicion, String valor){
		try{
			if(ventas.size() > 0){
				if(valor.indexOf("'") > -1){
					valor = valor.substring(1 , valor.length() - 1).replace('-' , ' ');
				}
				if(param[1].equals("*") && clave.equals("null") && condicion.equals("null") && valor.equals("null")){
					showSales();
				}else{
					System.out.println("");
					for(beansVentas  venta : ventas){
						if(param[1].equals("*")){
							param = ("codigo,nombre,nick,cantidad,precio,fecha,total").split(",");
						}
						if(clave.equals("cantidad") || clave.equals("codigo") || clave.equals("precio") || clave.equals("total")){
							if(true == operar(Integer.parseInt(getCampoVenta(clave, venta)) ,condicion, Integer.parseInt(valor))){
								System.out.println("-----------------------------------------");
								for(int i = 1; i < param.length ; i++){
									if(param[i].trim().length() > 3){
										mostrarDatoDeVenta(param[i].trim(), venta);
									}
								}
								System.out.println("-----------------------------------------");
							}
						}
						if(condicion.toLowerCase().equals("like")){
							if(getCampoVenta(clave, venta).toLowerCase().startsWith(valor.toLowerCase())){
								System.out.println("-----------------------------------------");
								for(int i = 1; i < param.length ; i++){
									if(param[i].trim().length() > 3){
										mostrarDatoDeVenta(param[i].trim(), venta);
									}
								}
								System.out.println("-----------------------------------------");
							}
						}
						if(clave.equals("null") && condicion.equals("null") && valor.equals("null")){
							System.out.println("-----------------------------------------");
							for(int i = 1; i < param.length ; i++){
								if(param[i].trim().length() > 3){
									mostrarDatoDeVenta(param[i].trim(), venta);
								}
							}
							System.out.println("-----------------------------------------");
						}
						if(true != (venta.getAccion().equals("Normal"))){
							if(venta.getAccion().equals("Descuento")){
								System.out.println("Se le aplico el descuento de "+Integer.toString(venta.getDescuento())+"%");
							}else{
								System.out.println("Se le aplico el descuento de "+Integer.toString(venta.getProductoAdicional())+"%");
							}
							System.out.println("Descripcion de la oferta     "+venta.getDescripcion());
						}
					}
					System.out.println("");
				}	
			}else{
				System.out.println("No hay registros");
			}
		}catch(Exception err){
			System.out.println(err);
		}
	}
	public String getCampoVenta(String clave, beansVentas venta){
		if(clave.trim().equals("nombre"))
			return venta.getNombre();
		if(clave.trim().equals("nick"))
			return venta.getNick();
		if(clave.trim().equals( "precio"))
			return Integer.toString(venta.getPrecio());
		if(clave.trim().equals("fecha"))
			return venta.getFecha();
		if(clave.trim().equals("cantidad"))
			return Integer.toString(venta.getCantidad());
		if(clave.trim().equals("codigo"))
			return Integer.toString(venta.getId());
		if(clave.trim().equals("total"))
			return Integer.toString(venta.getTotal());
		return "null";
	}
}