<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp"%>
<%-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd"> --%>
<html>
<head>
<title>ShareMyTrip-Modificar datos personal</title>
<link href="css/estilo.css" rel="stylesheet" type="text/css">
</head>
<body>
	<form action="modificarDatosPersonal" method="POST">
		<header><h1>ShareMyTrip</h1>
			<h2>Modificar datos personal</h2></header>
		<%@ include file="indiceRegistrado.jsp"%>
		<section> 
		<jsp:useBean id="user" class="uo.sdi.model.User" scope="session" />
		<%--aqui el user es el attributo guardado en objeto sesion en la clase validarseAction--%>
		<table>
			<tr>
				<td align="right">Login:</td>
				<td id="login"><jsp:getProperty property="login" name="user" /></td>
			</tr>
			<tr>
				<td align="right">Nombre:</td>
				<td id="name"><input type="text" name="name" size="15"
					value="<jsp:getProperty property="name" name="user" />"></td>
			</tr>
			<tr>
				<td align="right">Apellidos:</td>
				<td id="surname"><input type="text" name="surname" size="15"
					value="<jsp:getProperty property="surname" name="user" />"></td>
			</tr>
			<tr>
				<td align="right">Email:</td>
				<td id="email"><input type="text" name="email" size="15"
					value="<jsp:getProperty property="email" name="user"/>"></td>
			</tr>
			<tr>
				<td align="right">Password Antiguo:(rellenase solo en el caso que quieres modificar contraseña)</td>
				<td id="passwordAntiguo"><input type="password"
					name="passwordAntiguo" size="15"></td>
			</tr>
			<tr>
				<td align="right">Password Nuevo:(rellenase solo en el caso que quieres modificar contraseña)</td>
				<td id="passwordNuevo"><input type="password"
					name="passwordNuevo" size="15"></td>
			</tr>
			<tr>
				<td align="right">Repetir Password Nuevo:(rellenase solo en el caso que quieres modificar contraseña)</td>
				<td id="repetirPasswordNuevo"><input type="password"
					name="repetirPasswordNuevo" size="15"></td>
			</tr>
			<tr>
				<td align="right"><input type="submit" value="Modificar"></td>
				<%-- nombre del boton --%>
			</tr>
		</table>
		<br/>
		</section>
	</form>
	<footer>
		<%@ include file="contacto.jsp"%>
   </footer>
</body>
</html>