package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.model.User;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.UserDao;
import alb.util.log.Log;

public class ValidarseAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		String identificadorUsuario=request.getParameter("identificadorUsuario");
		String contrasena=request.getParameter("contrasena");
		HttpSession session=request.getSession();
		if (session.getAttribute("user")==null) {//si session no esta iniciada
			UserDao dao = PersistenceFactory.newUserDao();
			User userByLogin = dao.findByLogin(identificadorUsuario);
			if (userByLogin!=null) {
				if(userByLogin.getPassword().equals(contrasena)){
					session.setAttribute("user", userByLogin);
					synchronized(request.getServletContext()){
						int contador=Integer.parseInt((String)request.getServletContext().getAttribute("contador"));
						request.getServletContext().setAttribute("contador", String.valueOf(contador+1));
					}
					Log.info("El usuario [%s] ha iniciado sesión",identificadorUsuario);
				}else{
					session.invalidate();
					Log.info("El usuario [%s] ha indroducido mal su contrasena", identificadorUsuario);
					resultado="FRACASO";
					request.setAttribute("errorLogin", "El usuario " + identificadorUsuario + " ha indroducido mal su contrasena");
				}
			}
			else {
				session.invalidate();
				Log.info("El usuario [%s] no está registrado", identificadorUsuario);
				resultado="FRACASO";
				request.setAttribute("errorLogin", "El usuario " + identificadorUsuario + " no está registrado");
			}
		}
		else{
			User u = (User) session.getAttribute("user");
			if (!identificadorUsuario.equals(u.getLogin())) {//teniedo sesion inicializado como uno y ahora inicia la sesion con otro
				Log.info("Se ha intentado iniciar sesión como [%s] teniendo la sesión iniciada como [%s]",identificadorUsuario,((User)session.getAttribute("user")).getLogin());
				//aqui estaba el error debe poner lo antes del session.invalidate()
				request.setAttribute("errorLogin", "Se ha intentado iniciar sesión como " + identificadorUsuario + " teniendo la sesión iniciada como " + ((User)session.getAttribute("user")).getLogin());
				session.invalidate();
				resultado="FRACASO";
			}
		}
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	
}
