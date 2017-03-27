<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<html>
<head>
<title>ShareMyTrip-RegistroFracasado</title>
<link href="css/estilo.css" rel="stylesheet" type="text/css">
</head>
<body>
	<header><h1>ShareMyTrip</h1>
 	<h2>ERROR, Lo sentimos!</h2></header>
 	<section>
 		No has podido registrarse, puede que el identificador del usuario esta usado, dejo campo vacio, 
el formato de correoElectronico no es correcto o no coincidieron las contrasenas.
 		<br/>
 		<strong>CAUSA: ${errorRegistrar}</strong>
 		<br/>
		<a href="registrarse.jsp"><input type="button" value="Volver"/></a>
	</section>
	<footer>
		<%@ include file="contacto.jsp"%>
   </footer>
</body>
</html>
