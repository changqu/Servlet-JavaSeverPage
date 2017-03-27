package uo.sdi.acciones;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.model.Rating;
import uo.sdi.model.Seat;
import uo.sdi.model.SeatStatus;
import uo.sdi.model.User;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.RatingDao;
import uo.sdi.persistence.SeatDao;
import uo.sdi.persistence.TripDao;
import uo.sdi.persistence.UserDao;
import alb.util.log.Log;

public class ComentarViajeAction implements Accion{
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="FRACASO";
		User user = (User) request.getSession().getAttribute("user");
		
		String identificadorUsuario = (String)request.getParameter("identificadorUsuario");
		String punto = (String)request.getParameter("punto");
		String comentario = (String)request.getParameter("comentario");
		
		if(identificadorUsuario.equals("") || punto.equals("") || comentario.equals("")){
			Log.info("ERROR al intentar dejar comentario, existe campos vacios al intentar dejar comentario");
			request.setAttribute("mensajeComentarViaje", "ERROR al intentar dejar comentario, existe campos vacios al intentar dejar comentario");
			return resultado;
		}
		
		Long viajeId = Long.parseLong((request.getQueryString().split("="))[1]);
		request.setAttribute("viajeId", viajeId);
		TripDao tDao = PersistenceFactory.newTripDao();
		UserDao uDao = PersistenceFactory.newUserDao();
		SeatDao sDao = PersistenceFactory.newSeatDao();
		RatingDao rDao = PersistenceFactory.newRatingDao();
		User promotor;
		List<User> participantes = new ArrayList<User>();
		
		try{
			//obtener el promotor
			promotor = uDao.findById(tDao.findById(viajeId).getPromoterId());
			//obtener los participantes
			for(Seat s: sDao.findByTrip(viajeId))
				if(s.getStatus()==SeatStatus.ACCEPTED && !s.getUserId().equals(promotor.getId()))
					participantes.add(uDao.findById(s.getUserId()));
			
			boolean validoUsuario=false;
			for(User u: participantes)//es participante
				if(u.getLogin().equals(identificadorUsuario))
					validoUsuario=true;
			if(identificadorUsuario.equals(promotor.getLogin()))//es promotor
				validoUsuario=true;
			if(identificadorUsuario.equals(user.getLogin()) || !validoUsuario){
				Log.info("ERROR al intentar dejar comentario, esta dejando comentario a si mismo o miembros que no sea del viaje [%s]",viajeId);
				request.setAttribute("mensajeComentarViaje", "ERROR al intentar dejar comentario, esta dejando comentario a si mismo o miembros que no sea del viaje " + viajeId);
				return resultado;
			}
			
			Integer p;
			try{
				p = Integer.parseInt(punto);
				if(p>5 || p<1){
					Log.info("ERROR al intentar dejar comentario, la puntuacion debe ser entre 1 y 5");
					request.setAttribute("mensajeComentarViaje", "ERROR al intentar dejar comentario, la puntuacion debe ser entre 1 y 5");
					return resultado;
				}
			}catch(Exception e){
				Log.info("ERROR al intentar dejar comentario, para puntuacion debe introducir numero de 1 al 5");
				request.setAttribute("mensajeComentarViaje", "ERROR al intentar dejar comentario, para puntuacion debe introducir numero de 1 al 5");
				return resultado;
			}
			
			Rating r = new Rating();
			r.setComment(comentario); r.setValue(p);
			r.setSeatAboutTripId(viajeId); r.setSeatFromTripId(viajeId);
			r.setSeatAboutUserId(uDao.findByLogin(identificadorUsuario).getId());
			r.setSeatFromUserId(user.getId());
			
			rDao.save(r);
			
			Log.info("Dejando comentario al usuario [%s] para el viaje [%s]",identificadorUsuario, viajeId);
			request.setAttribute("mensajeComentarViaje", "Has dejado comentario al usuario "+ identificadorUsuario +" correctamente");
			
		}catch (Exception e) {
			Log.error("Algo ha ocurrido a la hora de comentar viaje");
		}
		
		return "EXITO";
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
