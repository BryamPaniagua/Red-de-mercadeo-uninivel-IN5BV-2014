package org.bryampaniagua.beans;
 
	/**
	*Esta clase guardara los datos 
	*de personales de un administrador
	*@author Bryam Paniagua[B-2012442]
	**/
 
public class beansAdministrador{
	/**
	*	Estos son los parametros de la clase beansAdministrador
	*	@param nombre ,
	*	@param nick, 
	*	@param password, 
	*	@param edad
	**/
	String nombre;
	String nick;
	String password;
	int edad;
	int cod;
		/**
		*El metodo setNombre guarda el nombre del administrador
		*@param nombre 
		**/
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	public void setCodigo(int cod){
		this.cod = cod;		
	}
	public int getCodigo(){
		return this.cod;		
	}
		/**
		*El metodo getNombre retorna el nombre del administrador
		*@return nombre 
		**/		
	public String getNombre(){
		return nombre;
	}
		/**
		*El metodo setNick recibe el nick con
		*@param nick 
		**/
	public void setNick(String nick){
		this.nick = nick;
	}
		/**
		*El metodo getNick devuelve el valor contenido en @param nick
		*@return nick 
		**/	
	public String getNick(){
		return nick;
	}	
		/**
		*El metodo setPassword recibe la contrasenia con 
		*@param password 
		**/
	public void setPassword(String password){
		this.password = password;
	}
		/**
		*El metodo getPassword devuelve el valor contenido en @param password
		*@return password 
		**/	
	public String getPassword(){
		return password;
	}
		/**
		*El metodo setEdad  recibe la edad con
		*@param edad		
		**/
	public void setEdad(int edad){
		this.edad = edad;
	}
		/**
		*El metodo getEdad devuelve el valor contenido en @param edad
		*@return edad
		**/
	public int getEdad(){
		return edad;
	}
	public beansAdministrador(int cod, String nombre, String nick, String password, int edad){
		this.cod = cod;
		this.nombre = nombre;
		this.nick = nick;
		this.password = password;
		this.edad = edad;
	}
	public beansAdministrador(){
	
	}
}