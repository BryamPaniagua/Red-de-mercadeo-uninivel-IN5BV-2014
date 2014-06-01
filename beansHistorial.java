package org.bryampaniagua.beans;

import org.bryampaniagua.beans.beansMiembro;
	/**
	*La clase beansHistorial guardara datos de miembros agregados
	**/
public class beansHistorial{
	private int cod;
	private beansMiembro downline;
	private beansMiembro upline;
	private beansMiembro contacto;
	private String fecha;
	
	public beansHistorial(){
		
	}
	/**
	*Metodo setUpline recibe un objeto con los datos del upline ingresado
	*@param upline
	**/
	public void setUpline(beansMiembro upline){
		this.upline = upline;
	}
	public void setId(int cod){
		this.cod = cod;
	}
	public int getId(){
		return this.cod;
	}
	/**
	*Metodo getUpline retorna el valor recibido y guardado por el metodo setUpline
	*@return upline
	**/
	public beansMiembro getUpline(){
		return this.upline;
	}	
	/**
	*Metodo setDate recibe la fecha de ingreso del downline
	*@param fecha
	**/
	public void setDate(String fecha){
		this.fecha = fecha;
	}
	/**
	*Metodo getDate retorna el valor recibido por setDate
	*@return fecha
	**/
	public String getDate(){
		return this.fecha;
	}
	/**
	*Metodo setDownline recibe un objeto con los datos del downline ingresado
	*@param downline
	**/
	public void setDownline(beansMiembro downline){
		this.downline = downline;
	}
	/**
	*Metodo getDownline retorna el valos recibido por el metodo setDownline
	*@return downline
	**/
	public beansMiembro getDownline(){
		return this.downline;
	}
	/**
	*Metodo setContacto recibe un objeto con los datos del miembro que ingresa al downline a la red
	*@param contacto
	**/
	public void setContacto(beansMiembro contacto){
		this.contacto = contacto;
	}
	/**
	*Metodo getContacto retorna el valor recibido por el metodo setContacto
	*@return contacto
	**/
	public beansMiembro getContacto(){
		return this.contacto;
	}
}