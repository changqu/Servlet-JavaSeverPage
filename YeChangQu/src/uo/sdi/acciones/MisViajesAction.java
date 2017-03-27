package uo.sdi.acciones;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.util.log.Log;
import uo.sdi.model.Application;
import uo.sdi.model.Seat;
import uo.sdi.model.SeatStatus;
import uo.sdi.model.Trip;
import uo.sdi.model.TripStatus;
import uo.sdi.model.User;
import uo.sdi.persistence.ApplicationDao;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.SeatDao;
import uo.sdi.persistence.TripDao;
import uo.sdi.persistence.UserDao;

public class MisViajesAction implements Accion{

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		User user = (User)request.getSession().getAttribute("user");
		TripDao tDao = PersistenceFactory.newTripDao();
		SeatDao sDao = PersistenceFactory.newSeatDao();
		UserDao uDao = PersistenceFactory.newUserDao();
		ApplicationDao aDao = PersistenceFactory.newApplicationDao();
		
		List<Trip> misHistorialViajes = new ArrayList<Trip>();
		List<Trip> miViaje;
		
		try{
			for(Trip t: tDao.findDone()){
				if(t.getPromoterId().equals(user.getId())){//si usuario es promotor
					misHistorialViajes.add(t);
				}else{								
					Long[] l = {user.getId(), t.getId()};
					Seat asiento = sDao.findById(l);//si usuario es admitido en viaje
					if(asiento!=null && asiento.getStatus()==SeatStatus.ACCEPTED)
						misHistorialViajes.add(t);
				}
			}
			Log.info("Obteniedo historial de viaje del usuario [%s] que puede ser promotor o admitido en el viaje", user.getLogin());
			request.setAttribute("misHistorialViajes", misHistorialViajes);
			
			//mi viaje no de estado DONE y solicitud o participantes que hay ahora soy como promotor del viaje
			miViaje = tDao.findNoDoneByPromoter(user.getId());
			request.setAttribute("miViaje", miViaje);
			
			Map<Long, Map<String, Seat>> miViajeParticipantes = mapaUserParticipantes(miViaje, sDao, uDao);
			Map<Long, List<User>> miViajeSolicitantes = mapaUserSolicitantes(miViaje, aDao, uDao);
			request.setAttribute("miViajeParticipantes", miViajeParticipantes);
			request.setAttribute("miViajeSolicitantes", miViajeSolicitantes);
			
			//encontrar todos los viajes relacionados al user sea participante o solicitantes
			List<Trip> viajeRelacionado = new ArrayList<Trip>();
			for(Application a: aDao.findByUserId(user.getId()))
				viajeRelacionado.add(tDao.findById(a.getTripId()));	
			for(Seat s: sDao.findByUser(user.getId())){
				Trip t = tDao.findById(s.getTripId());
				if(t.getStatus()!=TripStatus.DONE && !t.getPromoterId().equals(user.getId()))
					viajeRelacionado.add(t);
			}
			request.setAttribute("viajeRelacionado", viajeRelacionado);
			
			Map<Long, Map<String, Seat>> viajeRelacionadoParticipantes = mapaUserParticipantes(viajeRelacionado, sDao, uDao);
			Map<Long, List<User>> viajeRelacionadoSolicitantes = mapaUserSolicitantes(viajeRelacionado, aDao, uDao);
			request.setAttribute("viajeRelacionadoParticipantes", viajeRelacionadoParticipantes);
			request.setAttribute("viajeRelacionadoSolicitantes", viajeRelacionadoSolicitantes);
			
			Map<Long, String> mapaPromotor = new HashMap<Long, String>();
			for(Trip t: viajeRelacionado)
				mapaPromotor.put(t.getId(), uDao.findById(t.getPromoterId()).getLogin());
			request.setAttribute("mapaPromotor", mapaPromotor);
			
			List<Trip> relacion = new ArrayList<Trip>();
			relacion.addAll(misHistorialViajes);
			relacion.addAll(miViaje);
			relacion.addAll(viajeRelacionado);
			Map<Long, String> mapaRelacion = new HashMap<Long, String>();
			for (Trip t: relacion){
				Long[] l = {user.getId(), t.getId()};
				Seat s = sDao.findById(l);
				Application a = aDao.findById(l);
				if(t.getPromoterId().equals(user.getId())){
					mapaRelacion.put(t.getId(), "PROMOTOR");
				}else if(s!=null){//aqui hay que utilizar == para comparar
					if(s.getStatus()==SeatStatus.ACCEPTED)
						mapaRelacion.put(t.getId(), "ADMITIDO");
					else
						mapaRelacion.put(t.getId(), "EXCLUIDO");
				}else if(a!=null){
					if(t.getAvailablePax()>0)
						mapaRelacion.put(t.getId(), "PENDIENTE");
					else
						mapaRelacion.put(t.getId(), "SIN_PLAZA");
				}
			}
			request.setAttribute("mapaRelacion", mapaRelacion);
			Log.debug("Obteniendo las relaciones del usuario con las viajes");
			
		}catch(Exception e){
			Log.info("Algo ocurrido obteniedo listar historial de viaje del usuario [%s]", user.getLogin());
		}
		
		return "EXITO";
	}
	
	public Map<Long, Map<String, Seat>> mapaUserParticipantes (List<Trip> viajes, SeatDao sDao, UserDao uDao){
		Map<Long, Map<String, Seat>> mapaUserParticipantes = new HashMap<Long, Map<String, Seat>>();
		for(Trip t: viajes){
			Map<String, Seat> m = new HashMap<String, Seat>();
			for(Seat s : sDao.findByTrip(t.getId()))
				if(!s.getUserId().equals(t.getPromoterId()))//para no incluir promotor como participante
					m.put(uDao.findById(s.getUserId()).getLogin(), s);
			mapaUserParticipantes.put(t.getId(), m);
		}
		return mapaUserParticipantes;
	}

	public Map<Long, List<User>> mapaUserSolicitantes (List<Trip> viajes, ApplicationDao aDao, UserDao uDao){
		Map<Long, List<User>> mapaUserSolicitantes = new HashMap<Long, List<User>>();
		for(Trip t: viajes){
			List<User> solicitantes = new ArrayList<User>();
			for(Application a: aDao.findByTripId(t.getId()))
				solicitantes.add(uDao.findById(a.getUserId()));
			mapaUserSolicitantes.put(t.getId(), solicitantes);
		}
		return mapaUserSolicitantes;
	}
	
}
