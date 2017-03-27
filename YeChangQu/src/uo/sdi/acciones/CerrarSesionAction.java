package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.model.User;
import alb.util.log.Log;

public class CerrarSesionAction implements Accion{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		User usuario = (User) session.getAttribute("user");
		
		session.invalidate();//eso hace que el rol despues sea del publico
		
		Log.debug("%s ha cerrado sesion", usuario.getLogin());
		return "EXITO";
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}

}
