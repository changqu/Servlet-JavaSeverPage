package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.model.Seat;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.SeatDao;
import alb.util.log.Log;

public class DejarComentarioAlPromotorAction implements Accion{

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String comentario = (String)request.getParameter("comentarioAlPromotor");
		String[] informacion = request.getQueryString().split("&");
		Long userId = Long.parseLong(informacion[0].split("=")[1]);
		Long viajeId = Long.parseLong(informacion[1].split("=")[1]);
		Long[] l = {userId, viajeId};
		SeatDao sDao = PersistenceFactory.newSeatDao();
		try{
			Seat s = sDao.findById(l);
			s.setComment(comentario);
			sDao.update(s);
			Log.info("Dejando comentario al promotor de la viaje [%s]", viajeId);
			request.setAttribute("MensajeDejarComentarioAlPromotor", "Has dejado un comentario al promotor de la viaje "+viajeId+" correctamente");
		}catch(Exception e){
			Log.info("Ha ocurrido un error desconocido al intentar dejar comentario al promotor");
		}
		
		return "EXITO";
	}

}
