package uo.sdi.acciones;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.model.Application;
import uo.sdi.model.Rating;
import uo.sdi.model.Seat;
import uo.sdi.model.SeatStatus;
import uo.sdi.model.Trip;
import uo.sdi.model.User;
import uo.sdi.persistence.ApplicationDao;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.RatingDao;
import uo.sdi.persistence.SeatDao;
import uo.sdi.persistence.TripDao;
import uo.sdi.persistence.UserDao;
import alb.util.log.Log;

public class MostrarViajeAction implements Accion{
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		Long viajeId = Long.parseLong((request.getQueryString().split("="))[1]);
		request.setAttribute("viajeId", viajeId);
		
		TripDao tDao = PersistenceFactory.newTripDao();
		UserDao uDao = PersistenceFactory.newUserDao();
		ApplicationDao aDao = PersistenceFactory.newApplicationDao();
		SeatDao sDao = PersistenceFactory.newSeatDao();
		RatingDao rDao = PersistenceFactory.newRatingDao();
		
		Trip t;
		User promotor;
		List<User> participantes = new ArrayList<User>();
		List<User> solicitantes = new ArrayList<User>();
		
		List<Rating> calificacionesSobrePromotor = new ArrayList<Rating>();
		List<Rating> calificacionesSobreParticipantes = new ArrayList<Rating>();
		List<Map<String, String>> mapaCalificacionesSobrePromotor = new ArrayList<Map<String, String>>();
		List<Map<String, String>> mapaCalificacionesSobreParticipantes = new ArrayList<Map<String, String>>();
		
		try {
			//obtener promotor del viaje
			t = tDao.findById(viajeId);
			promotor = uDao.findById(t.getPromoterId());
			request.setAttribute("promotor", promotor);
			//obtener participantes del viaje en tseat
			for(Seat s: sDao.findByTrip(viajeId))
				if(s.getStatus()==SeatStatus.ACCEPTED && !s.getUserId().equals(promotor.getId()))
					participantes.add(uDao.findById(s.getUserId()));
			request.setAttribute("participantes", participantes);
			//obtener solicitantes del viaje
			for(Application a : aDao.findByTripId(viajeId))
				solicitantes.add(uDao.findById(a.getUserId()));
			request.setAttribute("solicitantes", solicitantes);
			//calificaciones
			calificacionesSobrePromotor = rDao.findByAboutUserId(promotor.getId());
			for(User u : participantes)
				for(Rating r: rDao.findByAboutUserId(u.getId()))
					calificacionesSobreParticipantes.add(r);
			for (Rating r: calificacionesSobrePromotor){
				Map<String, String> m = new HashMap<String, String>();
				m.put("calificacionAl", uDao.findById(r.getSeatAboutUserId()).getLogin());
				m.put("calificacionDel", uDao.findById(r.getSeatFromUserId()).getLogin());
				m.put("valoracion", String.valueOf(r.getValue()));
				m.put("comentario", String.valueOf(r.getComment()));
				mapaCalificacionesSobrePromotor.add(m);
			}
			for (Rating r: calificacionesSobreParticipantes){
				Map<String, String> m = new HashMap<String, String>();
				m.put("calificacionAl", uDao.findById(r.getSeatAboutUserId()).getLogin());
				m.put("calificacionDel", uDao.findById(r.getSeatFromUserId()).getLogin());
				m.put("valoracion", String.valueOf(r.getValue()));
				m.put("comentario", String.valueOf(r.getComment()));
				mapaCalificacionesSobreParticipantes.add(m);
			}
					
			request.setAttribute("listaCalificacionesPromotor", mapaCalificacionesSobrePromotor);
			request.setAttribute("listaCalificacionesParticipantes", mapaCalificacionesSobreParticipantes);
			
			Log.debug("Obtenida lista de calificaciones sobre promotor que contiene [%d] calificaciones", calificacionesSobrePromotor.size());
			Log.debug("Obtenida lista de calificaciones sobre los participantes que contiene [%d] calificaciones", calificacionesSobreParticipantes.size());
		}catch (Exception e) {
			Log.error("Algo ha ocurrido obteniendo lista de calificaciones");
		}
		return "EXITO";
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}

}
