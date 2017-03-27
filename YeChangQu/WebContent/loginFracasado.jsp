<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<html>
<head>
<title>ShareMyTrip-LoginFracasado</title>
<link href="css/estilo.css" rel="stylesheet" type="text/css">
</head>
<body>
	<header><h1>ShareMyTrip</h1>
 	<h2>ERROR, Lo sentimos!</h2></header>
 	<section>
 		<p>No has podido iniciar sesion, ya has iniciado la sesion o indrodujo de forma incorrecta 
 		su identificador y contrasena.</p>
 		<br/>
 		<strong>CAUSA: ${requestScope.errorLogin}</strong> <%-- elemento del request.setAtributte puede ser directamente usado por Expression Languaje --%>
		<br/>
		<a href="login.jsp"><input type="button" value="Volver"/></a>
	</section>
	<footer>
		<%@ include file="contacto.jsp"%>
   </footer>
</body>
</html>