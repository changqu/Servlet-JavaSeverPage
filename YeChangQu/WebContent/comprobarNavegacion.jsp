<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${requestScope.jspSiguiente==null}">
	<jsp:forward page="navegacionInvalida" /><%-- como hace que redirige la pagina a page navegacionInvalida, como no existe produce un excepcion, cogido por Controlador y vamos al login --%>
</c:if>
