package uo.sdi.acciones;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.model.AddressPoint;
import uo.sdi.model.Seat;
import uo.sdi.model.SeatStatus;
import uo.sdi.model.Trip;
import uo.sdi.model.TripStatus;
import uo.sdi.model.User;
import uo.sdi.model.Waypoint;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.TripDao;
import alb.util.log.Log;

public class RegistrarViajeAction implements Accion{

	@SuppressWarnings("deprecation")
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado = "FRACASO";//resultado iniciado a fracaso

		//salida
		String salidaCiudad = (String)request.getParameter("salidaCiudad");
		String salidaDireccion = (String)request.getParameter("salidaDireccion");
		String salidaProvincia = (String)request.getParameter("salidaProvincia");
		String salidaPais = (String)request.getParameter("salidaPais");
		String salidaCodigoPostal = (String)request.getParameter("salidaCodigoPostal");
		if(salidaCiudad.equals("") || salidaDireccion.equals("") || salidaProvincia.equals("")
				|| salidaPais.equals("") || salidaCodigoPostal.equals("")){
			request.setAttribute("mensajeRegistrarViaje", "ERROR, No has podido registrar viaje, existen campos no introducidos en parte salida");
			Log.info("No has podido registrar viaje, existen campos no introducidos en parte salida");
			return resultado;
		}
		String[] salidaFecha = ((String)request.getParameter("salidaFecha")).split("-");
		String[] salidaHora = ((String)request.getParameter("salidaHora")).split("-");
		Date salidaDate = null;
		try{
			salidaDate = new Date(Integer.parseInt(salidaFecha[0])-1900, Integer.parseInt(salidaFecha[1])-1, Integer.parseInt(salidaFecha[2]), 
				Integer.parseInt(salidaHora[0]), Integer.parseInt(salidaHora[1]), Integer.parseInt(salidaHora[2]));
		}catch(Exception e){
			request.setAttribute("mensajeRegistrarViaje", "ERROR, No has podido registrar viaje, has introducido las fechas y horas de salida en formado incorrecto");
			Log.info("No has podido registrar viaje, has introducido las fechas y horas de salida en formado incorrecto");
			return resultado;
		}
		
		Waypoint puntoSalida = new Waypoint(0.0, 0.0);
		if (!((String)request.getParameter("salidaLat")).equals("") && !((String)request.getParameter("salidaLon")).equals("")) {
			Double salidaLat = Double.parseDouble((String)request.getParameter("salidaLat"));
			Double salidaLon = Double.parseDouble((String)request.getParameter("salidaLon"));
			puntoSalida = new Waypoint(salidaLat, salidaLon);
		}

		AddressPoint salida = new AddressPoint(salidaDireccion, salidaCiudad, salidaProvincia, salidaPais, salidaCodigoPostal, puntoSalida);

		//llegada
		String llegadaCiudad = (String)request.getParameter("llegadaCiudad");
		String llegadaDireccion = (String)request.getParameter("llegadaDireccion");
		String llegadaProvincia = (String)request.getParameter("llegadaProvincia");
		String llegadaPais = (String)request.getParameter("llegadaPais");
		String llegadaCodigoPostal = (String)request.getParameter("llegadaCodigoPostal");
		if(llegadaCiudad.equals("") || llegadaDireccion.equals("") || llegadaProvincia.equals("")
				|| llegadaPais.equals("") || llegadaCodigoPostal.equals("")){
			request.setAttribute("mensajeRegistrarViaje", "ERROR, No has podido registrar viaje, existen campos no introducidos en parte llegada");
			Log.info("No has podido registrar viaje, existen campos no introducidos en parte llegada");
			return resultado;
		}
		String llegadaFecha[] = ((String)request.getParameter("llegadaFecha")).split("-");
		String llegadaHora[] = ((String)request.getParameter("llegadaHora")).split("-");
		Date llegadaDate = null;
		try{
			llegadaDate = new Date(Integer.parseInt(llegadaFecha[0])-1900, Integer.parseInt(llegadaFecha[1])-1, Integer.parseInt(llegadaFecha[2]), 
				Integer.parseInt(llegadaHora[0]), Integer.parseInt(llegadaHora[1]), Integer.parseInt(llegadaHora[2]));
		}catch(Exception e){
			request.setAttribute("mensajeRegistrarViaje", "ERROR, No has podido registrar viaje, has introducido las fechas y horas de llegada en formado incorrecto");
			Log.info("No has podido registrar viaje, has introducido las fechas y horas de llegada en formado incorrecto");
			return resultado;
		}
		
		Waypoint puntoLlegada = new Waypoint(0.0, 0.0);
		if (!((String)request.getParameter("llegadaLat")).equals("") && !((String)request.getParameter("llegadaLon")).equals("")) {
			Double llegadaLat = Double.parseDouble((String)request.getParameter("llegadaLat"));
			Double llegadaLon = Double.parseDouble((String)request.getParameter("llegadaLon"));
			puntoLlegada = new Waypoint(llegadaLat, llegadaLon);
		}
		
