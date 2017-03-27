package uo.sdi.acciones;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.util.log.Log;
import uo.sdi.model.Seat;
import uo.sdi.model.SeatStatus;
import uo.sdi.model.User;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.SeatDao;
import uo.sdi.persistence.TripDao;
import uo.sdi.persistence.UserDao;

public class IrComentarViajeAction implements Accion{
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		Long viajeId = Long.parseLong((request.getQueryString().split("="))[1]);
		request.setAttribute("viajeId", viajeId);
		
		TripDao tDao = PersistenceFactory.newTripDao();
		UserDao uDao = PersistenceFactory.newUserDao();
		SeatDao sDao = PersistenceFactory.newSeatDao();
		
		User promotor;
		List<User> participantes = new ArrayList<User>();
		
		try{
			//obtener el promotor
			promotor = uDao.findById(tDao.findById(viajeId).getPromoterId());
			request.setAttribute("promotor", promotor);
			//obtener los participantes
			for(Seat s: sDao.findByTrip(viajeId))
				if(s.getStatus()==SeatStatus.ACCEPTED && !s.getUserId().equals(promotor.getId()))
					participantes.add(uDao.findById(s.getUserId()));
			request.setAttribute("participantes", participantes);
			Log.info("Obtienendo el promotor y las participantes del viaje [%s]", viajeId);
		}catch (Exception e) {
			Log.error("Algo ha ocurrido a la hora de obtener promotor y participantes de viaje");
		}
		
		return "EXITO";
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
