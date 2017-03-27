package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.model.User;
import uo.sdi.model.UserStatus;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.UserDao;
import alb.util.log.Log;

public class RegistrarseAction implements Accion{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String resultado = "EXITO";  
		String identificadorUsuario = (String)request.getParameter("identificadorUsuario");
		String nombre = (String)request.getParameter("nombre");
		String apellidos=(String)request.getParameter("apellidos");
		String correoElectronico=(String)request.getParameter("correoElectronico");
		String contrasena = (String)request.getParameter("contrasena");
		String repetirContrasena = (String)request.getParameter("repetirContrasena");
		UserDao dao = PersistenceFactory.newUserDao();
		if(identificadorUsuario.equals("") || nombre.equals("") || apellidos.equals("") 
				|| correoElectronico.equals("") || contrasena.equals("") || 
				repetirContrasena.equals("") || !correoElectronico.contains("@")){
			Log.debug("Existen campos vacios o el correoElectronico no es valido");
			resultado = "FRACASO";
			request.setAttribute("errorRegistrar", "Existen campos vacios o el correoElectronico no es valido");
		}else{
			if(contrasena.equals(repetirContrasena)){
				if(dao.findByLogin(identificadorUsuario)==null){
					User usuarioNuevo = new User();
					usuarioNuevo.setLogin(identificadorUsuario);
					usuarioNuevo.setName(nombre);
					usuarioNuevo.setSurname(apellidos);
					usuarioNuevo.setEmail(correoElectronico);
					usuarioNuevo.setPassword(contrasena);
					usuarioNuevo.setStatus(UserStatus.ACTIVE);
					try {
						dao.save(usuarioNuevo);
						Log.debug("El nuevo usuario [%s] ha sido registrado con exito",usuarioNuevo.getLogin());
					}
					catch (Exception e) {
						Log.error("Ha ocurrido un error intentando registrar [%s]", usuarioNuevo);
						resultado = "FRACASO";
						request.setAttribute("errorRegistrar", "Ha ocurrido un error intentando registrar " + usuarioNuevo);
					}
				}else{
					Log.error("El identificador del usuario ha sido usado [%s]", identificadorUsuario);
					resultado = "FRACASO";	
					request.setAttribute("errorRegistrar", "El identificador del usuario ha sido usado " + identificadorUsuario);
				}
			}else {
				Log.debug("Las contraseñas no coinciden [%s] - [%s] ", contrasena, repetirContrasena);
				resultado = "FRACASO";
				request.setAttribute("errorRegistrar", "Las contraseñas no coinciden " + contrasena + " - " + repetirContrasena);
			}
		}
	
		request.setAttribute("resultado", resultado);//?
		return resultado;
	}

}
