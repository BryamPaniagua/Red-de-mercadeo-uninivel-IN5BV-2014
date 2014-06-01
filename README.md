Framework que simula una red de mercadeo.

En este último año tras la proliferación de muchas redes de mercadeo y el éxito de algunas se a decidido construir nuestra propia red utilizando el plan de compensacion uninivel. Esto implica el diseño de una base de datos  adecuada  en  la  que  se  almacenará  información  detallada  de nuestros clientes así como de sus consumos/reconsumos. Obviamente para esto necesitamos una aplicación gráfica adecuada que nos ayude a gestionar  esta  información.  Se  le  a  encargado  al  departamento  de informática  que se encargue de ambos asuntos lo antes posible. Las especificaciones técnicas se describirán a detalle más abajo.

Comandos del miembro:

	-add downline
		parametros nombre='nombre' nick='nick' password=contrasenia edad=000 pin=000 tarjeta=0000 down=upline
		
	-buy product
		parametros nombre='nombre del producto'  cantidad=000
		
	-edit me
		parametros 'los que quiera cambiar'
		
	-show me [money]
		No recibe parametros
		
	-show history [buy] [downline] [nick='nick']
		parametros puede recibir un nick
		
	-logout 
		No recibe parametros
		
Comandos del administrador:

	-show sales [nombre='nombre del producto']
		Si se le envia un nombre muestra las coincidencias
		Sino muestra todas las ventas
	
	-list admins
		No recibe parametros
	
	-show me
		No recibe parametros
		
	-add admin
		parametros nombre='nombre' nick='nick' password=contrasenia edad=000 
	
	-add member
		parametros nombre='nombre' nick='nick' password=contrasenia edad=00 pin=000 tarjeta=0000 
		
	-edit me
		parametros Los que se quieran cambiar
	
	-remove admin
		parametro nick='nick'
		
	-add product
		parametros nombre='nombre' categoria='categoria' precio=000 existencias=000
		
	-remove product
		parametros nombre='nombre'
		
	-logout
		No recibe parametros
		
	-add ofert
		parametros producto='nombre del producto' descripcion='Una descripcion inicio='dia-mes-anio' fin='dia-mes-anio' oferta=[El descuento] o [La oferta] ejemplo: oferta=30% o oferta=3x1
		
Comandos compartidos:		

	-search downline
		parametros nick='nick'
		
	-list products
		No recibe parametros
		
	-list downlines
		No recibe parametros
		
	-show product
		parametros nombre='nombre del producto'
		Si no se especifica el nombre mostrara todos los productos ingresados
		
	-show status members
		No recibe parametros
		
	-select (datos separados por comas) from (lista en minusculas) [condicion (los datos de la condicion deben estar separados por comas)] 
		ejemplo:	select codigo,nombre,precio from productos where codigo < 2
			
			Los miembros podran hacer consultas en la mayoria de lista excepto en administradores
