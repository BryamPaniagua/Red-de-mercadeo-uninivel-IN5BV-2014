package org.bryampaniagua.utilidades;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

	/**
	*Clase Ingresar hace el ingreso de caracteres de afuera hacia adentro del programa
	*@author Bryam Paniagua[B-2012442]
	**/
public class Ingresar{
	/**
	*Metodo leerString crea una variable con el valor de la clase BufferedReader y recibe caracteres, luego los retorna 
	*@return dato
	**/
	public static String leerString(){
		BufferedReader ingreso = null;
		
		String dato = null;
		
		try{
			ingreso = new BufferedReader(new InputStreamReader(System.in));
			dato = ingreso.readLine();
		}catch(IOException mstk){
			System.out.println("Se ha producido un error. Debuger....");
		}
		return dato;
	}
}