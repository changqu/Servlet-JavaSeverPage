package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.util.log.Log;
import uo.sdi.model.Trip;
import uo.sdi.persistence.PersistenceFactory;

public class IrModificarViajeAction implements Accion{

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		Long viajeId = Long.parseLong(request.getQueryString().split("=")[1]);
		try{
			Trip t = PersistenceFactory.newTripDao().findById(viajeId);
			request.setAttribute("viaje", t);
		}catch(Exception e){
			Log.info("Ha ocurrido un error al intentar dirigir a la pagina de modificar viaje");
		}
		return "EXITO";
	}

}
