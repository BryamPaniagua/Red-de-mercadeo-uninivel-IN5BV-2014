package org.bryampaniagua.utilidades.eventos;

import java.util.HashMap; 
	 
	/**
	*Interfaz Escuchador recibe el comando y los parametros para su ejecucion
	*@author Bryam Paniagua[B-2012442]
	**/
public interface Escuchador{
	/**
	*Metodo accionador recibe siempre y cuando hayan parametros en la cadena
	*@param hacer
	*@param accionReacion
	**/
	void accionador(String hacer, HashMap<String, String> accionReaccion);
	
	/**
	*Metodo accionadorProceso recibe si solo hay comandos en la cadena
	*@param accion
	**/
	void accionadorProceso(String accion);
}