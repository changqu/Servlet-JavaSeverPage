<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp" %>
<%-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd"> --%>
<html>
<head>
<title>ShareMyTrip-Viaje en detalle</title>
<link href="css/estilo.css" rel="stylesheet" type="text/css">
</head>
<body>
	<header><h1>ShareMyTrip</h1>
 	<h2>Viaje en detalle</h2></header>
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
  	<strong>Solicitantes: </strong><c:forEach var="entry" items="${solicitantes}">
  		${entry.login}
  		<br/> 
  	</c:forEach>
  	<h3>Comentarios y valoraciones que hubo sobre promotor y participante</h3> 
	<table border="1">
			<tr><td><h3>Sobre promotor</h3></td></tr>
			<tr>
				<th>Promotor</th>
				<th>Punto de valoracion</th>
				<th>Comentario</th>
				<th>Comentada y valorada por</th>
			</tr>
		<c:forEach var="entry" items="${listaCalificacionesPromotor}" varStatus="i">
			<tr id="item_${i.index}">
				<td>${entry["calificacionAl"]}</td>
				<td>${entry["valoracion"]}</td>
				<td>${entry["comentario"]}</td>
				<td>${entry["calificacionDel"]}</td>
			</tr>
		</c:forEach>
	</table>
	<br/>
	<table border="1">
			<tr><td><h3>Sobre particpantes</h3></td></tr>
			<tr>
				<th>Participantes</th>
				<th>Punto de valoracion</th>
				<th>Comentario</th>
				<th>Comentada y valorada por</th>
			</tr>
		<c:forEach var="entry" items="${listaCalificacionesParticipantes}" varStatus="i">
			<tr id="item_${i.index}">
				<td>${entry["calificacionAl"]}</td>
				<td>${entry["valoracion"]}</td>
				<td>${entry["comentario"]}</td>
				<td>${entry["calificacionDel"]}</td>
			</tr>
		</c:forEach>
	</table>
	<br/>
	<a href="solicitarPlaza?idViaje=${viajeId}"><input type="button" value="Solicitar plaza"/></a>
	<br/>
	<br/>
	<a href="listarViajes"><input type="button" value="Volver"/></a>
	</section>
	<hr><br>
	<footer>
		<%@ include file="contacto.jsp"%>
   </footer>
</body>
</html>