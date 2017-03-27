package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.model.Seat;
import uo.sdi.model.SeatStatus;
import uo.sdi.model.Trip;
import uo.sdi.model.TripStatus;
import uo.sdi.persistence.ApplicationDao;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.SeatDao;
import uo.sdi.persistence.TripDao;
import uo.sdi.persistence.UserDao;
import alb.util.log.Log;

public class ConfirmarPasajeroAction implements Accion{

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String[] informacion = request.getQueryString().split("&");
		Long userId = Long.parseLong(informacion[0].split("=")[1]);
		Long viajeId = Long.parseLong(informacion[1].split("=")[1]);
		Long[] l = {userId, viajeId};
		
		TripDao tDao = PersistenceFactory.newTripDao();
		ApplicationDao aDao = PersistenceFactory.newApplicationDao();
		SeatDao sDao = PersistenceFactory.newSeatDao();
		UserDao uDao = PersistenceFactory.newUserDao();
		
		Seat s = new Seat();
		s.setComment(null);
		s.setStatus(SeatStatus.ACCEPTED);
		s.setTripId(viajeId);
		s.setUserId(userId);
		Trip t;
		try{
			t = tDao.findById(viajeId);
			if(t.getAvailablePax()>0){//si plaza libre queda
				aDao.delete(l);
				sDao.save(s);
				t.setAvailablePax(t.getAvailablePax()-1);
				if(t.getAvailablePax()==0)
					t.setStatus(TripStatus.CLOSED);
				tDao.update(t);
				Log.info("Se ha confirmado correctamente el usuario [%s] de la viaje [%s]", uDao.findById(userId).getLogin(), viajeId);
				request.setAttribute("mensajeConfirmarPasajero", "Se ha confirmado correctamente el usuario con id "+userId+" al viaje "+viajeId);
			}else{
				Log.info("Error, No se puede confirmar usuario con id [%s] de la viaje [%s] dado que no hay plaza libre", userId, viajeId);
				request.setAttribute("mensajeConfirmarPasajero", "Error, No se ha confirmado el usuario " + uDao.findById(userId).getLogin() 
						+ " al viaje " + viajeId + " dado que no existe plaza");
			}
		}catch(Exception e){
			Log.info("Ha ocurrido un error desconocido al intentar confirmar usuario");
		}
		
		return "EXITO";
	}

}
