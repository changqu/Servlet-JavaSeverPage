package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.model.Trip;
import uo.sdi.persistence.PersistenceFactory;
import alb.util.log.Log;

public class OrdenacionPromotorAction implements Accion{

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		List<Trip> viajes;
		
		try {
			viajes=PersistenceFactory.newTripDao().ordenacionByPromotor();
			
			request.setAttribute("listaViajesActivo", viajes);
			Log.debug("Obtenida lista de viajes activos conteniendo [%d] viajes", viajes.size());
		}
		catch (Exception e) {
			Log.error("Algo ha ocurrido en la ordenacion de viajes");
		}
		return "EXITO";
	}

}
