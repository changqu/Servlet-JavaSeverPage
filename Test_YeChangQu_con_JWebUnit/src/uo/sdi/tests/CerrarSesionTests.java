package uo.sdi.tests;

import static net.sourceforge.jwebunit.junit.JWebUnit.assertTitleEquals;
import static net.sourceforge.jwebunit.junit.JWebUnit.beginAt;
import static net.sourceforge.jwebunit.junit.JWebUnit.clickLink;
import static net.sourceforge.jwebunit.junit.JWebUnit.setTextField;
import static net.sourceforge.jwebunit.junit.JWebUnit.submit;

import org.junit.Test;

public class CerrarSesionTests extends YeChangQuTests {

	@Test
	public void testCerrarSesion() {
		beginAt("/");
		setTextField("identificadorUsuario", "user4");
		setTextField("contrasena", "user4");
		submit();
		clickLink("cerrarSesion"); 
		assertTitleEquals("ShareMyTrip-Inicie sesion");
	}

}
