package uo.sdi.tests;

import static net.sourceforge.jwebunit.junit.JWebUnit.assertTextPresent;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertTitleEquals;
import static net.sourceforge.jwebunit.junit.JWebUnit.beginAt;
import static net.sourceforge.jwebunit.junit.JWebUnit.setTextField;
import static net.sourceforge.jwebunit.junit.JWebUnit.submit;

import org.junit.Test;

public class IniciarSesionTests extends YeChangQuTests{

	//iniciar sesion con exito
    @Test
    public void testIniciarSesionConExito() {
    	// Rellenando el formulario HTML
        beginAt("/");  // Navegar a la URL
        setTextField("identificadorUsuario", "user1"); // Rellenar primer campo de formulario
        setTextField("contrasena", "user1"); 
        submit(); // Enviar formulario
        assertTitleEquals("ShareMyTrip-Pagina principal del usuario");  // Comprobar título de la página
        assertTextPresent("Es Vd el usuario numero:"); // Comprobar cierto texto está presente
    }

    @Test
    public void testIniciarSesionConExitoConQueryString() {
    	// Rellenando el formulario HTML
        beginAt("/validarse?identificadorUsuario=user2&contrasena=user2");  // Navegar a la URL
        assertTitleEquals("ShareMyTrip-Pagina principal del usuario");  // Comprobar título de la página
        assertTextPresent("Es Vd el usuario numero:"); // Comprobar cierto texto está presente
    }
    
    //iniciarSesion Sin exito por un contrasena incorrecta
    @Test
    public void testIniciarSesionSinExito() {
    	// Rellenando el formulario HTML
        beginAt("/");  // Navegar a la URL
        setTextField("identificadorUsuario", "user1"); // Rellenar primer campo de formulario
        setTextField("contrasena", "contrasenaIncorrecta");
        submit(); // Enviar formulario
        assertTitleEquals("ShareMyTrip-LoginFracasado");  // Comprobar título de la página
        assertTextPresent("No has podido iniciar sesion, ya has iniciado la sesion o indrodujo de forma incorrecta su identificador y contrasena."); 
    }
	
}
