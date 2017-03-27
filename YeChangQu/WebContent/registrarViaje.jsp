<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="comprobarNavegacion.jsp" %> 
<%-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd"> --%>
<html>
<head> 
<title>ShareMyTrip-RegistrarViaje</title><%--para navegador, nombre de la pagina --%>
<link href="css/estilo.css" rel="stylesheet" type="text/css">
</head> 
<body>
  <form action="registrarViaje" method="post">
	
	<header><h1>ShareMyTrip</h1>
 	<h2>Registrar viaje</h2></header>
 	<hr><br>
 	<%@ include file="indiceRegistrado.jsp"%>
 	<section>
 	<strong>Los campos* son obligatorios</strong> <br/>
 	<table>
 		<tr>
 			<td><h3 align="center">Lugar de salida </h3></td>
 		</tr>
    	<tr> 
    		<td align="right">Ciudad*</td>
	    	<td><input type="text" name="salidaCiudad" size="15"></td>
      	</tr>
      	<tr> 
    		<td align="right">Direccion*</td>
	    	<td><input type="text" name="salidaDireccion" size="15"></td>
      	</tr>
      	<tr> 
    		<td align="right">Provincia*</td>
	    	<td><input type="text" name="salidaProvincia" size="15"></td>
      	</tr>
      	<tr> 
    		<td align="right">Pais*</td>
	    	<td><input type="text" name="salidaPais" size="15"></td>
      	</tr>
     	<tr> 
    		<td align="right">Codigo Postal*</td>
	    	<td><input type="text" name="salidaCodigoPostal" size="15"></td>
      	</tr>
     	<tr> 
    		<td align="right">Longitud (opcional)</td>
	    	<td><input type="text" name="salidaLon" size="15"></td>
      	</tr>
     	<tr> 
    		<td align="right">Latitud (opcional)</td>
	    	<td><input type="text" name="salidaLat" size="15"></td>
      	</tr>
     	<tr> 
    		<td align="right">Fecha de salida (formato:yyyy-mm-dd)*</td>
	    	<td><input type="text" name="salidaFecha" size="15"></td>
      	</tr>
     	<tr> 
    		<td align="right">Hora de salida(formato:hh-mm-ss)*</td>
	    	<td><input type="text" name="salidaHora" size="15"></td>
      	</tr> 
 		<tr>
 			<td><h3 align="center">Lugar de llegada </h3></td>
 		</tr>
    	<tr> 
    		<td align="right">Ciudad*</td>
	    	<td><input type="text" name="llegadaCiudad" size="15"></td>
      	</tr>
      	<tr> 
    		<td align="right">Direccion*</td>
	    	<td><input type="text" name="llegadaDireccion" size="15"></td>
      	</tr>
      	<tr> 
    		<td align="right">Provincia*</td>
	    	<td><input type="text" name="llegadaProvincia" size="15"></td>
      	</tr>
      	<tr> 
    		<td align="right">Pais*</td>
	    	<td><input type="text" name="llegadaPais" size="15"></td>
      	</tr>
     	<tr> 
    		<td align="right">Codigo Postal*</td>
	    	<td><input type="text" name="llegadaCodigoPostal" size="15"></td>
      	</tr>
     	<tr> 
    		<td align="right">Longitud (opcional)</td>
	    	<td><input type="text" name="llegadaLon" size="15"></td>
      	</tr>
     	<tr> 
    		<td align="right">Latitud (opcional)</td>
	    	<td><input type="text" name="llegadaLat" size="15"></td>
      	</tr>
     	<tr> 
    		<td align="right">Fecha de llegada(formato:yyyy-mm-dd)*</td>
	    	<td><input type="text" name="llegadaFecha" size="15"></td>
      	</tr>
     	<tr> 
    		<td align="right">Hora de llegada(formato:hh-mm-ss)*</td>
	    	<td><input type="text" name="llegadaHora" size="15"></td>
      	</tr>
      	<tr>
 			<td><h3 align="center">Otros datos </h3></td>
 		</tr>
      	<tr> 
    		<td align="right">Fecha limite para apuntarse(formato:yyyy-mm-dd)*</td>
	    	<td><input type="text" name="fechaLimite" size="15"></td>
      	</tr>
      	<tr> 
    		<td align="right">Hora de limite para apuntarse(formato:hh-mm-ss)*</td>
	    	<td><input type="text" name="horaLimite" size="15"></td>
      	</tr>
      	<tr> 
    		<td align="right">Coste total, repartir para todos*</td>
	    	<td><input type="text" name="coste" size="15"></td>
      	</tr>
      	<tr> 
    		<td align="right">Descripcion(opcional)</td>
	    	<td><textarea id ="descripcion" name="descripcion" rows="6" cols="28"></textarea></td>
	      	</tr>
      	<tr> 
    		<td align="right">Numero plazas maximo*</td>
	    	<td><input type="text" name="plazasMaximo" size="15"></td>
      	</tr>
      	<tr> 
    		<td align="right">Numero plazas disponibles*</td>
	    	<td><input type="text" name="plazasDisponibles" size="15"></td>
      	</tr>
      	<tr> 
    		<td align="right">Los campos * son obligatorios</td>
      	</tr>
      	<tr>
    	    <td align="right"><input type="submit" value="Registrar viaje"/></td>
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