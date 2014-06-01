package org.bryampaniagua.manejadores;

import java.util.ArrayList;

import org.bryampaniagua.beans.beansAdministrador;
import org.bryampaniagua.beans.beansMiembro;
import org.bryampaniagua.sistema.Login;
	/**
	*Clase ManejadorAdministradores sera la encargada de los procesos propios de un administrador
	*@author Bryam Paniagua[B-2012442]
	**/
public class ManejadorAdministradores{
	
	private ArrayList<beansAdministrador> admins ;
	private beansAdministrador autenticado = null;
	
	private static ManejadorAdministradores instancia;	
	/**
	*Metodo getInstancia devuelve una instancia hacia el manejador si no se ha hecho una antes sino devuelve la misma
	*Aqui se aplica SINGLETON
	*@return instancia
	**/
	public static ManejadorAdministradores getInstancia(){
		if(instancia == null)
			instancia = new ManejadorAdministradores();
		 return instancia;
	}	
	/**
	*En el constructor se crea la lista dinamica que sera el contenedor de los objetos que guardan los datos de los administradores 
	*y hace el ingreso por defecto de tres de ellos
	**/
	private ManejadorAdministradores(){
		admins = new ArrayList<beansAdministrador>();
		
		admins.add(new beansAdministrador(1,"Bryam", "bryam", "paniagua", 18));
		admins.add(new beansAdministrador(2,"Hermes", "hermes", "sotoj", 19));
		admins.add(new beansAdministrador(3,"Administrador", "admin", "admin", 0));
	}
	/**
	*Metodo addAdmin recibe un objeto con los datos de un administrador y los ingresa a la lista
	*@param admin
	**/
	public void addAdmin(beansAdministrador admin){
		int cod = 4;
		admin.setCodigo(cod + 1);
		admins.add(admin);
	}
	/**
	*Metodo buscarAdmin recibe el nick de un administrador y lo busca en la lista y devuelve el objeto si lo encuentra
	*@param nick
	*	@return admin
	**/
	public beansAdministrador buscarAdmin(String nick){
		for(beansAdministrador admin : admins){
			if(admin.getNick().equals(nick))
				return admin;
		}
		return null;
	}
	/**
	*Metodo comprobarAdmin recibe un nick y una contrasenia para compararlas con cada uno de los administradores ingresados
	*Y devuelve un booleano si lo encuentra o no
	*@param nick
	*@param password 
	*	@return boolean
	**/
	public boolean comprobarAdmin(String nick, String password){
		beansAdministrador admin = buscarAdmin(nick);
		if(admin != null){
			if(admin.getPassword().equals(password)){
				setAdmin(admin);
				return true;
			}
		}
		return false;
	}
	/**
	*Metodo removerAdmin recibe un nick y pide la contrasenia como permiso para borrar al administrador y lo envia a hacer la busqueda
	*@param nick
	**/
	public void removerAdmin(String nick){
		try{
			if(getAdmin().getPassword().equals(Login.getPasswordOculta())){
				admins.remove(buscarAdmin(nick));
				System.out.println("Se ha borrado exitosamente.");
			}			
		}catch(Exception err){
			System.out.println("No se ha encontrado.");
		}
	}
	/**
	*Metodo setAdmin recibe al administrador que se ha iniciado sesion
	*@param autenitcado
	**/
	public void setAdmin(beansAdministrador autenticado){
		this.autenticado = autenticado;
	}
	/**
	*Metodo getAdmin devuelve el administrador que esta en sesion
	*@return autenticado
	*/
	public beansAdministrador getAdmin(){
		return autenticado;
	}
	/**
	*Metodo desautenticarAdmin vuelve nula la autenticacion, no recibe parametros
	**/
	public void desautenticarAdmin(){
		System.out.println(" ");
		System.out.println("Ha cerrado sesion.");
		System.out.println(" ");
		setAdmin(null);
	}
	/**
	*Metodo showMe muestra los datos del administrador en sesion, no recibe parametros
	**/
	public void showMe(){
		if(getAdmin() != null){
			System.out.println("Tus datos son:");
			System.out.println("----------------------------------");
			System.out.println("Nick          -->    "+autenticado.getNick());
			System.out.println("----------------------------------");
			System.out.println("Nombre        -->    "+autenticado.getNombre());
			System.out.println("Rol           -->    Administrador");
			System.out.println("Edad          -->    "+autenticado.getEdad());
			System.out.println("----------------------------------");
		}
	}
	/**
	*Metodo mostrarAdmins muestra a todos los administradores agregados
	*No recibe parametros
	**/
	public void mostrarAdmins(){
		if(admins.size() > 0){
			for(beansAdministrador admin : admins){
				System.out.println("----------------------------------");
				System.out.println("Nombre        -->    "+admin.getNombre());
				System.out.println("Nick          -->    "+admin.getNick());
				System.out.println("Edad          -->    "+admin.getEdad());
				System.out.println("----------------------------------");			
			}
		}else{
			System.out.println("No hay usuarios ingresados.");
		}
	}
	public void mostrarDatosAdmin(String campo, beansAdministrador admin){
		String descripcion = null, parametro = null;
		switch(campo){
			case "codigo":
				descripcion = "Codigo del downline      ";
				parametro = Integer.toString(admin.getCodigo());
			break;
			case "nombre":
				descripcion = "Nombre del downline      ";
				parametro = admin.getNombre();
			break;
			case "nick":
				descripcion = "Nick del donwline        ";
				parametro = admin.getNick();
			break;
			case "password":
				System.out.println("No se puede mostrar la contrasenia");
				descripcion = "null";
				parametro = "null";
			break;
			case "edad":
				descripcion = "Edad del downline     ";
				parametro = Integer.toString(admin.getEdad());			
			break;
		}
		if(true != (descripcion.equals("null")) && true != (parametro.equals("null"))){
			System.out.println(descripcion + parametro);
		}
	}
	public void mostraDatosAdministradores(String[] param, String clave, String condicion, String valor){
		try{
			if(valor.indexOf("'") > -1){
				valor = valor.substring(1 , valor.length() - 1).replace('-' , ' ');
			}
			if(param[1].equals("*") && clave.equals("null") && condicion.equals("null") && valor.equals("null")){
				mostrarAdmins();
			}else{
				System.out.println("");
				for(beansAdministrador  adm : admins){
					if(param[1].equals("*")){
						param = ("codigo,nombre,nick,edad").split(",");
					}
					if(clave.equals("edad") || clave.equals("codigo")){
						if(true == ManejadorProductos.getInstancia().operar(Integer.parseInt(getDatosAdmin(clave, adm)) ,condicion, Integer.parseInt(valor))){
							System.out.println("-----------------------------------------");
							for(int i = 1; i < param.length ; i++){
								if(param[i].trim().length() > 3){
									mostrarDatosAdmin(param[i].trim(), adm);
								}
							}
							System.out.println("-----------------------------------------");
						}
					}
					if(condicion.toLowerCase().equals("like")){
						if(getDatosAdmin(clave, adm).toLowerCase().startsWith(valor.toLowerCase())){
							System.out.println("-----------------------------------------");
							for(int i = 1; i < param.length ; i++){
								if(param[i].trim().length() > 3){
									mostrarDatosAdmin(param[i].trim(), adm);
								}
							}
							System.out.println("-----------------------------------------");
						}
					}
					if(clave.equals("null") && condicion.equals("null") && valor.equals("null")){
						System.out.println("-----------------------------------------");
						for(int i = 1; i < param.length ; i++){
							if(param[i].trim().length() > 3){
								mostrarDatosAdmin(param[i].trim(), adm);
							}
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
	public String getDatosAdmin(String clave, beansAdministrador admin){
		if(clave.trim().equals("nombre"))
			return admin.getNombre();
		if(clave.trim().equals("nick"))
			return admin.getNick();
		if(clave.trim().equals("edad"))
			return Integer.toString(admin.getEdad());
		if(clave.trim().equals("codigo"))
			return Integer.toString(admin.getCodigo());
		return "null";
	}
}