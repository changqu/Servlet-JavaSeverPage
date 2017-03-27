package uo.sdi.tests;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;

import org.junit.Test;

public class ListarViajeTests extends YeChangQuTests{

	@Test
	public void testListarViajes() {
		beginAt("/");  // Navegar a la URL
		assertLinkPresent("listarViajes");  // Comprobar que existe el hipervínculo
		clickLink("listarViajes"); // Seguir el hipervínculo
		assertTitleEquals("ShareMyTrip-Listado de viajes");  // Comprobar título de la página
		
		assertElementPresent("item_0"); // Comprobar elemento presente en la página
		assertElementPresent("item_1"); // Comprobar elemento presente en la página
		assertElementPresent("item_2"); //...
		
		beginAt("/");
		setTextField("identificadorUsuario", "user2");
		setTextField("contrasena", "user2");
		submit();
		clickLink("listarViajes"); 
		assertTitleEquals("ShareMyTrip-Listado de viajes");
		assertTextPresent("*Para ver la viaje en detalle, pulse id de viaje, alli puedes ver los comentarios y puntuacion sobre el promotor y del resto de participantes. "
				+ "Ademas, en la pagina de la detalle del viaje puedes hacer solicitud de plaza.");
  }

}
