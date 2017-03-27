package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.model.Application;
import uo.sdi.model.Seat;
import uo.sdi.model.User;
import uo.sdi.persistence.ApplicationDao;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.SeatDao;
import alb.util.log.Log;

public class SolicitarPlazaAction implements Accion{

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado = "FRACASO";
		Long viajeId = Long.parseLong((((String)request.getQueryString()).split("="))[1]);
		Long userId = ((User)request.getSession().getAttribute("user")).getId();
		Long[] l = {userId, viajeId};
		ApplicationDao aDao = PersistenceFactory.newApplicationDao();
		SeatDao sDao = PersistenceFactory.newSeatDao();

		try {
			Application a = aDao.findById(l);
			Seat s = sDao.findById(l);
			Application application = new Application();
			if(a==null && s==null){
				application.setTripId(viajeId);
				application.setUserId(userId);
				aDao.save(application);
				Log.debug("Insertando solicitud del user [%s] al tApplication de la viaje [%s]", userId, viajeId);
				request.setAttribute("mensajeSolicitarPlaza", "Enhorabuena, tu solicitud de viaje ha sido trasmitada");
				resultado = "EXITO";
			}else{
				Log.debug("El user [%s] ya tiene solicitud con el viaje o ya es miembro o esta excluido al viaje [%s]", userId, viajeId);
				request.setAttribute("mensajeSolicitarPlaza", "ERROR, ya tiene solicitud con el viaje o es el ya es miembro o esta excluido al viaje "+viajeId);
			}
		}catch (Exception e) {
			Log.error("Algo ha ocurrido solicitando plaza");
		}
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
}
