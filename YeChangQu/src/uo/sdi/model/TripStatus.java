package uo.sdi.model;

public enum TripStatus {
	OPEN,   //ordinal() 0      //un nuevo viaje abierto
	CLOSED,  //ordinal() 1     //viaje con las plazas llenan pero no llego a closingdate
	CANCELLED,  //ordinal()2   //el viaje se cancela antes del closingdate(las plazas este lleno o no)
	DONE  //ordinal() 3        //viaje ya hecho pasado al closingDate
}
