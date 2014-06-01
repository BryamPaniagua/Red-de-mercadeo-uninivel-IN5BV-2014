package org.bryampaniagua.beans;
 
import java.util.ArrayList;

import org.bryampaniagua.beans.beansOferta;	

	/**
	*La clase beansProducto guardara los datos de un producto para su venta 
	*@author Bryam Paniagua[B-2012442]
	**/
 
public class beansProducto{
	private int idProducto;
	private String nombre;
	private String categoria;
	private int precio;
	private int existencias;
	private ArrayList<beansOferta> listaDeOfertas;
	/**
	*Metodo sacarCodigo sumara en uno la variable estatica 
	*@param idProducto
	**/
	public beansProducto() {
		setListaDeOfertas(new ArrayList<beansOferta>());
	}
	/**
	*Metodo getId retornara el valor unico para cada producto
	*@return idProducto
	**/
	public void setId(int idProducto){
		this.idProducto = idProducto;
	}
	public int getId(){
		return idProducto;
	}
	/**
	*Metodo setNombre recibe el nombre del producto ingresado
	*@param nombre
	**/
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	/**
	*Metodo getNombre retorna el valor recibido por el metodo setNombre
	*@return nombre
	**/
	public String getNombre(){
		return this.nombre;
	}
	/**
	*Metodo setCategoria recibe la categoria del producto ingresado
	*@param categoria
	**/
	public void setCategoria(String categoria){
		this.categoria  = categoria;
	}
	/**
	*Metodo getCategoria devuelve la categoria del producto ingresado
	*@return categoria
	**/
	public String getCategoria(){
		return this.categoria;
	}
	/**
	*Metodo setPrecio recibe el precio de compra del producto
	*@param precio
	**/
	public void setPrecio(int precio){
		this.precio = precio;
	}
	/**
	*Metodo getPrecio devuelve el precio del producto ingresado
	*@return precio
	**/
	public int getPrecio(){
		return precio;
	}
	/**
	*Metodo setExistencia recibe la cantidad de productos que hay disponibles
	*@param existencias
	**/
	public void setExistencias(int existencias){
		this.existencias = existencias;
	}
	/**
	*Metodo getExistencias devuelve la cantidad de productos existentes
	*@return existencias
	**/
	public int getExistencias(){
		return existencias;
	}
	public beansProducto(int idProducto, String nombre,String categoria,int precio,int existencias){
		this.idProducto = idProducto;
		this.nombre = nombre;
		this.categoria = categoria;
		this.precio = precio;
		this.existencias = existencias;
		setListaDeOfertas(new ArrayList<beansOferta>());
	}
	public void setListaDeOfertas(ArrayList<beansOferta> listaDeOfertas){
		this.listaDeOfertas = listaDeOfertas;
	}
	public ArrayList<beansOferta> getListaDeOfertas(){
		return this.listaDeOfertas;
	}
}