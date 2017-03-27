<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp" %> 
<%-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd"> --%>
<html>
<head>
<title>ShareMyTrip-Pagina principal del usuario</title>
<link href="css/estilo.css" rel="stylesheet" type="text/css">
</head>
<body>
	<header><h1>ShareMyTrip</h1>
 	<h2>Bienvenido, ${user.login}</h2></header>
	<%@ include file="indiceRegistrado.jsp"%>
 	<section>
	<br/>
	Estas en la pagina principal!	
	<br/>
	<Strong>${mensajeModificarDatos}</Strong>	
	<br/>
	<Strong>${mensajeRegistrarViaje}</Strong>	
	<br/>
	<Strong>${mensajeSolicitarPlaza}</Strong>	
	<br/>
	<Strong>${mensajeCancelarSolicitud}</Strong>
	<br/>
	<Strong>${mensajeComentarViaje}</Strong>
	<br/>
	<Strong>${mensajeExcluirPasajero}</Strong>
	<br/>
	<Strong>${mensajeConfirmarPasajero}</Strong>
	<br/>
	<Strong>${MensajeDejarComentarioAlPromotor}</Strong>
	<br/>
	<Strong>${mensajeCancelarViaje}</Strong>
	<br/>
	<Strong>${mensajeModificarViaje}</Strong>
	<br/>
	Es Vd el usuario numero: ${contador}  <%--${date}--%>
	</section>
	<footer>
		<%@ include file="contacto.jsp"%>
    </footer>
</body>
</html>
