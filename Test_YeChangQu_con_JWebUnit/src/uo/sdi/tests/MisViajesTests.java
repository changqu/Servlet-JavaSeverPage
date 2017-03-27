package uo.sdi.tests;

import static net.sourceforge.jwebunit.junit.JWebUnit.assertTextPresent;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertTitleEquals;
import static net.sourceforge.jwebunit.junit.JWebUnit.beginAt;
import static net.sourceforge.jwebunit.junit.JWebUnit.clickLink;
import static net.sourceforge.jwebunit.junit.JWebUnit.setTextField;
import static net.sourceforge.jwebunit.junit.JWebUnit.submit;

import org.junit.Test;

public class MisViajesTests extends YeChangQuTests{

	@Test
	public void testMisViajes() {
		beginAt("/");
		setTextField("identificadorUsuario", "user4");
		setTextField("contrasena", "user4");
		submit();
		clickLink("misViajes"); 
		assertTitleEquals("ShareMyTrip-Mis viajes");
		assertTextPresent("Historial de viaje");
		assertTextPresent("Mi viaje activo(Soy Promotor^_^)");
		assertTextPresent("Viajes pendientes y aceptados");
	}

}
