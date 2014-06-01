package org.bryampaniagua.beans;

	/**
	*Clase beansVentas guardara los datos de una compra
	*@author Bryam Paniagua [B-2012442]
	**/
public class beansVentas{

	private int codigo;
	private String nick;
	private String nombre;
	private int cantidad;
	private int precio;
	private int total;
	private String fecha;
	private String accion = "Normal";
	private String descripcion;
	private int totalSinDescuento;
	private int porcentaje;
	private int productoAdicional;

	public beansVentas(){
	
	}
	/**
	*Metodo setNick recibe el nick del miembro comprador
	*@param nick
	**/
	public void setNick(String nick){
		this.nick = nick;
	}
	/**
	*Metodo getNick devuelve el nick del comprador
	*@return nick
	**/
	public String getNick(){
		return this.nick;
	}
	public void setId(int codigo){
		this.codigo = codigo;
	}
	public int getId(){
		return this.codigo;
	}
	/**
	*Metodo setCantidad recibe la cantidad de productos comprados
	*@param cantidad
	**/
	public void setCantidad(int cantidad){
		this.cantidad = cantidad;
	}
	/**
	*Metodo getCantidad devuelve la cantidad de productos comprados
	*@return cantidad
	**/
	public int getCantidad(){
		return this.cantidad;
	}
	/**
	*Metodo setFecha recibe la fecha de venta
	*@param fecha
	**/
	public void setFecha(String fecha){
		this.fecha = fecha;
	}
	/**
	*Metodo getFecha devuelve la fecha de compra
	*@return fecha
	**/
	public String getFecha(){
		return fecha;
	}
	/**
	*Metodo setNombre recibe el nombre del producto
	*@param nombre
	**/
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	/**
	*Metodo getNombre devuelve el nombre del producto comprado
	*@return nombre
	**/
	public String getNombre(){
		return this.nombre;
	}
	/**
	*Metodo setPrecio recibe el precio unitario del producto en el momento de la venta
	*@param precio
	**/
	public void setPrecio(int precio){
		this.precio = precio;
	}
	/*
	Metodo getPrecio devuelve el precio del producto
	@return precio
	*/
	public int getPrecio(){
		return this.precio;
	}
	/**
	*Metodo setTotal recibe el valor de la venta
	*@param total
	**/
	public void setTotal(int total){
		this.total = total;
	}
	/**
	*Metodo getTotal devuelve el total de la venta
	*@return total
	**/
	public int getTotal(){
		return this.total;
	}
	public void setDescuento(int porcentaje){
		this.porcentaje = porcentaje;
	}
	public int getDescuento(){
		return this.porcentaje;
	}
	public void setAccion(String accion){
		this.accion = accion;
	}
	public String getAccion(){
		return this.accion;
	}
	public void setProductoAdicional(int productoAdicional){
		this.productoAdicional = productoAdicional;
	}
	public int getProductoAdicional(){
		return this.productoAdicional;
	}
	public void setDescripcion(String descripcion){
		this.descripcion = descripcion;
	}
	public String getDescripcion(){
		return this.descripcion;
	}
	public void setTotalSinDescuento(int totalSinDescuento){
		this.totalSinDescuento = totalSinDescuento;
	}
	public int getTotalSinDescuento(){
		return this.totalSinDescuento;
	}
}