<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd"> --%>
<html>
<head> 
<title>ShareMyTrip-Inicie sesion</title> <%-- nombre de la ventana del navegador --%>
<link href="css/estilo.css" rel="stylesheet" type="text/css">
</head>
<body>
  <form action="validarse" method="post">

	<header><h1>ShareMyTrip</h1>
 	<h2>Inicie sesion</h2></header>
 	<hr><br><%-- produce una linea recto --%>
 	<%@ include file="indicePublico.jsp"%>
 	<section>
	 	<table>
	    	<tr> 
	    		<td align="right">Su identificador de usuario</td>
		    	<td><input type="text" name="identificadorUsuario" size="15"></td>
	      	</tr>
	      	<tr> 
	    		<td align="right">Su contrasena</td>
		    	<td><input type="password" name="contrasena" size="15"></td>
	      	</tr>
	      	<tr>
	    	    <td align="right"><input type="submit" value="Enviar"/></td>
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