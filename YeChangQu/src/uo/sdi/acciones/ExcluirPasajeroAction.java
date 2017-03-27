package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.util.log.Log;
import uo.sdi.model.Seat;
import uo.sdi.model.SeatStatus;
import uo.sdi.model.Trip;
import uo.sdi.model.TripStatus;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.SeatDao;
import uo.sdi.persistence.TripDao;
import uo.sdi.persistence.UserDao;

public class ExcluirPasajeroAction implements Accion{

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String[] informacion = request.getQueryString().split("&");
		Long userId = Long.parseLong(informacion[0].split("=")[1]);
		Long viajeId = Long.parseLong(informacion[1].split("=")[1]);
		
		TripDao tDao = PersistenceFactory.newTripDao();
		SeatDao sDao = PersistenceFactory.newSeatDao();
		UserDao uDao = PersistenceFactory.newUserDao();
		
		try{//puede excluir usuario en open, closed y cancelled
			Seat s = sDao.findByUserAndTrip(userId, viajeId);
			s.setStatus(SeatStatus.EXCLUDED);
			sDao.update(s);//guarda s al BD con status actualizado, importante
			Trip t = tDao.findById(viajeId);
			if(t.getAvailablePax() < t.getMaxPax()-1)//plaza libre sea menor que (max-1)
				t.setAvailablePax(t.getAvailablePax()+1);
			if(t.getStatus() == TripStatus.CLOSED)//si este caso justo el viaje esta lleno y quitas pasajero
				t.setStatus(TripStatus.OPEN);	//el viaje cambia de estado al open
			tDao.update(t);
			Log.info("Se ha excluido correctamente el usuario con id [%s] de la viaje [%s]", userId, viajeId);
			request.setAttribute("mensajeExcluirPasajero", "Se ha excluido correctamente el usuario " + 
					uDao.findById(userId).getLogin() + " de la viaje " + viajeId);
		}catch(Exception e){
			Log.info("Ha ocurrido un error desconocido al intentar excluir usuario");
		}
		
		return "EXITO";
	}

}
