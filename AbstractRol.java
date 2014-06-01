package org.bryampaniagua.abstracta;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.bryampaniagua.utilidades.Ingresar;
import org.bryampaniagua.utilidades.Decodificador;
import org.bryampaniagua.beans.beansMiembro;
import org.bryampaniagua.beans.beansProducto;
import org.bryampaniagua.beans.beansVentas;
import org.bryampaniagua.beans.beansOferta;
import org.bryampaniagua.manejadores.ManejadorAdministradores;
import org.bryampaniagua.manejadores.ManejadorProductos;
import org.bryampaniagua.manejadores.ManejadorMiembros;
import org.bryampaniagua.manejadores.ManejadorHistorial;
	/**
	*Clase AbstractRol contiene todos los metodos que son compartidos entre el administrador y el miembro
	*@author Bryam Paniagua[B-2012442]
	**/
public abstract class AbstractRol{
	
	private Decodificador decodificar = null;
	private	String comandos = null;
	/**
	*Metodo getDecodificador devuelve la variable recibida por el metodo setDecodificador
	*@return decodificar
	**/
	public Decodificador getDecodificador(){
		return this.decodificar;
	}
	/**
	*Metodo setDecodificador recibe una varible del mismo tipo que la clase Decodificador
	*@param decodificar
	**/
	public void setDecodificador(Decodificador decodificar){
		this.decodificar = decodificar;
	}	
	/**
	*Metodo comandos hara la entrada a la cuenta del usuario autenticado
	*No recibe parametros
	**/
	public void comandos(){
		boolean sesion;
		do{
			sesion = false;
			System.out.println(" ");
			if(ManejadorAdministradores.getInstancia().getAdmin() != null){
				System.out.print(ManejadorAdministradores.getInstancia().getAdmin().getNick()+">>");
				sesion = true;
			}else{
				ManejadorMiembros.getInstancia().comprobarEstado(ManejadorMiembros.getInstancia().getMiembro());
				if(ManejadorMiembros.getInstancia().getMiembro() != null){
					ManejadorMiembros.getInstancia().volverCero();
					System.out.print(ManejadorMiembros.getInstancia().getMiembro().getNick()+">>");
					sesion = true;
				}
			}
			if(sesion == true){
				comandos = Ingresar.leerString();
				if(comandos != null){
					this.decodificar.separarComando(comandos);
				}else{
					System.out.println("Debes escribir algo");
				}
			}
		}while(sesion == true);
	}
	/**
	*Metodo showProducts muestra todos los productos agregados en la lista
	*No recibe parametros
	**/
	public void showProducts(int rol){
		ArrayList<beansProducto> listaProductos;
		listaProductos = ManejadorProductos.getInstancia().listaProductos();
		if(listaProductos.size() >= 0){
			for(beansProducto producto : listaProductos){
				if(rol == 1){
					mostrarProducto(producto);
				}else{
					if(producto.getExistencias() > 0){
						mostrarProducto(producto);
					}
				}
			}
		}else{
			System.out.println("No hay productos ingresados");
		}
	}
	/**
	*Metodo showProduct mostrar un producto en especifico, el que coincida con el nombre recibido
	*@param nombre
	**/
	public void showProduct(String nombre){
		beansProducto search;
		search = ManejadorProductos.getInstancia().searchName(nombre);
		if(search != null){
			mostrarProducto(search);
		}
	}
	/**
	*Metodo verAdentro funciona en conjunto con el metodo listMember que muestra todos los miembros ingresados
	*Este metodo recibe un miembro y un nivel, ejecuta la lista interior del miembro recibido y 
	*muestra a los miembros que tiene adentro
	*Y lo repite hasta que no haya otro miembro dentro de uno.
	*@param miembro
	*@param nivel
	
	**/
	public static void verAdentro(beansMiembro miembro, int nivel){
		for(beansMiembro interiorMiembro : miembro.getListaInterna()){
			mostrarMiembro(interiorMiembro, nivel);
			verAdentro(interiorMiembro, nivel+1);
		}
	}
	/**
	*Metodo listMember empieza los miembros de la lista principal
	*Y envia a cada uno de estos al metodo verAdentro
	*No recibe parametros
	**/
	public static void listMembers(){
		ArrayList<beansMiembro> listaMembers = null;
		listaMembers =  ManejadorMiembros.getInstancia().getLista();
		if(listaMembers.size() >= 0){
			for(beansMiembro miembro : listaMembers){
				mostrarMiembro(miembro, 0);
				verAdentro(miembro,1);
			}
		}else{
			System.out.println("No hay miembros ingresados.");
		}
	}
	/**
	*Metodo searchDown recibe un nick y lo envia al metodo search1 a ManejadorMiembros
	**/
	public void searchDown(String nick){
		ManejadorMiembros.getInstancia().search1(nick);
	}	
	/**
	*Metodo mostrarProducto recibe un objeto que guarda los datos de un producto y muestra los datos 
	*@param producto
	**/
	public void mostrarProducto(beansProducto producto){
		mostrarDatos(producto);
		String tab = getLines(1);
		if(producto.getListaDeOfertas().size() >= 0){
			for(beansOferta oferta : producto.getListaDeOfertas()){
				int[] fecha = getFechas(oferta);
				if(fecha[0] <= fecha[1] && fecha[1] <= fecha[2]){
				System.out.println(" ");	
				System.out.println(tab+"-------------------------------------------------");
				System.out.println(tab+"Codigo de la oferta "+producto.getNombre()+"OFERT-00"+Integer.toString(oferta.getId()));
				System.out.println(tab+"-------------------------------------------------");
				System.out.println(tab+"APROVECHA PARA COMPRAR!!");
				System.out.println(tab+"Descripcion          "+oferta.getDescripcion());
				System.out.println(tab+"Inicio de la oferta  "+oferta.getFechaDeInicio());
				System.out.println(tab+"Fin de la oferta     "+oferta.getFechaDeFinalizacion());
				System.out.println(tab+"-------------------------------------------------");
				}
			}
		}
	}
	int fin = 0, inicio = 0, actual = 0;
	public int[] getFechas(beansOferta oferta){
		int mesActual = (Calendar.getInstance().get(Calendar.MONTH)+1);
		int diaActual = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		int anioActual = Calendar.getInstance().get(Calendar.YEAR);
		
		String fechaInicio[] = oferta.getFechaDeInicio().split("-");
		int diaInicio = Integer.parseInt(fechaInicio[0]);
		int mesInicio = Integer.parseInt(fechaInicio[1]);
		int anioInicio = Integer.parseInt(fechaInicio[2]);
		
		String fechaFin[] = oferta.getFechaDeFinalizacion().split("-");
		int diaFin = Integer.parseInt(fechaFin[0]);
		int mesFin = Integer.parseInt(fechaFin[0]);
		int anioFin = Integer.parseInt(fechaFin[2]);
		
		int fechas[] = new  int[3]; 
		
		fechas[0] = (diaInicio + mesInicio + anioInicio);
		fechas[1] = (diaActual + mesActual + anioActual);
		fechas[2] = (diaFin + mesFin + anioFin);
		
		return fechas;
	}
	public void mostrarDatos(beansProducto producto){
		System.out.println(" ");	
		System.out.println("----------------------------");	
		System.out.println("Codigo           "+producto.getId());
		System.out.println("----------------------------");
		System.out.println("Nombre           "+producto.getNombre());
		System.out.println("Categoria        "+producto.getCategoria());
		System.out.println("Precio           "+"Q."+producto.getPrecio()+".00");
		System.out.println("Existencias      "+producto.getExistencias());
		System.out.println("----------------------------");	
	}
	/**
	*Metodo getLines recibe un nivel y revuelve una variable con el numero de espacios segun el nivel
	*@param nivel
	*	@return tab
	**/
	public static String getLines(int nivel){
		String tab = "";
		for(int i = 0; i < nivel ; i++){
			tab += "     ";
		}
		return tab;
	}
	/**
	*Metodo mostrarMiembro recibe un objeto con los datos de un miembro y un nivel 
	*y los muestra con la sangria recibida del metodo getLines	
	*@param member
	*@param nivel
	**/
	public static void mostrarMiembro(beansMiembro member, int nivel){
		String tab = getLines(nivel);
		System.out.println(tab+"----------------------------------");
		System.out.println(tab+"Nick                   | "+member.getNick());
		System.out.println(tab+"----------------------------------");
		System.out.println(tab+"Codigo                 | "+member.getCod());
		System.out.println(tab+"Nombre                 | "+member.getNombre());
		System.out.println(tab+"Edad                   | "+member.getEdad());
		System.out.println(tab+"Tarjeta Credito        | "+member.getTarjeta());
		System.out.println(tab+"----------------------------------");		
	}
	/**
	*Metodo showStatusInterno recibe un miembro y un nivel 
	*Muestra el estado monetario de los miembros dentro de el y repite el proceso
	*@param miembro
	*@param nivel
	**/
	public void showStatusInterno(beansMiembro miembro, int nivel){
		for(beansMiembro status2 : miembro.getListaInterna()){
			ManejadorMiembros.getInstancia().showMeMoney(status2,nivel+1);
			showStatusInterno( status2, nivel+1);
		}
	}
	/**
	*Metodo muestra el estado monetario de los miembros principales y luego envia el miembro al metodo 
	*showStatusInterno para mostrar su interior
	*No recibe parametros
	**/
	public void showStatus(){
		ManejadorMiembros.getInstancia().payMembers();
		for(beansMiembro status : ManejadorMiembros.getInstancia().getLista()){
			ManejadorMiembros.getInstancia().showMeMoney(status,1);
			showStatusInterno(status, 1);
		}
	}
	public void mostrar(){
		
	}
	public void setConsulta(String[] param, String tabla, String clave, String condicion, String valor){
		switch(tabla){
			case "miembros":
				ManejadorMiembros.getInstancia().mostrarDatosDownline(param, clave, condicion, valor);
			break;
			case "productos":
				ManejadorProductos.getInstancia().mostrarDatoDelProducto(param, clave, condicion, valor);
			break;
			case "ventas":
				ManejadorProductos.getInstancia().mostrarDatosDeVentas(param, clave, condicion, valor);
			break;
			case "administradores":
				ManejadorAdministradores.getInstancia().mostraDatosAdministradores(param, clave, condicion, valor);
			break;
			case "historial":
				ManejadorHistorial.getInstancia().mostrarDatosHist(param, clave, condicion, valor);
			break;
			default:
				System.out.println("No es valida la lista");
			break;
		}
		
	}
}
		