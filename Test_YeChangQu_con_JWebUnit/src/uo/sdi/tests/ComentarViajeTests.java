package uo.sdi.tests;

import static net.sourceforge.jwebunit.junit.JWebUnit.assertTextPresent;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertTitleEquals;
import static net.sourceforge.jwebunit.junit.JWebUnit.beginAt;
import static net.sourceforge.jwebunit.junit.JWebUnit.clickLink;
import static net.sourceforge.jwebunit.junit.JWebUnit.gotoPage;
import static net.sourceforge.jwebunit.junit.JWebUnit.setTextField;
import static net.sourceforge.jwebunit.junit.JWebUnit.submit;

import org.junit.Test;

public class ComentarViajeTests extends YeChangQuTests{

	@Test
	public void testComentarViaje() {
		beginAt("/");
		setTextField("identificadorUsuario", "user4");
		setTextField("contrasena", "user4");
		submit();
		clickLink("misViajes"); 
		gotoPage("irComentarViaje?idViaje=29");
		
		assertTitleEquals("ShareMyTrip-ComentarViaje"); 
		assertTextPresent("ViajeId");
		assertTextPresent("Promotor");
		assertTextPresent("Participantes");
		
		setTextField("identificadorUsuario", "user3");
		setTextField("punto", "3");
		setTextField("comentario", "Es majo");
		submit();
		assertTitleEquals("ShareMyTrip-Pagina principal del usuario"); 
		assertTextPresent("Has dejado comentario al usuario user3 correctamente");
		
	}

}
