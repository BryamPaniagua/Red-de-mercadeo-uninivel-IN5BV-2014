package org.bryampaniagua.utilidades;

import java.util.HashMap;
import java.util.ArrayList;

import org.bryampaniagua.utilidades.eventos.Escuchador;
	/**
	*Clase Decodificador separa por comandos y parametros la cadena ingresada enviandola a la interfaz Escuchador
	*@author Bryam Paniagua[B-2012442]
	**/ 
public class Decodificador{
	private Escuchador escuchar;	
	/**
	*Metodo agregarEscuchador recibe un objeto del tipo de la interfaz
	*@param escuchar
	**/
	public void agregarEscuchador(Escuchador escuchar){
		this.escuchar = escuchar;
	}
	/**
	*Metodo separarComando recibe la cadena para su fragmentacion
	*Es en este metodo donde se hace el envio de la cadena fragmentada a la interfaz
	*@param comando
	**/
	public void separarComando(String comando){
		try{
			HashMap<String, String> accionReaccion = null;
			accionReaccion = new HashMap<String, String>();
			ArrayList<String> param = new ArrayList<String>();
			String clave = null, condicion = null, valor = null, lista = null;
			boolean despues = false, where = false;
			if(comando.indexOf("'") >= 0){
				String comandoReem = reemplazar(comando);
				if(comandoReem != null){
					comando = comandoReem;
				}
			}
			if(comando.toLowerCase().startsWith("select")){
				String cad[] = comando.trim().replace(',' , ' ').split(" ");
				int i = 0;
				do{ 
					if(cad[i].trim().toLowerCase().equals("from")){
						despues = true;
						if(cad[i].trim().toLowerCase().equals("from"))
							lista = cad[i+1];
					}
					if(despues != true){
						if(cad[i].trim().startsWith("'")){
							param.add(cad[i].trim().substring(1, cad[i].length()-1).replace('-' , ' '));
						}else{
							param.add(cad[i].trim());
						}
					}
					if(cad[i].trim().toLowerCase().equals("where")){
						where = true;
					}
					if(cad[i].toLowerCase().equals("like") || cad[i].equals("=") || cad[i].equals(">") || cad[i].equals("<") || cad[i].equals(">=") || cad[i].equals("<=") && where == true){
						clave = cad[i-1].trim();
						condicion = cad[i].trim();
						valor = cad[i+1].trim();
					}else{
						for(int ii = 0; ii < cad[i].length(); ii++){
							char recorrerArray = cad[i].charAt(ii);
							if(recorrerArray == '=' && cad[i].charAt(ii-1) != '<' && cad[i].charAt(ii-1) != '>'){
								clave = cad[i].substring(0, ii);
								condicion = Character.toString(recorrerArray);
								valor = cad[i].substring((ii+1) , cad[i].length());
							}
							if(recorrerArray == '<' || recorrerArray == '>' ){
								if(cad[i].charAt(ii + 1) == '='){
									clave = cad[i].substring(0, ii);
									condicion = Character.toString(recorrerArray)+"=";
									valor = cad[i].substring((ii+2) , cad[i].length());
									break;
								}else{
									clave = cad[i].substring(0, ii);
									condicion = Character.toString(recorrerArray);
									valor = cad[i].substring((ii+1) , cad[i].length());
									break;
								}
							}
						}
					}
					i++;
				}while(i < cad.length);	
				
				for(int ii = 1; ii < param.size() ; ii++){
					accionReaccion.put("Dato"+ii, param.get(ii));
				}
				accionReaccion.put("Otros", lista+","+clave+","+condicion+","+valor);
				this.escuchar.accionador(cad[0].toLowerCase(), accionReaccion);
			}else{
			
				String  cadenaSeparada[] = comando.trim().split(" ");	
				int longitud =cadenaSeparada.length;
				switch(longitud){
					case 1:
						this.escuchar.accionadorProceso(cadenaSeparada[0]);
					break;
					case 2:
						this.escuchar.accionadorProceso((cadenaSeparada[0]+" "+cadenaSeparada[1]));
					break;
					default:
						String accion;
						if(cadenaSeparada.length == 3 && cadenaSeparada[2].indexOf("=") < 0){
							this.escuchar.accionadorProceso((cadenaSeparada[0]+" "+cadenaSeparada[1]+" "+cadenaSeparada[2]));
						}else{
							if(cadenaSeparada[2].indexOf("=") < 0){
								accion = cadenaSeparada[0]+" "+cadenaSeparada[1]+" "+cadenaSeparada[2];
							}else{
								accion = cadenaSeparada[0]+" "+cadenaSeparada[1];
							}
							for(int i = 2;i < cadenaSeparada.length; i++){
								String[] contenedor = cadenaSeparada[i].split("=");
								if(contenedor.length > 1){
									if(contenedor[1].startsWith("'")){
										accionReaccion.put(contenedor[0], contenedor[1].substring(1, contenedor[1].length()-1).replace('-' , ' '));
									}else{
										accionReaccion.put(contenedor[0], contenedor[1]);
									}
								}
							}
							this.escuchar.accionador(accion, accionReaccion);
						}
					break;
				}
			}
		}catch(Exception err){
			System.out.println("Se ha detectado un error en:"+err);
		}
	}
	/**
	*Metodo reemplazar recibe la cadena si es que tiene al menos una comilla simple para reemplazar por guion todos los 
	*espacios contenidos desde la primera comilla hasta la segunda haciendo parejas de comillas y luego lo devuelve
	*@param comando
	*@return comando
	**/
	public String reemplazar(String comando){
		ArrayList<String> comillas = new ArrayList<String>();
		try{
			String temp,reem;
			int ii = 0;
			for(int i = 0; i < comando.length() ; i++){
				if(comando.substring(i , i+1).equals("'")){
					comillas.add(""+i+"");
				}
			}
			for(int i = 0; i < comillas.size(); i++){
				ii = ii+1;
				temp = comando.substring(Integer.parseInt(comillas.get(i)) , Integer.parseInt(comillas.get(ii)));
				reem = comando.substring(Integer.parseInt(comillas.get(i)) , Integer.parseInt(comillas.get(ii))).replace(' ' , '-');
				comando = comando.replace(temp , reem);
				i = i+1;
				ii = ii+1;
			}
			comillas.clear();
			return comando;
		}catch(Exception e){
			System.out.println("Ingrese bien los parametros.");
		}
		return null;
	}
}