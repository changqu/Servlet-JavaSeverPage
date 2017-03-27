package uo.sdi.persistence;

import java.util.Date;
import java.util.List;

import uo.sdi.model.Trip;
import uo.sdi.persistence.util.GenericDao;

public interface TripDao extends GenericDao<Trip, Long> {

	Trip findByPromoterIdAndArrivalDate(Long id, Date arrivalDate);
	List<Trip> findOpen();
	List<Trip> findDone();
	List<Trip> findNoDoneByPromoter(Long id);
	
	List<Trip> ordenacionByOrigen();
	List<Trip> ordenacionByDestino();
	List<Trip> ordenacionByFecha();
	List<Trip> ordenacionByPromotor();
	
}
