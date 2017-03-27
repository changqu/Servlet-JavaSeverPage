package uo.sdi.tests;

import org.junit.*;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;

public class SolicitarPlazaTests extends YeChangQuTests{

	@Test
	public void testSolicitarPlazaExito() {
		beginAt("/");

		setTextField("identificadorUsuario", "user3");
		setTextField("contrasena", "user3");
		submit();
		
		gotoPage("listarViajes");
		gotoPage("mostrarViaje?id=49");
		
		assertTitleEquals("ShareMyTrip-Viaje en detalle"); 
		assertTextPresent("ViajeId");
		assertTextPresent("Promotor");
		assertTextPresent("Participantes");
		
		clickLinkWithText("Solicitar plaza");
		
		assertTitleEquals("ShareMyTrip-Pagina principal del usuario"); 
		assertTextPresent("Enhorabuena, tu solicitud de viaje ha sido trasmitada");
	}
	
	@Test
	public void testSolicitarPlazaFallado() {
		beginAt("/");

		setTextField("identificadorUsuario", "user3");
		setTextField("contrasena", "user3");
		submit();
		
		gotoPage("listarViajes");
		gotoPage("mostrarViaje?id=43");
		
		assertTitleEquals("ShareMyTrip-Viaje en detalle"); 
		assertTextPresent("ViajeId");
		assertTextPresent("Promotor");
		assertTextPresent("Participantes");
		
		clickLinkWithText("Solicitar plaza");
		
		assertTitleEquals("ShareMyTrip-Pagina principal del usuario"); 
		assertTextPresent("ERROR, ya tiene solicitud con el viaje o es el ya es miembro o esta excluido al viaje");
	}

}
