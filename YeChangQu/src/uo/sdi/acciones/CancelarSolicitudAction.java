package uo.sdi.acciones;

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

public class CancelarSolicitudAction implements Accion{

	@Override
	public String execute(HttpServletRequest request, 
			HttpServletResponse response) {
		
		User user = (User)request.getSession().getAttribute("user");
		Long userId = user.getId();
		Long viajeId = Long.parseLong(request.getQueryString().split("=")[1]);
		Long[] l = {userId, viajeId};
		
		TripDao tDao = PersistenceFactory.newTripDao();
		SeatDao sDao = PersistenceFactory.newSeatDao();
		ApplicationDao aDao = PersistenceFactory.newApplicationDao();
		
		try{
			Application a = aDao.findById(l);
			Seat s = sDao.findById(l);
			Trip t = tDao.findById(viajeId);
			
			if(a!=null){
				aDao.delete(l);
				request.setAttribute("mensajeCancelarSolicitud", "Enhorabuena has cancelado su solicitud de plaza correctamente");
				Log.info("El usuario [%s] se ha cancelado solicitud de plaza para viaje [%s]",user.getLogin(), viajeId);
			}else if(s!=null){//sea estado accepted o excluido
				sDao.delete(l);
				request.setAttribute("mensajeCancelarSolicitud", "Enhorabuena has cancelado su solicitud de plaza correctamente");
				Log.info("El usuario [%s] se ha cancelado solicitud de plaza para viaje [%s]",user.getLogin(), viajeId);
				if(s.getStatus()==SeatStatus.ACCEPTED){
					t.setAvailablePax(t.getAvailablePax()+1);
					if(t.getStatus()==TripStatus.CLOSED)//si este caso justo lleno y quitas una plaza
						t.setStatus(TripStatus.OPEN);	//el viaje cambia de estado al open
					tDao.update(t);
				}
			}
		}catch(Exception e){
			Log.info("Algo ha ocurrido al intentar cancelar solicitud de plaza");
		}
				
		return "EXITO";
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}

}
