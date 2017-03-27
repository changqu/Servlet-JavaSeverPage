<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd"> --%>
<html>
<head> 
<title>ShareMyTrip-Registrarse</title><%--para navegador, nombre de la pagina --%>
<link href="css/estilo.css" rel="stylesheet" type="text/css">
</head> 
<body>
  <form action="registrarse" method="post">
	
	<header><h1>ShareMyTrip</h1>
 	<h2>Registrarse</h2></header>
 	<hr><br>
 	<%@ include file="indicePublico.jsp"%>
 	<section>
 	<table>
    	<tr> 
    		<td align="right">Identificador de usuario</td>
	    	<td><input type="text" name="identificadorUsuario" size="15"></td>
      	</tr>
      	<tr> 
    		<td align="right">Nombre</td>
	    	<td><input type="text" name="nombre" size="15"></td>
      	</tr>
      	<tr> 
    		<td align="right">Apellidos</td>
	    	<td><input type="text" name="apellidos" size="15"></td>
      	</tr>
      	<tr> 
    		<td align="right">Correo electronico</td>
	    	<td><input type="text" name="correoElectronico" size="15"></td>
      	</tr>
      	<tr> 
    		<td align="right">Contrasena</td>
	    	<td><input type="password" name="contrasena" size="15"></td>
      	</tr>
      	<tr> 
    		<td align="right">Repetir contrasena</td>
	    	<td><input type="password" name="repetirContrasena" size="15"></td>
      	</tr>
      	<tr>
    	    <td align="right"><input type="submit" value="Registrar"/></td>
      	</tr>
      </table>
      </section>
   </form>
   <hr><br>
   <footer>
		<%@ include file="contacto.jsp"%>
   </footer>
</body>
</html>