package uo.sdi.listener;

import java.io.FileOutputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import alb.util.log.Log;

public class PersistentCounter implements ServletContextListener {

	//aqui crea el contador cogiendo el valor del archivo aplicacion.properties
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		// Leer el contador total de sesiones de usuario iniciadas
		String counter="0";
		Properties properties = new Properties();
		try {
			properties.load(arg0.getServletContext().getResourceAsStream(
					arg0.getServletContext().getInitParameter("ubicacionDelContadorDeSesiones")));
			counter=properties.getProperty("contadorSesiones");
		} catch (Exception e) {}
		//Guardar atributo a un objeto servletContext que sera un atributo comun en toda la instancia de aplicacion para todo usuario
		arg0.getServletContext().setAttribute("contador",counter);
		Log.debug("Inicializando contador de sesiones de usuario a: %s",counter);
	}
	
	//Es una operacion interna lo ve en el servidor mientras su ejecucion
	//Con el nuevo valor del contador lo escribe al fichero
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

		// Guardar el contador total de sesiones de usuario iniciadas
		String counter=(String)arg0.getServletContext().getAttribute("contador");//coge el valor contador
		Properties properties = new Properties();
		properties.setProperty("contadorSesiones", counter);//lo guarda en contadorSesion el counter
		try {
	        properties.store(new FileOutputStream(arg0.getServletContext().getRealPath(
	        		arg0.getServletContext().getInitParameter("ubicacionDelContadorDeSesiones"))),"Lista de propiedades"); 
		} catch (Exception e) {}
		Log.debug("Persistiendo contador de sesiones de usuario con el valor: %s",counter);
	}
	
	
}