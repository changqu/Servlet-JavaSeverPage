<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<body>
	<p><%=application.getInitParameter("nombre")%> | <a href="mailto:<%=application.getInitParameter("correo")%>"> <%=application.getInitParameter("correo")%></a>
	</p>
	<p><%=application.getInitParameter("asignatura")%> | <%=application.getInitParameter("universidad")%></p>
</body>
</html>