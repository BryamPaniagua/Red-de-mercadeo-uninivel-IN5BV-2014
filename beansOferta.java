package org.bryampaniagua.beans;


public class beansOferta{
	private String descripcion;
	private String fechaDeInicio;
	private String fechaDeFinalizacion;
	private String accion;
	private String oferta;
	private int porcentaje;
	private int idOferta;
	
	public beansOferta(){
		
	}
	public void setDescripcion(String descripcion){
		this.descripcion = descripcion;
	}
	public String getDescripcion(){
		return this.descripcion;
	}
	public void setFechaDeInicio(String fechaDeInicio){
		this.fechaDeInicio = fechaDeInicio;
	}
	public String getFechaDeInicio(){
		return fechaDeInicio;
	}
	public void setFechaDeFinalizacion(String fechaDeFinalizacion){
		this.fechaDeFinalizacion = fechaDeFinalizacion;
	}
	public String getFechaDeFinalizacion(){
		return fechaDeFinalizacion;
	}
	public void setOferta(String oferta){
		this.oferta = oferta;
	}
	public String getOferta(){
		return oferta;
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
	public void setId(int idOferta){
		this.idOferta = idOferta;
	}
	public int getId(){
		return this.idOferta;
	}
}