		AddressPoint llegada = new AddressPoint(llegadaDireccion, llegadaCiudad, llegadaProvincia, llegadaPais, llegadaCodigoPostal, puntoLlegada);

		//otros
		String[] fechaLimite = ((String)request.getParameter("fechaLimite")).split("-");
		String[] horaLimite = ((String)request.getParameter("horaLimite")).split("-");
		Date limiteDate = null;
		try{
		limiteDate = new Date(Integer.parseInt(fechaLimite[0])-1900, Integer.parseInt(fechaLimite[1])-1, Integer.parseInt(fechaLimite[2]), 
				Integer.parseInt(horaLimite[0]), Integer.parseInt(horaLimite[1]), Integer.parseInt(horaLimite[2]));
		}catch(Exception e){
			request.setAttribute("mensajeRegistrarViaje", "ERROR, No has podido registrar viaje, has introducido la limite de fechas y horas de en formado incorrecto");
			Log.info("no has podido registrar viaje, has introducido la limite de fechas y horas de en formado incorrecto");
			return resultado;
		}
		String descripcion = (String)request.getParameter("descripcion");
		Integer plazasmaximo = null;
		Integer plazasDisponibles = null;
		Double coste = null;
		try{
			plazasmaximo = Integer.parseInt((String)request.getParameter("plazasMaximo"));
			plazasDisponibles = Integer.parseInt((String)request.getParameter("plazasDisponibles"));
			coste = Double.parseDouble((String)request.getParameter("coste"));
		}catch(Exception e){
			request.setAttribute("mensajeRegistrarViaje", "ERROR, No has podido registrar viaje, has introducido la plaza maximo o plaza disponible o coste en formado incorrecto");
			Log.info("No has podido registrar viaje, has introducido la plaza maximo o plaza disponible o coste en formado incorrecto");
			return resultado;
		}
		
		if (plazasDisponibles >= plazasmaximo || plazasDisponibles <= 0 || plazasmaximo <=0 ) {
			request.setAttribute("mensajeRegistrarViaje", "ERROR, No has podido registrar viaje, la plaza disponible no puede ser mayor o igual que plaza maximo o estan negativo alguno");
			Log.info("No has podido registrar viaje, la plaza disponible no puede ser mayor o igual que plaza maximo o estan negativo alguno");
			return resultado;
		}
	
		if (!comprobarFechas(salidaDate, llegadaDate, limiteDate)) {
			request.setAttribute("mensajeRegistrarViaje", "ERROR, No has podido registrar viaje, la relacion fecha salida, fecha llegada y fecha limite no esta bien, revisa, porfavor");
			Log.info("No has podido registrar viaje, la relacion fecha salida, fecha llegada y fecha limite no esta bien, revisa, porfavor");
			return resultado;
		}
		try{
			TripDao dao = PersistenceFactory.newTripDao();
			Trip viaje = new Trip();
			
			viaje.setDepartureDate(salidaDate);
			viaje.setArrivalDate(llegadaDate);	
			viaje.setDeparture(salida);
			viaje.setDestination(llegada);
			viaje.setClosingDate(limiteDate);
			viaje.setMaxPax(plazasmaximo);
			viaje.setAvailablePax(plazasDisponibles);
			viaje.setComments(descripcion);
			viaje.setStatus(TripStatus.OPEN);//luego en impl llama al getStatus.ordinal asi consigue insertar 0,1,2,3 con estado correspondiente
			viaje.setEstimatedCost(coste);
			Long promotorId = ((User) request.getSession().getAttribute("user")).getId();
			viaje.setPromoterId(promotorId);
			
			dao.save(viaje);
			
			Log.info("El viaje se ha registrado con exito");
			request.setAttribute("mensajeRegistrarViaje", "Enhorabuena, has registrado tu viaje correctamente.");
			resultado = "EXITO";
			
			//meter promotor al tseat ok bien
			Seat s = new Seat();
			s.setComment("Soy promotor de viaje");
			s.setStatus(SeatStatus.ACCEPTED);
			s.setUserId(promotorId);
			s.setTripId((PersistenceFactory.newTripDao().findByPromoterIdAndArrivalDate(promotorId, llegadaDate)).getId());
			PersistenceFactory.newSeatDao().save(s);
			
		}catch(Exception e){
			request.setAttribute("mensajeRegistrarViaje", "ERROR, No has podido registrar viaje, ha ocurrido un problema desconocido al intentar registrar viaje");
			Log.info("No has podido registrar viaje, ha ocurrido un problema desconocido al intentar registrar viaje");
			return "FRACASO";
		}
		
		return resultado;
	}
	
	private boolean comprobarFechas(Date salidaFecha, Date llegadaFecha, Date fechaLimite) {
		Date now =new Date();
		if (fechaLimite.after(salidaFecha) || salidaFecha.after(llegadaFecha) || fechaLimite.before(now))
			return false;
		return true;
	}

}
