<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp" %>
<html>
<head>
<title>ShareMyTrip-Mis viajes</title>
<link href="css/estilo.css" rel="stylesheet" type="text/css">
</head>
<body>
	<header><h1>ShareMyTrip</h1>
 	<h2>Mis viajes</h2></header>
	<hr><br>
    	<%@ include file="indiceRegistrado.jsp"%>
  	<section>
	<table border="1">
		<tr><td><h3>Historial de viaje</h3></td></tr>
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
			<th>Estado</th>
		</tr>
		<c:forEach var="entry" items="${misHistorialViajes}" varStatus="i">
			<tr id="item_${i.index}">
				<td>${entry.id}</td>
				<td>${entry.departure.city}</td>
				<td>${entry.departureDate}</td>
				<td>${entry.destination.city}</td>
				<td>${entry.arrivalDate}</td>
				<td>${entry.availablePax}</td>
				<td>${entry.closingDate}</td>
				<td>${entry.comments}</td>
				<td>${entry.estimatedCost}</td>
				<td>${entry.status}</td>
				<td><a href="irComentarViaje?idViaje=${entry.id}"><input type="button" value="ComentarViaje"/></a></td>
			</tr>
		</c:forEach>
	</table>
	<br/>
	<table border="1">
		<tr><td><h3>Mi viaje activo(Soy Promotor^_^)</h3></td></tr>
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
			<th>Estado</th>
		</tr>
		<c:forEach var="entry" items="${miViaje}" varStatus="i">
			<tr id="item_${i.index}">
				<td>${entry.id}</td>
				<td>${entry.departure.city}</td>
				<td>${entry.departureDate}</td>
				<td>${entry.destination.city}</td>
				<td>${entry.arrivalDate}</td>
				<td>${entry.availablePax}</td>
				<td>${entry.closingDate}</td>
				<td>${entry.comments}</td>
				<td>${entry.estimatedCost}</td>
				<td>${entry.status}</td>
			</tr>
			<c:forEach var="var" items="${miViajeParticipantes[entry.id]}"> <!-- con foreach corre la mapa -->
				<tr>
					<td><strong>Participante:</strong></td><td>${var.key}</td>
					<td><strong>Comentario:</strong></td><td>${var.value.comment}</td>
					<td><strong>Estado:</strong></td><td>${var.value.status}</td>		
					<td><a href="excluirPasajero?idUsuario=${var.value.userId}&idViaje=${var.value.tripId}"><input type="button" value="ExcluirPasajero"/></a></td>
				</tr>
			</c:forEach>
			<c:forEach var="var" items="${miViajeSolicitantes[entry.id]}"> <!-- con foreach corre la lista -->
				<tr>
					<td><strong>Solicitante:</strong></td><td>${var.login}</td>
					<td><a href="confirmarPasajero?idUsuario=${var.id}&idViaje=${entry.id}"><input type="button" value="ConfirmarPasajero"/></a></td>
				</tr>
			</c:forEach>
			<tr><td><a href="cancelarViaje?viajeId=${entry.id}"><input type="button" value="CancelarViaje"/></a></td></tr>
			<tr><td><a href="irModificarViaje?viajeId=${entry.id}"><input type="button" value="ModificarViaje"/></a></td></tr>
		</c:forEach>
	</table>
	<br/>
	<table border="1">
		<tr><td><h3>Viajes pendientes y aceptados</h3></td></tr>
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
			<th>Estado</th>
		</tr>
		<c:forEach var="entry" items="${viajeRelacionado}" varStatus="i">
			<tr id="item_${i.index}">
				<td>${entry.id}</td>
				<td>${entry.departure.city}</td>
				<td>${entry.departureDate}</td>
				<td>${entry.destination.city}</td>
				<td>${entry.arrivalDate}</td>
				<td>${entry.availablePax}</td>
				<td>${entry.closingDate}</td>
				<td>${entry.comments}</td>
				<td>${entry.estimatedCost}</td>
				<td>${entry.status}</td>
			</tr>
			<tr><td><strong>Promotor: </strong></td><td>${mapaPromotor[entry.id]}</td></tr>
			<c:forEach var="var" items="${viajeRelacionadoParticipantes[entry.id]}">
				<tr>
					<td><strong>Participante:</strong></td><td>${var.key}</td>
					<td><strong>Comentario:</strong></td>
					<c:if test="${var.key==user.login}">
						<td><form action="dejarComentarioAlPromotor?userId=${var.value.userId}&viajeId=${var.value.tripId}" method="post">
								<input type="text" name="comentarioAlPromotor" value="${var.value.comment}">
								<input type="submit" value="DejarComentarioAlPromotor"/>
						</form></td>
					</c:if>
					<c:if test="${var.key!=user.login}">
						<td>${var.value.comment}</td>
					</c:if>
					<td><strong>Estado:</strong></td><td>${var.value.status}</td>
					<c:if test="${var.key==user.login}">
						<td><a href="cancelarSolicitud?idViaje=${entry.id}"><input type="button" value="Cancelar solicitud"/></a></td>	
					</c:if>	
				</tr>
			</c:forEach>
			<c:forEach var="var" items="${viajeRelacionadoSolicitantes[entry.id]}">
				<tr>
					<td><strong>Solicitante:</strong></td><td>${var.login}</td>
					<c:if test="${var.login==user.login}">
						<td><a href="cancelarSolicitud?idViaje=${entry.id}"><input type="button" value="Cancelar solicitud"/></a></td>	
					</c:if>	
				</tr>
			</c:forEach>
		</c:forEach>
	</table>
	<br/>
	<table border="1">
		<tr>
			<th>Id viaje</th>
			<th>Estado de relacion con viaje</th>
		</tr>
		<c:forEach var="entry" items="${mapaRelacion}">
			<tr>
				<td>${entry.key}</td>
				<td>${entry.value}</td>
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