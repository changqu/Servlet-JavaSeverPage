package uo.sdi.tests;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;

import org.junit.*;

public class RegistrarseTests extends YeChangQuTests{

	@Test
	public void testNavegacion() {
		beginAt("/");
		assertLinkPresentWithText("¿No estas registrado?Registrarse");
	}
	
	@Test
	public void testFalloContrasena() {
		beginAt("registrarse.jsp");
		
		setTextField("identificadorUsuario", "user");
		setTextField("nombre", "nombre");
		setTextField("apellidos", "apellidos");
		setTextField("correoElectronico", "correo@correo.com");
		
		setTextField("contrasena", "contrasena1");
		setTextField("repetirContrasena", "contrasena2");
		
		submit();
		
		assertTitleEquals("ShareMyTrip-RegistroFracasado");  // Comprobar título de la página
	    assertTextPresent("No has podido registrarse, puede que el identificador del usuario esta usado, dejo campo vacio, el formato de correoElectronico no es correcto o no coincidieron las contrasenas."); 
	}
	
	@Test
	public void testFalloCorreo() {
		beginAt("registrarse.jsp");
		
		setTextField("identificadorUsuario", "user");
		setTextField("nombre", "nombre");
		setTextField("apellidos", "apellidos");
		setTextField("correoElectronico", "correo.com");
		
		setTextField("contrasena", "contrasena1");
		setTextField("repetirContrasena", "contrasena1");
		
		submit();
		
		assertTitleEquals("ShareMyTrip-RegistroFracasado");  // Comprobar título de la página
	    assertTextPresent("No has podido registrarse, puede que el identificador del usuario esta usado, dejo campo vacio, el formato de correoElectronico no es correcto o no coincidieron las contrasenas."); 
	}
	
}
