package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.util.log.Log;
import uo.sdi.model.Trip;
import uo.sdi.model.TripStatus;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.TripDao;

public class CancelarViajeAction implements Accion{

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		Long viajeId = Long.parseLong(request.getQueryString().split("=")[1]);
		TripDao tDao = PersistenceFactory.newTripDao();
		try{
			Trip t = tDao.findById(viajeId);
			t.setStatus(TripStatus.CANCELLED);
			tDao.update(t);
			Log.info("Se ha cancelado correctamente el viaje [%s]", viajeId);
			request.setAttribute("mensajeCancelarViaje", "Se ha cancelado correctamente el viaje " + viajeId);
		}catch(Exception e){
			Log.info("Ha ocurrido un error desconocido al intentar cancelar viaje");
		}
		
		return "EXITO";
	}

}
