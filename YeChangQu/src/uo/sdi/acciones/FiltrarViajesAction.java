package uo.sdi.acciones;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.model.Trip;
import uo.sdi.model.TripStatus;
import uo.sdi.persistence.PersistenceFactory;
import alb.util.log.Log;

public class FiltrarViajesAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		List<Trip> viajes;
		List<Trip> viajesActivo = new ArrayList<Trip>();
		List<Trip> viajesFiltrado;
		String origen = (String)request.getParameter("fOrigen");
		String destino = (String)request.getParameter("fDestino");
		String coste = (String)request.getParameter("fCoste");
		try {
			viajes=PersistenceFactory.newTripDao().findAll();
			for (Trip t: viajes) 
				if(t.getStatus()==TripStatus.OPEN)	
					viajesActivo.add(t);	
			if(!origen.equals("")){
				viajesFiltrado = new ArrayList<Trip>();
				for(Trip t: viajesActivo)
					if(!t.getDeparture().getCity().equals(origen))
						viajesFiltrado.add(t);
				viajesActivo.removeAll(viajesFiltrado);
			}
			if(!destino.equals("")){
				viajesFiltrado = new ArrayList<Trip>();
				for(Trip t: viajesActivo)
					if(!t.getDestination().getCity().equals(destino))
						viajesFiltrado.add(t);
				viajesActivo.removeAll(viajesFiltrado);
			}
			if(!coste.equals("")){
				viajesFiltrado = new ArrayList<Trip>();
				for(Trip t: viajesActivo)
					if(t.getEstimatedCost() > Double.parseDouble(coste))
						viajesFiltrado.add(t);
				viajesActivo.removeAll(viajesFiltrado);
			}
			
			request.setAttribute("listaViajesActivo", viajesActivo);
			Log.debug("Obtenida lista de viajes activos conteniendo [%d] viajes", viajesActivo.size());
		}
		catch (Exception e) {
			Log.error("Algo ha ocurrido en el filtrado de las viajes");
		}
		return "EXITO";
	}

}
