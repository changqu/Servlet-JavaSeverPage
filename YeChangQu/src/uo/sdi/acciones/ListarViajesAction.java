package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.model.Trip;
import uo.sdi.persistence.PersistenceFactory;
import alb.util.log.Log;

public class ListarViajesAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		List<Trip> viajesActivo;
		try {
			viajesActivo = PersistenceFactory.newTripDao().findOpen();
			request.setAttribute("listaViajesActivo", viajesActivo);
			Log.debug("Obtenida lista de viajes activos conteniendo [%d] viajes", viajesActivo.size());
		}
		catch (Exception e) {
			Log.error("Algo ha ocurrido obteniendo lista de viajes");
		}
		return "EXITO";
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	
}
