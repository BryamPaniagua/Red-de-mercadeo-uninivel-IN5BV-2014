package org.bryampaniagua.beans;

import java.util.ArrayList;
	/**
	*Clase beansMiembro guardara los datos necesarios para el ingreso de un miembro, ademas hereda los metodo de beansAdministrador
	*@author Bryam Paniagua[B-2012442]
	**/
public class beansMiembro extends beansAdministrador{
	private int cod;
	private int pin = 0;
	private String estado = null;
	private long tarjeta = 0;
	private long ingresos = 0;
	private long egresos = 0;
	private ArrayList<beansMiembro> list;
	/**
	*En el constructor se envia una lista dinamica al metodo setListaInterna
	**/
	public beansMiembro(){
		setListaInterna(new ArrayList<beansMiembro>());
	}
	/**
	*Metodo setPin recibe el pin del miembro
	*@param pin
	**/
	public void setPin(int pin){
		this.pin = pin;		
	}
	public void setCod(int cod){
		this.cod = cod;		
	}
	public int getCod(){
		return cod;		
	}
	/**
	*Metodo getPin devuelve el pin del miembro
	*@return pin
	**/
	public int getPin(){
		return pin;
	}
	/**
	*Metodo setEstado recibe el situacion de ingreso del miembro puede ser nuevo, viejo, actiov o inactivo
	*@param estado
	**/
	public void setEstado(String estado){
		this.estado = estado;
	}
	/**
	*Metodo getEstado devuelve el estado del miembro
	*@return estado
	**/
	public String getEstado(){
		return estado;
	}
	/**
	*Metodo setTarjeta recibe el numero de tarjeta de credito del miembro
	*@param tarjeta
	**/
	public void setTarjeta(long tarjeta){
		this.tarjeta = tarjeta;
	}
	/**
	*Metodo getTarjeta devuelve el numero de tarjeta de credito del miembro
	*@return tarjeta
	**/
	public long getTarjeta(){
		return tarjeta;
	}
	/**
	*Metodo setIngresos recibe las entradas monetarias del miembro
	*@param ingresos
	**/
	public void setIngresos(long ingresos){
		this.ingresos = ingresos;
	}
	/**
	*Metodo getIngresos devuelve las suma de los ingresos monetarios del miembro
	*@return ingresos
	**/
	public long getIngresos(){
		return ingresos;
	}
	/**
	*Metodo setEgresos recibe el total de gastos del miembro
	*@param egresos
	**/
	public void setEgresos(long egresos){
		this.egresos = egresos;
	}
	/**
	*Metodo getEgresos devuelve el total de gastos de un miembro
	*@return egresos
	**/
	public long getEgresos(){
		return egresos;
	}
	public beansMiembro(int cod,String nombre, String nick, String password, int edad, int pin, long tarjeta, String estado){
		this.cod = cod;
		this.nombre = nombre;
		this.nick = nick;
		this.password = password;
		this.edad = edad;
		this.pin = pin;
		this.tarjeta = tarjeta;
		this.egresos = 0;
		this.ingresos = 0;
		this.estado = estado;
		setListaInterna(new ArrayList<beansMiembro>());
	}
	/**
	*Metodo setListaInterna recibe una lista dinamica 
	*@param lista
	**/
	public void setListaInterna(ArrayList<beansMiembro> lista){
		this.list = lista;
	}
	/**
	*Metodo getListaInterna devuelve la lista recibida en el metodo setListaInterna
	*@return list
	**/
	public ArrayList<beansMiembro> getListaInterna(){
		return this.list;
	}
}