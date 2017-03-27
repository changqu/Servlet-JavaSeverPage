<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp" %>
<%-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd"> --%>
<html>
<head>
<title>ShareMyTrip-Listado de viajes</title>
<link href="css/estilo.css" rel="stylesheet" type="text/css">
</head>
<body>
	<header><h1>ShareMyTrip</h1>
 	<h2>Lista de oferta de viajes</h2></header>
	<hr><br>
    <c:if test="${user==null}">
		<%@ include file="indicePublico.jsp"%>
    </c:if>
    <c:if test="${user!=null}">
    	<%@ include file="indiceRegistrado.jsp"%>
    </c:if>
  	<section>
  	<c:if test="${user!=null}">
		*Para ver la viaje en detalle, pulse id de viaje, alli puedes ver los comentarios y puntuacion sobre el 
		promotor y del resto de participantes. Ademas, en la pagina de la detalle del viaje puedes hacer solicitud 
		de plaza.<br/>
		<br/>
	</c:if>
	<strong>Ordenar viajes:</strong><br/>
	<a href="ordenacionOrigen"><input type="button" value="Ordenar viaje por origen"/></a><br/>
	<a href="ordenacionDestino"><input type="button" value="Ordenar viaje por destino"/></a><br/>
	<a href="ordenacionFecha"><input type="button" value="Ordenar viaje por fecha salida"/></a><br/>
	<a href="ordenacionPromotor"><input type="button" value="Ordenar viaje por promotor"/></a><br/>
	<br/>
	<form action="filtrarViajes" method="get">
		<strong>Filtrar viajes:</strong><br/>
		*Introduzcan parametros que interesan para filtrar viaje, la primera letra de la ciudad debe ser mayúscula<br/>
		Origen: <input type="text" size="16" name="fOrigen"/><br/>
		Destino: <input type="text" size="16" name="fDestino"/><br/>
		Coste maximo(*número): <input type="text" size="12" name="fCoste"/><br/>
		<input type="submit" value="FiltrarViaje"><br/>
	</form>
	<table border="1">
			<tr>
				<th>ID viaje</th>
				<th>Origen</th>
				<th>Fecha salida</th>
				<th>Destino</th>
				<th>Fecha llegada</th>
				<th>Plazas libres</th>
				<th>Fecha limite para apuntar</th>
				<th>Comentario del promotor al viaje</th>
				<th>Coste a repartir</th>
			</tr>
		<c:forEach var="entry" items="${listaViajesActivo}" varStatus="i">
			<tr id="item_${i.index}">
				<c:if test="${user!=null}">
					<td><a href="mostrarViaje?id=${entry.id}">${entry.id}</a></td><%--aqui pone un href para que pueda ver viaje con la detalle --%>
				</c:if>
				<c:if test="${user==null}">
					<td>${entry.id}</td>
				</c:if>
				<td>${entry.departure.city}</td>
				<td>${entry.departureDate}</td>
				<td>${entry.destination.city}</td>
				<td>${entry.arrivalDate}</td>
				<td>${entry.availablePax}</td>
				<td>${entry.closingDate}</td>
				<td>${entry.comments}</td>
				<td>${entry.estimatedCost}</td>
			</tr>
		</c:forEach>
	</table>
	</section>
	<hr><br>
	<footer>
		<%@ include file="contacto.jsp"%>
   </footer>
</body>
</html>