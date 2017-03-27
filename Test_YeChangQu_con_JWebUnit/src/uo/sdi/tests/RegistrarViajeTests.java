package uo.sdi.tests;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;

import org.junit.Test;

public class RegistrarViajeTests extends YeChangQuTests{

	@Test
	public void testRegistrarViajeExito() {
		beginAt("/");

		setTextField("identificadorUsuario", "user1");
		setTextField("contrasena", "user1");
		submit();
		
		gotoPage("irRegistrarViaje");
		
		//salida
		setTextField("salidaCiudad", "sc");
		setTextField("salidaDireccion", "sd");
		setTextField("salidaProvincia", "sp");
		setTextField("salidaPais", "sp");
		setTextField("salidaCodigoPostal", "666666");
		setTextField("salidaLon", "");
		setTextField("salidaLat", "");
		setTextField("salidaFecha", "2017-12-20");
		setTextField("salidaHora", "00-00-00");
		//llegada
		setTextField("llegadaCiudad", "lc");
		setTextField("llegadaDireccion", "ld");
		setTextField("llegadaProvincia", "lp");
		setTextField("llegadaPais", "lp");
		setTextField("llegadaCodigoPostal", "999999");
		setTextField("llegadaLon", "");
		setTextField("llegadaLat", "");
		setTextField("llegadaFecha", "2017-12-21");
		setTextField("llegadaHora", "00-00-00");
		//otros
		setTextField("fechaLimite", "2017-12-19");
		setTextField("horaLimite", "00-00-00");
		setTextField("coste", "99");
		setTextField("descripcion", "Me encanta el viaje!");
		setTextField("plazasMaximo", "5");
		setTextField("plazasDisponibles", "4");
		
		submit();
		assertTitleEquals("ShareMyTrip-Pagina principal del usuario");  // Comprobar título de la página
	    assertTextPresent("Enhorabuena, has registrado tu viaje correctamente."); 
	}
	
	@Test
	public void testRegistrarViajeFalloFechas() {
		beginAt("/");

		setTextField("identificadorUsuario", "user1");
		setTextField("contrasena", "user1");
		submit();
		
		gotoPage("irRegistrarViaje");
		
		//salida
		setTextField("salidaCiudad", "sc");
		setTextField("salidaDireccion", "sd");
		setTextField("salidaProvincia", "sp");
		setTextField("salidaPais", "sp");
		setTextField("salidaCodigoPostal", "666666");
		setTextField("salidaLon", "");
		setTextField("salidaLat", "");
		setTextField("salidaFecha", "2017-12-20");
		setTextField("salidaHora", "00-00-00");
		//llegada
		setTextField("llegadaCiudad", "lc");
		setTextField("llegadaDireccion", "ld");
		setTextField("llegadaProvincia", "lp");
		setTextField("llegadaPais", "lp");
		setTextField("llegadaCodigoPostal", "999999");
		setTextField("llegadaLon", "");
		setTextField("llegadaLat", "");
		setTextField("llegadaFecha", "2017-12-19");
		setTextField("llegadaHora", "00-00-00");
		//otros
		setTextField("fechaLimite", "2017-12-22");
		setTextField("horaLimite", "00-00-00");
		setTextField("coste", "99");
		setTextField("descripcion", "Me encanta el viaje!");
		setTextField("plazasMaximo", "5");
		setTextField("plazasDisponibles", "4");
		
		submit();
		assertTitleEquals("ShareMyTrip-Pagina principal del usuario");  // Comprobar título de la página
	    assertTextPresent("ERROR, No has podido registrar viaje, la relacion fecha salida, fecha llegada y fecha limite no esta bien, revisa, porfavor"); 
	}

}
