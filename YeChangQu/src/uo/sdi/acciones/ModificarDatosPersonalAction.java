package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.model.User;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.UserDao;
import alb.util.log.Log;

public class ModificarDatosPersonalAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String nuevoNombre=(String)request.getParameter("name");
		String nuevoApellidos=(String)request.getParameter("surname");
		String nuevoEmail=(String)request.getParameter("email");
		String passwordAntiguo=(String)request.getParameter("passwordAntiguo");
		String passwordNuevo=(String)request.getParameter("passwordNuevo");
		String repetirPasswordNuevo=(String)request.getParameter("repetirPasswordNuevo");
		HttpSession session=request.getSession();
		User usuario=(User)session.getAttribute("user");//obtiene el usuario que esta en la sesion
		if(nuevoNombre.equals("") || nuevoApellidos.equals("") || nuevoEmail.equals("")){
			request.setAttribute("mensajeModificarDatos", "ERROR, No has podido modificarDatos, ya que existen campos obligatorios vacios");
			Log.info("No has podido modificarDatos, ya que existen campos obligatorios vacios");
			return "FRACASO";
		}
		if(!nuevoEmail.contains("@")){
			request.setAttribute("mensajeModificarDatos", "ERROR, No has podido modificarDatos, ya que el nuevo email es incorrecto");
			Log.info("No has podido modificarDatos, ya que el nuevo email es incorrecto");
			return "FRACASO";//aqui el metodo termina? claro se termina con el return
		}
		if(!passwordAntiguo.equals("")){
			if(passwordAntiguo.equals(usuario.getPassword())){
				if(!passwordNuevo.equals("") && !repetirPasswordNuevo.equals("")){
					if(passwordNuevo.equals(repetirPasswordNuevo)){
						usuario.setPassword(passwordNuevo);
					}else{
						request.setAttribute("mensajeModificarDatos", "ERROR, No has podido modificarDatos, ya que las contrasenas no coinciden");
						Log.info("No has podido modificarDatos, ya que las contrasenas no coinciden");
						return "FRACASO";
					}	
				}else{
					request.setAttribute("mensajeModificarDatos", "ERROR, No has podido modificarDatos, ya que no has introducido nada de contrasena nuevo");
					Log.info("No has podido modificarDatos, ya que no has introducido nada de contrasena nuevo");
					return "FRACASO";//aqui el metodo termina? claro se termina con el return
				}
			}else{
				request.setAttribute("mensajeModificarDatos", "ERROR, No has podido modificarDatos, ya que has introducido mal la contrasena antiguo");
				Log.info("No has podido modificarDatos, ya que has introducido mal la contrasena antiguo");
				return "FRACASO";//aqui el metodo termina? claro se termina con el return
			}
		}else{
			if(!passwordNuevo.equals("") || !repetirPasswordNuevo.equals("")){
				request.setAttribute("mensajeModificarDatos", "ERROR, No has podido modificarDatos, no has introducido password antiguo e has introducido algo de password nuevo");
				Log.info("No has podido modificarDatos, no has introducido password antiguo e has introducido algo de password nuevo");
				return "FRACASO";
			}
		}
		
		usuario.setName(nuevoNombre);
		usuario.setSurname(nuevoApellidos);
		usuario.setEmail(nuevoEmail);
		try {
			UserDao dao = PersistenceFactory.newUserDao();
			dao.update(usuario);
			request.setAttribute("mensajeModificarDatos", "Enhorabuena, Has modificado correctamente sus datos");
			Log.debug("Modificado nombre de [%s] con el valor [%s]", usuario.getLogin(), nuevoNombre);
			Log.debug("Modificado apellidos de [%s] con el valor [%s]", usuario.getLogin(), nuevoApellidos);
			Log.debug("Modificado email de [%s] con el valor [%s]", usuario.getLogin(), nuevoEmail);
		}
		catch (Exception e) {
			Log.error("Algo ha ocurrido actualizando datos del [%s]", usuario.getLogin());
		}
		return "EXITO";
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	
}
