package org.bryampaniagua.manejadores;

import java.util.ArrayList;
import java.util.Calendar;

import org.bryampaniagua.beans.beansMiembro;
import org.bryampaniagua.abstracta.appMiembro;
	/**
	*Clase ManejadorMiembro contiene los procesos propios de un miembro
	*@author Bryam Paniagua[B-2012442]
	**/
public class ManejadorMiembros{

	private ArrayList<beansMiembro> miembros;
	private beansMiembro autenticado = null;
	private beansMiembro resultado;
	private static ManejadorMiembros instancia = null ;
	private static int mes;
	private static int anio;
	private int cod = 4;
	/**
	*En el contructor se crea la lista y se hace el ingreso de tres meimbros por defecto
	**/
	private ManejadorMiembros(){
		miembros = new ArrayList<beansMiembro>();
		mes = Calendar.getInstance().get(Calendar.MONTH);
		anio = Calendar.getInstance().get(Calendar.YEAR);
		miembros.add( new beansMiembro(1,"Ricardo", "richard", "122",23, 324, Long.parseLong("99999999999"), "nuevo"));
		miembros.add( new beansMiembro(2,"Jordi", "jordi", "j21",18, 234, Long.parseLong("8888888888"), "nuevo"));
		miembros.add( new beansMiembro(3,"Miembro", "mim", "mim",0, 423, Long.parseLong("7777777777"), "nuevo"));
	}
	/**
	*En el metodo getInstancia se aplica SINGLETON para la clase
	**/
	public static ManejadorMiembros getInstancia(){
		if(instancia == null)
			instancia = new ManejadorMiembros();
		return instancia;
	}
	/*
	Metodo addMember recibe un objeto con los datos de un nuevo miembro y lo ingresa
	@param miembro
	*/
	public void addMember(beansMiembro miembro){
		miembro.setCod(cod++);
		miembros.add(miembro);
	}
	public void addDownline(beansMiembro downline, beansMiembro upline){
		downline.setCod(cod++);
		upline.getListaInterna().add(downline);
	}
	/**
	*Metodo desautenticarMiembro vuelve nula la autenticacion cuando el miembro cierra sesion
	*No recibe parametros
	**/
	public void desautenticarMiembro(){
		System.out.println(" ");
		System.out.println("Ha cerrado sesion.");
		System.out.println(" ");
		setMiembro(null);
	}
	/**
	*Metodo setMiembro recibe el objeto del miembro que ha iniciado sesion
	*@param autenticado
	**/
	public void setMiembro(beansMiembro autenticado){
		this.autenticado = autenticado;
	}
	/**
	*Metodo getMiembro devuelve al miembro en sesion
	*@param autenticado
	**/
	public beansMiembro getMiembro(){
		return autenticado;
	}
	/**
	*Metodo getLista devuelve la lista que guarda a los miembros principales
	*@return miembros
	**/
	public ArrayList<beansMiembro> getLista(){
		return this.miembros;
	}
	/**
	*Metodo comprobarMiembro recibe un nick y una contrasenia, los compara y si la busqueda es exitosa
	*Envia al miembro al metodo setMiembro
	*@param nick
	*@param password
	**/
	public boolean comprobarMiembro(String nick, String password){
		for(beansMiembro miembro : miembros){
			if(miembro.getNick().equals(nick) && miembro.getPassword().equals(password)){
				setMiembro(miembro);
				return true;
			}else{
				resultado = comprobarInterior(miembro, nick, password);
				if(resultado != null){
					setMiembro(resultado);
					return true;
				}
			}
		}
		return false;
	}
	/**
	*Metodo comprobarInterior recibe un miembro pricipal, un nick y un contrasenia y compara con los miembros contenidos
	*dentro del miembro recibido
	*@param miembro
	*@param nick
	*@param password
	**/
	public beansMiembro comprobarInterior(beansMiembro miembro, String nick, String password){
		for(beansMiembro InteriorMiembro : miembro.getListaInterna()){
			if(InteriorMiembro.getNick().equals(nick) && InteriorMiembro.getPassword().equals(password)){
				return InteriorMiembro;
			}else{
				resultado = comprobarInterior(InteriorMiembro, nick, password);
				if(resultado != null)
					return resultado;
				}
			}
		return null;
	}
	/**
	*Metodo showMe muestra los datos del miembro en sesion
	*No recibe datos
	**/
	public void showMe(){
		if(getMiembro() != null){
			System.out.println("Tus datos son:");
			System.out.println("----------------------------------");
			System.out.println("| Nick    |   "+getMiembro().getNick());
			System.out.println("----------------------------------");
			System.out.println("| Nombre  |   "+getMiembro().getNombre());
			System.out.println("| Rol     |    Miembro");
			System.out.println("| Edad    |   "+getMiembro().getEdad());				
			System.out.println("| Pin     |   "+getMiembro().getPin());
			System.out.println("| Tarjeta |   "+getMiembro().getTarjeta());
			System.out.println("----------------------------------");
		}
	}
	/**
	*Metodo showMeMoney muestra los datos monetarios del miembro recibido y le aplica la sangria recibida del metodo getLines 
	*en appMiembro
	*@param miembro
	*@param nivel
	**/
	public void showMeMoney(beansMiembro miembro, int nivel){
		String tab = appMiembro.getLines(nivel);
		System.out.println(tab+"----------------------------------");
		System.out.println(tab+"| "+miembro.getNick()+" Tus datos monetarios son:");
		System.out.println(tab+"----------------------------------");
		System.out.println(tab+"| Entradas        -->    "+miembro.getIngresos());
		System.out.println(tab+"| Salidas         -->    "+miembro.getEgresos());
		System.out.println(tab+"----------------------------------");
		if(miembro.getIngresos() < miembro.getEgresos()){
			System.out.println(tab+"|   Su deficit es de : "+(miembro.getIngresos() - miembro.getEgresos()));
		}else if(miembro.getEgresos() == 0){
			System.out.println(tab+"| Compra algo para estar activo.");
		}else if(miembro.getEgresos() < miembro.getIngresos()){
			System.out.println(tab+"|   Su superavit es de : "+(miembro.getIngresos() - miembro.getEgresos()));
		}
		System.out.println(tab+"----------------------------------");
	}
	/**
	*Metodo buscarDownline busca un nick en la lista principal y luego manda a cada miembro al metodo buscarDentroDownline
	*para buscarlos adentro de cada miembro 
	*@param nick
	*	@return resultado
	**/
	public beansMiembro buscarDownline(String nick){
		for(beansMiembro buscar : miembros ){
			if(buscar.getNick().equals(nick)){
				return buscar;
			}else{
				resultado = buscarDentroDownline(buscar, nick);
				if(resultado != null)
					return resultado;
			}
		}
		return null;
	}
	/**
	*Metodo buscarDentroDownline recibe un miembro y un nick 
	*Busca la coincidencia del nick dentro del miembro recibido
	*Lo envia dentro del interior de cada miembro si la busqueda no es exitosa
	*@param miembro
	*@param nick
	*	@return resultado
	**/
	public beansMiembro buscarDentroDownline(beansMiembro miembro, String nick){
		for(beansMiembro busquedaInterior : miembro.getListaInterna() ){
			if(busquedaInterior.getNick().equals(nick)){
				return busquedaInterior;
			}else{
				resultado = buscarDentroDownline(busquedaInterior, nick);
				if(resultado != null)
					return resultado;
			}
		}
		return null;
	}
	/**
	*Metodo search2 busca la coincidencia del nick dentro del miembro recibido
	*@param miembro
	*@param cadena
	**/
	public void search2(beansMiembro miembro, String cadena){
		for(beansMiembro miembroInterno : miembro.getListaInterna()){
			coincidencias(miembroInterno, cadena);
			search2(miembroInterno, cadena);
		}
	}
	/**
	*Metodo search1 busca la coincidencia del nick recibido en la lista principal
	*@param cadena
	**/
	public void search1(String cadena){
		for(beansMiembro mim : miembros){
			coincidencias(mim, cadena);
			search2(mim, cadena);
		}
	}
	/**
	*Metodo coincidencias recibe un miembro y una cadena para buscar una coindicencia en el nick
	*Y lo muestra si hay alguna coincidencia
	*@param mim
	*@param cadena
	**/
	public void coincidencias(beansMiembro mim, String cadena){
		boolean contiene = false;
		if(cadena.length() == 0){
			if(mim.getNick().indexOf(cadena) > -1)
				contiene = true;
		}
		if(mim.getNick().endsWith(cadena) || mim.getNick().startsWith(cadena) || contiene == true){
			System.out.println("----------------------------------");
			System.out.println("| Nick    |   "+mim.getNick());
			System.out.println("----------------------------------");
			System.out.println("| Nombre  |   "+mim.getNombre());
			System.out.println("| Rol     |    Miembro");
			System.out.println("| Edad    |   "+mim.getEdad());				
			System.out.println("| Pin     |   "+mim.getPin());
			System.out.println("| Tarjeta |   "+mim.getTarjeta());
			System.out.println("----------------------------------");
		}
	}
	/**
	*Metodo devolverPorcentaje recibe una cantidad y un nivel
	*Segun el nivel se sacara un porcentaje de pago y lo devuelve
	*@param valor
	*@param nivel
	*	@return descuento
	**/
	public long devolverPorcentaje(long valor, int nivel){
		long descuento = 0;
		if(nivel == 1){
			descuento = (valor * 2)/100;
		}else if(nivel  == 2){
			descuento = (valor * 25)/100;
		}else if(nivel  == 3){
			descuento = (valor * 6)/100;
		}else if(nivel >= 4 && nivel <= 5){
			descuento = (valor * 3)/100;
		}else if(nivel >= 6 && nivel <= 10){
			descuento = (valor * 2)/100;
		}
		return descuento;
	}
	/**
	*Metodo condicionarPago recibe un lista y un nivel 
	*Debe cumplir con ciertas condiciones segun el nivel y si los cumple devuelve un booleano
	*@param lista
	*@param nivel
	*	@return resultado
	**/
	public boolean condicionarPago(ArrayList<beansMiembro> lista, int nivel){
		boolean resultado = false;
		if(nivel <= 2){
			resultado = true;
		}else if(nivel == 3){
			if(lista.size() >= 3)
				resultado = true;
		}else if(nivel == 4){
			if(lista.size() >= 5)
				resultado = true;
		}else if(nivel >= 5 && nivel <= 6){
			if(lista.size() >= 7)
				resultado = true;
		}else if(nivel >= 7 && nivel <= 8){
			if(lista.size() >= 9)
				resultado = true;
		}else if(nivel >= 9 && nivel <= 10){
			if(lista.size() >= 11)
				resultado = true;
		}
		return resultado;
	}
	/**
	*Metodo cobrarMiembros recibe un miembro y un nivel
	*Y realiza el cobro por su compra
	*@param miembro
	*@param nivel
	*	@return des
	**/
	public long cobrarMiembros(beansMiembro miembro, int nivel){
		long a = 0, desc = 0, des = 0;
		if(nivel <= 10){
			if(condicionarPago(miembro.getListaInterna(), nivel) == true){
				for(beansMiembro miembroInterno : miembro.getListaInterna()){
					desc += devolverPorcentaje(miembroInterno.getEgresos(), nivel)+50;
					a = cobrarMiembros(miembroInterno, nivel+1);
					des = a + desc;
				}
			}
		}
		return des;
	}
	/**
	*Metodo payMembers empieza el cobro de porcentajes en la lista principal
	*No recibe parametros
	**/
	public void payMembers(){
		for(beansMiembro miembro : miembros){
			miembro.setIngresos(cobrarMiembros(miembro, 1));
			payMembersIn(miembro);
		}
	}
	/**
	*Metodo payMembersIn recibe un miembro y le cobra el porcentaje a cada miembro dentro de el
	*@param mim
	**/
	public void payMembersIn(beansMiembro mim){
		for(beansMiembro otroMiembro : mim.getListaInterna()){
			otroMiembro.setIngresos(cobrarMiembros(otroMiembro, 1));
			payMembersIn(otroMiembro);
		} 
	}
	/**
	*Metodo comprobarEstado muestra un mensaje segun su situacion de ingreso
	*@param miembro
	**/
	public void comprobarEstado(beansMiembro miembro){
		if(miembro.getEstado().equals("nuevo")){
			miembro.setEstado("viejo");
			System.out.println("Eres nuevo.... ");
			System.out.println("Te damos la bienvenida.");
			System.out.println("Te daremos lo que queda del mes para que puedas comprar un producto.");
			System.out.println("Pero recuerda que en los siguientes meses tienes del primero al \nquinto dia del mes para comprar o tu cuenta sera desactivada ");
		}else if(miembro.getEstado().equals("viejo")){
			if(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) <= 29 && Calendar.getInstance().get(Calendar.MONTH) == mes){
				if(miembro.getEgresos() == 0 ){
					System.out.println("Recuerda que tienes solo lo que queda del mes para adquirir un producto");
				}
			}else{
					miembro.setEstado("Activo");
					comprobarEstado(miembro);
				}
		}else if(miembro.getEstado().equals("Activo")){
			if(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == 5 && miembro.getEgresos() == 0){
				System.out.println("Tu cuenta esta inactiva. Debes esperar al otro mes");
				miembro.setEstado("Inactivo");
				setMiembro(null);
			}else if(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) < 5 && miembro.getEgresos() == 0){
				System.out.println("Tienes "+(5-Calendar.getInstance().get(Calendar.DAY_OF_MONTH))+" dia(s) para comprar");
			}
		}else if(miembro.getEstado().equals("Inactivo")){
			if(Calendar.getInstance().get(Calendar.MONTH) == mes ){
				System.out.println("Estas inactivo, debes esperar al otro mes para comprar\no pasara lo mismo nuevamente");
				setMiembro(null);
			}else if(Calendar.getInstance().get(Calendar.MONTH) < 5){
				miembro.setEstado("Activo");
			}
		}
	}
	/**
	*Metodo volverCero resetea el estado monetario de los miembros principales segun la fecha
	*No recibe parametros
	**/
	public void volverCero(){
		if(Calendar.getInstance().get(Calendar.MONTH) != mes || Calendar.getInstance().get(Calendar.YEAR) != anio){
			mes = Calendar.getInstance().get(Calendar.MONTH);
			anio = Calendar.getInstance().get(Calendar.YEAR);
			for(beansMiembro miembro :  miembros){
				miembro.setEgresos(0);
				miembro.setIngresos(0);
				volverCeroInterior(miembro);
			}
		}
	}
	/**
	*Metodo volverCeroInterior resetea el estado monetario de los downlines 
	*@param miembro
	**/
	public void volverCeroInterior(beansMiembro miembro){
		for(beansMiembro InteriorMiembro : miembro.getListaInterna()){
			InteriorMiembro.setEgresos(0);
			InteriorMiembro.setIngresos(0);
			volverCeroInterior(InteriorMiembro);
		}
	}
	public void most(beansMiembro miembro, String[] param, String clave, String condicion, String valor){
		for(beansMiembro  mm : miembro.getListaInterna()){
			if(clave.equals("tarjeta") ||clave.equals("ingresos") ||clave.equals("egresos")){
				if(true == operarLong(Long.parseLong(getDatos(clave, mm)) ,condicion, Long.parseLong(valor))){
					System.out.println("-----------------------------------------");
					for(int i = 1; i < param.length ; i++){
						devolverDatos(param[i].trim(), mm);
					}
					System.out.println("-----------------------------------------");
				}
			}
			if(clave.equals("edad") || clave.equals("codigo")){
				if(true == ManejadorProductos.getInstancia().operar(Integer.parseInt(getDatos(clave, mm)) ,condicion, Integer.parseInt(valor))){
					System.out.println("-----------------------------------------");
					for(int i = 1; i < param.length ; i++){
						devolverDatos(param[i].trim(), mm);
					}
					System.out.println("-----------------------------------------");
				}
			}
			if(condicion.toLowerCase().equals("like")){
				if(getDatos(clave, mm).toLowerCase().startsWith(valor.toLowerCase())){
					System.out.println("-----------------------------------------");
					for(int i = 1; i < param.length ; i++){
						devolverDatos(param[i].trim(), mm);
					}
					System.out.println("-----------------------------------------");
				}
			}
			if(clave.equals("null") && condicion.equals("null") && valor.equals("null")){
				System.out.println("-----------------------------------------");
				for(int i = 1; i < param.length ; i++){
					devolverDatos(param[i].trim(), mm);
				}
				System.out.println("-----------------------------------------");
			}
			most(mm, param, clave, condicion, valor);
		}	
	}
	public void mostrarDatosDownline(String[] param, String clave, String condicion, String valor){
		try{
			if(miembros.size() > 0){
				if(valor.indexOf("'") > -1){
					valor = valor.substring(1 , valor.length() - 1).replace('-' , ' ');
				}
				if(param[1].equals("*") && clave.equals("null") && condicion.equals("null") && valor.equals("null")){
					appMiembro.listMembers();
				}else{
					System.out.println("");
					for(beansMiembro  mim : miembros){
						if(param[1].equals("*")){
							param = ("codigo,nombre,nick,edad,pin,tarjeta,ingresos,egresos").split(",");
						}
						if(clave.equals("tarjeta") ||clave.equals("ingresos") ||clave.equals("egresos")){
							if(true == operarLong(Long.parseLong(getDatos(clave, mim)) ,condicion, Long.parseLong(valor))){
								System.out.println("-----------------------------------------");
								for(int i = 1; i < param.length ; i++){
									devolverDatos(param[i].trim(), mim);
								}
								System.out.println("-----------------------------------------");
							}
						}
						if(clave.equals("edad") || clave.equals("codigo")){
							if(true == ManejadorProductos.getInstancia().operar(Integer.parseInt(getDatos(clave, mim)) ,condicion, Integer.parseInt(valor))){
								System.out.println("-----------------------------------------");
								for(int i = 1; i < param.length ; i++){
									devolverDatos(param[i].trim(), mim);
								}
								System.out.println("-----------------------------------------");
							}
						}
						if(condicion.toLowerCase().equals("like")){
							if(getDatos(clave, mim).toLowerCase().startsWith(valor.toLowerCase())){
								System.out.println("-----------------------------------------");
								for(int i = 1; i < param.length ; i++){
									devolverDatos(param[i].trim(), mim);
								}
								System.out.println("-----------------------------------------");
							}
						}
						if(clave.equals("null") && condicion.equals("null") && valor.equals("null")){
							System.out.println("-----------------------------------------");
							for(int i = 1; i < param.length ; i++){
								devolverDatos(param[i].trim(), mim);
							}
							System.out.println("-----------------------------------------");
						}
						most(mim, param, clave, condicion, valor);
					}
					System.out.println("");
				}	
			}else{
				System.out.println("No hay registros.");
			}
		}catch(Exception err){
			System.out.println(err);
		}
	}
	public void devolverDatos(String campo, beansMiembro miembro){
		String descripcion = null, parametro = null;
		switch(campo.trim()){
			case "codigo":
				descripcion = "Codigo del downline      ";
				parametro = Integer.toString(miembro.getCod());
			break;
			case "nombre":
				descripcion = "Nombre del downline      ";
				parametro = miembro.getNombre();
			break;
			case "nick":
				descripcion = "Nick del donwline        ";
				parametro = miembro.getNick();
			break;
			case "password":
				System.out.println("No se puede mostrar la contrasenia");
				descripcion = "null";
				parametro = "null";
			break;
			case "pin":
				descripcion = "Pin del downline         ";
				parametro = Integer.toString(miembro.getPin());
			break;
			case "tarjeta":
				descripcion = "Tarjeta del downline     ";
				parametro = Long.toString(miembro.getTarjeta());			
			break;
			case "ingresos":
				descripcion = "Ingresos del downline    ";
				parametro = Long.toString(miembro.getIngresos());
			break;
			case "egresos":
				descripcion = "Egresos del downline     ";
				parametro = Long.toString(miembro.getEgresos());			
			break;
			case "edad":
				descripcion = "Edad del downline        ";
				parametro = Integer.toString(miembro.getEdad());
			break;
			default:
				descripcion = "null";
				parametro = "null";
			break;
		}
		if(true != (descripcion.equals("null")) && true != (parametro.equals("null"))){
			System.out.println(descripcion + parametro);
		}
	}
	public boolean operarLong(Long var, String cond, Long var2){
		if(cond.equals("<") && var < var2)
			return true;
		if(cond.equals(">") && var > var2)
			return true;
		if(cond.equals(">=") && var >= var2)
			return true;
		if(cond.equals("=") && var == var2)
			return true;
		if(cond.equals("<=") && var <= var2)
			return true;
		return false;
	}
	public String getDatos(String clave, beansMiembro miembro){
		if(clave.trim().equals("nombre"))
			return miembro.getNombre();
		if(clave.trim().equals("nick"))
			return miembro.getNick();
		if(clave.trim().equals("edad"))
			return Integer.toString(miembro.getEdad());
		if(clave.trim().equals("codigo"))
			return Integer.toString(miembro.getCod());
		if(clave.trim().equals("tarjeta"))
			return Long.toString(miembro.getTarjeta());
		if(clave.trim().equals("pin"))
			return Integer.toString(miembro.getPin());
		if(clave.trim().equals("ingresos"))
			return Long.toString(miembro.getIngresos());
		if(clave.trim().equals("egresos"))
			return Long.toString(miembro.getEgresos());
		return "null";
	}
}