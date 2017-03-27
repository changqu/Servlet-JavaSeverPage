<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp"%>
<%-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd"> --%>
<html>
<head>
<title>ShareMyTrip-Modificar viaje</title>
<link href="css/estilo.css" rel="stylesheet" type="text/css">
</head>
<body>
	<form action="modificarViaje?idViaje=${viaje.id}" method="POST">
		<header><h1>ShareMyTrip</h1>
			<h2>Modificar viaje</h2></header>
		<section>
			<table>
		 		<tr>
		 			<td><h3 align="center">Lugar de salida </h3></td>
		 		</tr>
		    	<tr> 
		    		<td align="right">Ciudad*</td>
			    	<td><input type="text" name="salidaCiudad" size="15" value="${viaje.departure.city}"></td>
		      	</tr>
		      	<tr> 
		    		<td align="right">Direccion*</td>
			    	<td><input type="text" name="salidaDireccion" size="15" value="${viaje.departure.address}"></td>
		      	</tr>
		      	<tr> 
		    		<td align="right">Provincia*</td>
			    	<td><input type="text" name="salidaProvincia" size="15" value="${viaje.departure.state}"></td>
		      	</tr>
		      	<tr> 
		    		<td align="right">Pais*</td>
			    	<td><input type="text" name="salidaPais" size="15" value="${viaje.departure.country}"></td>
		      	</tr>
		     	<tr> 
		    		<td align="right">Codigo Postal*</td>
			    	<td><input type="text" name="salidaCodigoPostal" size="15" value="${viaje.departure.zipCode}"></td>
		      	</tr>
		     	<tr> 
		    		<td align="right">Longitud (opcional)</td>
			    	<td><input type="text" name="salidaLon" size="15" value="${viaje.departure.waypoint.lon}"></td>
		      	</tr>
		     	<tr> 
		    		<td align="right">Latitud (opcional)</td>
			    	<td><input type="text" name="salidaLat" size="15" value="${viaje.departure.waypoint.lat}"></td>
		      	</tr>
		      	<tr> 
		    		<td align="right">Date salida (original)</td>
			    	<td><input type="text" name="dateSalida" size="15" value="${viaje.departureDate}"></td>
		      	</tr>
		     	<tr> 
		    		<td align="right">Nueva fecha de salida (formato:yyyy-mm-dd)</td>
			    	<td><input type="text" name="salidaFecha" size="15"></td>
		      	</tr>
		     	<tr> 
		    		<td align="right">Nueva hora de salida(formato:hh-mm-ss)</td>
			    	<td><input type="text" name="salidaHora" size="15"></td>
		      	</tr> 
		 		<tr>
		 			<td><h3 align="center">Lugar de llegada </h3></td>
		 		</tr>
		    	<tr> 
		    		<td align="right">Ciudad*</td>
			    	<td><input type="text" name="llegadaCiudad" size="15" value="${viaje.destination.city}"></td>
		      	</tr>
		      	<tr> 
		    		<td align="right">Direccion*</td>
			    	<td><input type="text" name="llegadaDireccion" size="15" value="${viaje.destination.address}"></td>
		      	</tr>
		      	<tr> 
		    		<td align="right">Provincia*</td>
			    	<td><input type="text" name="llegadaProvincia" size="15" value="${viaje.destination.state}"></td>
		      	</tr>
		      	<tr> 
		    		<td align="right">Pais*</td>
			    	<td><input type="text" name="llegadaPais" size="15" value="${viaje.destination.country}"></td>
		      	</tr>
		     	<tr> 
		    		<td align="right">Codigo Postal*</td>
			    	<td><input type="text" name="llegadaCodigoPostal" size="15" value="${viaje.destination.zipCode}"></td>
		      	</tr>
		     	<tr> 
		    		<td align="right">Longitud (opcional)</td>
			    	<td><input type="text" name="llegadaLon" size="15" value="${viaje.destination.waypoint.lon}"></td>
		      	</tr>
		     	<tr> 
		    		<td align="right">Latitud (opcional)</td>
			    	<td><input type="text" name="llegadaLat" size="15" value="${viaje.destination.waypoint.lat}"></td>
		      	</tr>
		      	<tr> 
		    		<td align="right">Date llegada (original)</td>
			    	<td><input type="text" name="dateLlegada" size="15" value="${viaje.arrivalDate}"></td>
		      	</tr>
		     	<tr> 
		    		<td align="right">Nueva fecha de llegada(formato:yyyy-mm-dd)</td>
			    	<td><input type="text" name="llegadaFecha" size="15"></td>
		      	</tr>
		     	<tr> 
		    		<td align="right">Nueva hora de llegada(formato:hh-mm-ss)</td>
			    	<td><input type="text" name="llegadaHora" size="15"></td>
		      	</tr>
		      	<tr>
		 			<td><h3 align="center">Otros datos </h3></td>
		 		</tr>
		 		<tr> 
		    		<td align="right">Date limite (original)</td>
			    	<td><input type="text" name="dateLimite" size="15" value="${viaje.closingDate}"></td>
		      	</tr>
		      	<tr> 
		    		<td align="right">Nueva fecha limite para apuntarse(formato:yyyy-mm-dd)</td>
			    	<td><input type="text" name="fechaLimite" size="15"></td>
		      	</tr>
		      	<tr> 
		    		<td align="right">Nueva hora de limite para apuntarse(formato:hh-mm-ss)</td>
			    	<td><input type="text" name="horaLimite" size="15"></td>
		      	</tr>
		      	<tr> 
		    		<td align="right">Coste total, repartir para todos*</td>
			    	<td><input type="text" name="coste" size="15" value="${viaje.estimatedCost}"></td>
		      	</tr>
		      	<tr> 
		    		<td align="right">Descripcion(opcional)</td>
			    	<td><input type="text" name="descripcion" size="19"  value="${viaje.comments}"></td>
			    </tr>
		      	<%--<tr> esto mejor no deje que se lo modifique produce un lio que no puedes controlar
		    		<td align="right">Numero plazas maximo*</td>
			    	<td><input type="text" name="plazasMaximo" size="15" value="${viaje.maxPax}"></td>
		      	</tr>
		      	<tr> 
		    		<td align="right">Numero plazas disponibles*</td>
			    	<td><input type="text" name="plazasDisponibles" size="15" value="${viaje.availablePax}"></td>
		      	</tr>--%>
		      	<tr>
		    	    <td align="right"><input type="submit" value="ModificarViaje"/></td>
		      	</tr>
	      </table>
	      <br/>
	    Los campos * son obligatorios, solo si quieres modificar dates debes introducir nueva fecha y nueva hora ambos a
	    la vez, si no, puedes dejar tal como estaba.
      	<br/>
      	<a href="misViajes"><input type="button" value="Volver"/></a>
		</section>
	</form>
	<footer>
		<%@ include file="contacto.jsp"%>
   </footer>
</body>
</html>