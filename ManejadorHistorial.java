package org.bryampaniagua.manejadores;

import java.util.Calendar;
import java.util.ArrayList;

import org.bryampaniagua.beans.beansMiembro;
import org.bryampaniagua.beans.beansHistorial;
	/**
	*Clase ManejadorHistorial guardara un registro de los miembros agregados 
	*@author Bryam Paniagua [B-2012442]
	**/
public class ManejadorHistorial{
	private static ManejadorHistorial instancia;
	private ArrayList<beansHistorial> historial;
	int cod = 1;
	/**
	*En el constructor se creara la lista que guardara los registros
	**/
	private ManejadorHistorial(){
		historial = new ArrayList<beansHistorial>();
	}
	/**
	*Metodo getInstancia devuelve una instancia si no ha sido creada antes 
	*@return instancia
	**/
	public static ManejadorHistorial getInstancia(){
		if(instancia == null)
			instancia = new ManejadorHistorial();
		return instancia;
	}
	/**
	*Metodo getLista devuelve la lista dinamica que guarda los registros
	*@return historial
	**/
	public ArrayList<beansHistorial> getLista(){
		return this.historial;
	}
	/**
	*Metodo addToList recibe un objeto con los datos de un nuevo registro y lo agrega a la lista
	*@param down
	**/
	public void addToList(beansHistorial down){
		down.setId(cod++);
		this.historial.add(down);
	}
	/**
	*Metodo show recibe un nick y muestra todos los registros que coincidan con este
	*@param nick
	**/
	public void show(String nick){
		for(beansHistorial mostrarHistorial : historial){
			if(mostrarHistorial.getContacto().getNick().equals(nick)){
				System.out.println("|------------------------");
				System.out.println("|Ingresado por:      "+mostrarHistorial.getContacto().getNick());
				System.out.println("|------------------------");
				System.out.println("|Codigo              "+mostrarHistorial.getId());
				System.out.println("|Upline              "+mostrarHistorial.getUpline().getNick());
				System.out.println("|Downline            "+mostrarHistorial.getDownline().getNick());
				System.out.println("|fecha               "+mostrarHistorial.getDate());
				System.out.println("|------------------------");
			}
		}
	}
	/**
	*Metodo extraer datos recibe al nuevo miembro, al miembro que esta arriba de el (upline) y al miembro que lo ingresa
	*Se guardan en un objeto y son enviados al metodo addToList
	*@param downline
	*@param upline
	*@param contacto
	**/
	public void extraerDatos(int cod ,beansMiembro downline , beansMiembro upline, beansMiembro contacto){
		beansHistorial registro = new beansHistorial();
		registro.setId(cod);
		registro.setUpline(upline);
		registro.setDownline(downline);
		registro.setContacto(contacto);
		String dia = Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		String mes = Integer.toString(Calendar.getInstance().get(Calendar.MONTH)+1);
		String anio = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
		registro.setDate(dia+"-"+mes+"-"+anio);
		addToList(registro);
	}
	public void mostrarDatosDeIngreso(String campo, beansHistorial registro){
		String descripcion = null, parametro = null;
		switch(campo.trim()){
			case "downline":
				descripcion = "Nombre del downline nuevo       ";
				parametro = registro.getDownline().getNombre();
			break;
			case "upline":
				descripcion = "Nombre del upline               ";
				parametro = registro.getUpline().getNombre();
			break;
			case "contacto":
				descripcion = "Nombre del contacto             ";
				parametro = registro.getContacto().getNombre();
			break;
			case "fecha":
				descripcion = "Fecha del ingreso del downline  ";
				parametro = ""+registro.getDate()+"";
			break;
			case "codigo":
				descripcion = "Codigo de registro del downline ";
				parametro = Integer.toString(registro.getId());
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
	public void mostrarMas(beansMiembro mim){
		for(beansMiembro miembro : mim.getListaInterna()){
			show(miembro.getNick());
			mostrarMas(miembro);
		}
	}
	public void mostrarDatosHist(String[] param, String clave, String condicion, String valor){
		try{
			if(historial.size() > 0){
				if(valor.indexOf("'") > -1){
					valor = valor.substring(1 , valor.length() - 1).replace('-' , ' ');
				}
				if(param[1].equals("*") && clave.equals("null") && condicion.equals("null") && valor.equals("null")){
					for(beansMiembro mim : ManejadorMiembros.getInstancia().getLista()){
						show(mim.getNick());
						mostrarMas(mim);
					}
				}else{
					System.out.println("");
					for(beansHistorial  hist : historial){
						if(param[1].equals("*")){
							param = ("codigo,downline,upline,contacto,fecha").split(",");
						}
						if(clave.equals("codigo")){
							if(true == ManejadorProductos.getInstancia().operar(Integer.parseInt(getDatosHistorial(clave, hist)) ,condicion, Integer.parseInt(valor))){
								System.out.println("-----------------------------------------");
								for(int i = 0; i < param.length ; i++){
									if(param[i].trim().length() > 3){
										mostrarDatosDeIngreso(param[i].trim(), hist);
									}
								}
								System.out.println("-----------------------------------------");
							}
						}
						if(condicion.toLowerCase().equals("like")){
							System.out.println(condicion+" "+valor+"   "+getDatosHistorial(clave, hist));
							if(getDatosHistorial(clave, hist).toLowerCase().startsWith(valor.toLowerCase())){
								System.out.println("-----------------------------------------");
								for(int i = 0; i < param.length ; i++){
									if(param[i].trim().length() > 3){
										mostrarDatosDeIngreso(param[i].trim(), hist);
									}
								}
								System.out.println("-----------------------------------------");
							}
						}
						if(clave.equals("null") && condicion.equals("null") && valor.equals("null")){
							System.out.println("-----------------------------------------");
							for(int i = 0; i < param.length ; i++){
								if(param[i].trim().length() > 3){
									mostrarDatosDeIngreso(param[i].trim(), hist);
								}
							}
							System.out.println("-----------------------------------------");
						}
					}
					System.out.println("");
				}	
			}else{
				System.out.println("No hay registros.");
			}
		}catch(Exception err){
			System.out.println(err);
		}
	}
	public String getDatosHistorial(String clave, beansHistorial hist){
		if(clave.trim().equals("downline"))
			return hist.getDownline().getNick();
		if(clave.trim().equals("upline"))
			return hist.getUpline().getNick();
		if(clave.trim().equals("contacto"))
			return hist.getContacto().getNick();
		if(clave.trim().equals("codigo"))
			return Integer.toString(hist.getId());
		if(clave.trim().equals("fecha"))
			return hist.getDate();
		return "null";
	}
}