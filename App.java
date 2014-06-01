package org.bryampaniagua.sistema;

import org.bryampaniagua.sistema.Login;
	/**
	*Clase principal, esta instancia a la clase login para que el usuario entre a su cuenta 
	*@author Bryam Paniagua[B-2012442]
	**/

public class App{
	public static void main( String[] args){
		Login login = new Login();
		login.logear();
	
	}
}