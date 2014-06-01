package org.bryampaniagua.sistema;

import java.io.Console;

import org.bryampaniagua.utilidades.Ingresar;
import org.bryampaniagua.manejadores.ManejadorAdministradores;
import org.bryampaniagua.manejadores.ManejadorMiembros;
import org.bryampaniagua.abstracta.AbstractRol;
import org.bryampaniagua.utilidades.Decodificador;
import org.bryampaniagua.abstracta.appAdministrador;
import org.bryampaniagua.abstracta.appMiembro;
 
	/**
	*Clase Login manda a comprobar un nick y una contraseña
	*@author Bryam Paniagua[B-2012442]
	**/
public class Login{
	/**
	*Metodo logear manda a comprobacion el nick y la contrasenia y si lo encuentra en alguna de las dos listas
	*lo envia a la clase appAdministrador o appMiembro 
	**/
	public void logear(){
		AbstractRol coman;
		String nick = null;
		boolean seguir = true; 
			
		do{			 
			try{

				System.out.print("\nNick>");
				nick = Ingresar.leerString();
				if(nick.equals("exit")){
					seguir = false;
				}else{
					String password = getPasswordOculta();
					System.out.println(" ");
					if(ManejadorAdministradores.getInstancia().comprobarAdmin(nick, password) != false){
						System.out.println("Bienvenido, "+ ManejadorAdministradores.getInstancia().getAdmin().getNombre());	
						coman = new appAdministrador(new Decodificador());
						coman.comandos();
					}else{
						if(ManejadorMiembros.getInstancia().comprobarMiembro(nick,password) != false){
							System.out.println("Bienvenido, "+ ManejadorMiembros.getInstancia().getMiembro().getNombre());	
							coman = new appMiembro(new Decodificador());
							coman.comandos();
						}else{
							System.out.println("No esta registrado.");
						}
					}	
					seguir = true;
				}
			}catch(Exception err){
				
			}
		}while(seguir == true);
		System.out.println("--------------------------------------------------------");
		System.out.println("|0o. IN5BV-B[2012442]-bpaniagua2012442@kinal.edu.gt .o0|");
		System.out.println("--------------------------------------------------------");
	}
	/**
	*Metodo getPasswordOculta hace el ingreso de caracteres de manera oculta, luego lo devuelve
	*@return pass
	**/
	public static String getPasswordOculta(){
		Console ocultar = null;
		
		ocultar = System.console();
		String pass ;
		try{
			pass = new String (ocultar.readPassword("\nPassword>"));
		}catch(Exception err){
			pass = " ";
		}
		return pass;
	}
}