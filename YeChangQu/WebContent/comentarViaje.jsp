<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp" %>
<html>
<head>
<title>ShareMyTrip-ComentarViaje</title>
<link href="css/estilo.css" rel="stylesheet" type="text/css">
</head>
<body>
	<form action="comentarViaje?idViaje=${viajeId}" method="post">
	
	<header><h1>ShareMyTrip</h1>
 	<h2>Comentar viaje</h2></header>
	<hr><br>
  	<section>
  	<strong>ViajeId: </strong> ${viajeId}
  	<br/>
  	<strong>Promotor: </strong> ${promotor.login}
  	<br/>
  	<strong>Participantes: </strong><c:forEach var="entry" items="${participantes}">
  		${entry.login}
  		<br/> 
  	</c:forEach>
  	<table>
    	<tr> 
    		<td align="right">Usuario que quieres dejar comentario*</td>
	    	<td><input type="text" name="identificadorUsuario" size="15"></td>
      	</tr>
      	<tr> 
    		<td align="right">Punto de valoracion*</td>
	    	<td><input type="text" name="punto" size="15"></td>
      	</tr>
      	<tr> 
    		<td align="right">Comentario*</td>
	    	<td><textarea id ="comentario" name="comentario" rows="6" cols="28"></textarea></td>
      	</tr>
      	<tr>
    	    <td align="right"><input type="submit" value="DejarComentario"/></td>
      	</tr>
    </table>
    
	<br/>
	*Ojo, no puedes puntuarse si mismo y debes puntuar a los usuarios que sea participantes o promotor
	se puntua entre 1 y 5, los campos * son obligatorios
	<br/>
	<a href="misViajes"><input type="button" value="Volver"/></a>
	</section>
	<hr><br>
	<footer>
		<%@ include file="contacto.jsp"%>
   </footer>
  
   </form>
</body>
</html>