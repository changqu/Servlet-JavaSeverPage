package uo.sdi.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;

import uo.sdi.acciones.*;

public class Controlador extends javax.servlet.http.HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private Map<String, Map<String, Accion>> mapaDeAcciones; // <rol, <opcion, objeto Accion>>
	private Map<String, Map<String, Map<String, String>>> mapaDeNavegacion; // <rol, <opcion, <resultado, JSP>>>

	public void init() throws ServletException {  
		crearMapaAcciones();
		crearMapaDeJSP();
    }
	

	public void doGet(HttpServletRequest req, HttpServletResponse res)
				throws IOException, ServletException {
		
		String opcion, resultado, jspSiguiente;
		Accion accion;
		String rolAntes, rolDespues;
		
		try {
			opcion=req.getServletPath().replace("/","");//no se porque pero obtiene opcion o sea accion no del nombre del clase
				
			rolAntes=obtenerRolDeSesion(req);//segun session.getAttributte("user") determina rol entre publico y registrado
			
			accion=buscarAccionParaOpcion(rolAntes, opcion);//saca accion del mapa de acciones
				
			resultado=accion.execute(req,res); //propio class accion ejecutan sus acciones
												//resultado que puede ser fracaso o exito
			rolDespues=obtenerRolDeSesion(req);//saca rol del ahora puede ser cambiado del publico del registrado
												//ya que el propio calse accion puede cambiar session.getAttributte("user") a not null
			jspSiguiente=buscarJSPSegun(rolDespues, opcion, resultado);//saca jsp siguiente del mapaNavegacion
			//System.out.println(jspSiguiente);
			req.setAttribute("jspSiguiente", jspSiguiente);//esto para que? para que manejemos mas sencillo? para comprobarNavegacion.jsp

		} catch(Exception e) {//excepcion por si la mapa de accion o de jsp no contiene elemento o objeto que busca tras la ejecucion de accion
			
			req.getSession().invalidate();
			
			Log.error("Se ha producido alguna excepción no manejada [%s]",e);
			
			jspSiguiente="/login.jsp";//cualquier excepcion que capta aqui manda la pagina siguiente al login.jsp
		}							//puede ir descomentando este try para que vea el error que te muestra en navegador mejor
			
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(jspSiguiente); 
			
		dispatcher.forward(req, res);// despacha al jspSiguiente		
	}			
	
	
	private String obtenerRolDeSesion(HttpServletRequest req) {
		HttpSession sesion=req.getSession();
		if (sesion.getAttribute("user")==null)//no hay user
			return "PUBLICO"; //rol cambia a publico
		else
			return "REGISTRADO"; //rol cambia a registrado
	}

	// Obtiene un objeto accion en funci�n de la opci�n
	// enviada desde el navegador
	private Accion buscarAccionParaOpcion(String rol, String opcion) {
		
		Accion accion=mapaDeAcciones.get(rol).get(opcion);
		Log.debug("Elegida acción [%s] para opción [%s] y rol [%s]",accion,opcion,rol);
		return accion;
	}
	
	
	// Obtiene la p�gina JSP a la que habr� que entregar el
	// control el funci�n de la opci�n enviada desde el navegador
	// y el resultado de la ejecuci�n de la acci�n asociada
	private String buscarJSPSegun(String rol, String opcion, String resultado) {
//		System.out.println(resultado);
//		System.out.println(rol);
//		System.out.println(opcion);
		String jspSiguiente=mapaDeNavegacion.get(rol).get(opcion).get(resultado);
		Log.debug("Elegida página siguiente [%s] para el resultado [%s] tras realizar [%s] con rol [%s]",jspSiguiente,resultado,opcion,rol);
		return jspSiguiente;		
	}
		
	//mapa de acciones, <rol, <opcion, objeto Accion>>
	private void crearMapaAcciones() {
		
		mapaDeAcciones=new HashMap<String,Map<String,Accion>>();
		
		Map<String,Accion> mapaPublico=new HashMap<String,Accion>();
		mapaPublico.put("validarse", new ValidarseAction());
		mapaPublico.put("listarViajes", new ListarViajesAction());
		mapaPublico.put("registrarse", new RegistrarseAction());//razon publico si un usuario ha iniciado la sesion, no se le va ofrecer opcion de registrar
		mapaPublico.put("ordenacionOrigen", new OrdenacionOrigenAction());
		mapaPublico.put("ordenacionDestino", new OrdenacionDestinoAction());
		mapaPublico.put("ordenacionFecha", new OrdenacionFechaAction());
		mapaPublico.put("ordenacionPromotor", new OrdenacionPromotorAction());
		mapaPublico.put("filtrarViajes", new FiltrarViajesAction());
		mapaDeAcciones.put("PUBLICO", mapaPublico);
		
		Map<String,Accion> mapaRegistrado=new HashMap<String,Accion>();
		mapaRegistrado.put("modificarDatosPersonal", new ModificarDatosPersonalAction());
		mapaRegistrado.put("validarse", new ValidarseAction());//un usuario registrado tambien debe poder validar aunque puede producir error
		mapaRegistrado.put("listarViajes", new ListarViajesAction());
		mapaRegistrado.put("cerrarSesion", new CerrarSesionAction());//antes de cerrarsesion el rol es registrado
		mapaRegistrado.put("irPrincipal", new IrPrincipalAction());
		mapaRegistrado.put("irModificarDatosPersonal", new IrModificarDatosPersonalAction());
		mapaRegistrado.put("irRegistrarViaje", new IrRegistrarViajeAction());
		mapaRegistrado.put("registrarViaje", new RegistrarViajeAction());
		mapaRegistrado.put("mostrarViaje", new MostrarViajeAction());
		mapaRegistrado.put("solicitarPlaza", new SolicitarPlazaAction());
		mapaRegistrado.put("cancelarSolicitud", new CancelarSolicitudAction());
		mapaRegistrado.put("misViajes", new MisViajesAction());
		mapaRegistrado.put("irComentarViaje", new IrComentarViajeAction());
		mapaRegistrado.put("comentarViaje", new ComentarViajeAction());
		mapaRegistrado.put("confirmarPasajero", new ConfirmarPasajeroAction());
		mapaRegistrado.put("excluirPasajero", new ExcluirPasajeroAction());
		mapaRegistrado.put("dejarComentarioAlPromotor", new DejarComentarioAlPromotorAction());
		mapaRegistrado.put("cancelarViaje", new CancelarViajeAction());
		mapaRegistrado.put("irModificarViaje", new IrModificarViajeAction());
		mapaRegistrado.put("modificarViaje", new ModificarViajeAction());
		mapaRegistrado.put("ordenacionOrigen", new OrdenacionOrigenAction());
		mapaRegistrado.put("ordenacionDestino", new OrdenacionDestinoAction());
		mapaRegistrado.put("ordenacionFecha", new OrdenacionFechaAction());
		mapaRegistrado.put("ordenacionPromotor", new OrdenacionPromotorAction());
		mapaRegistrado.put("filtrarViajes", new FiltrarViajesAction());
		mapaDeAcciones.put("REGISTRADO", mapaRegistrado);
	}
	
	//mapa jsp // <rol, <opcion, <resultado, JSP>>>
	private void crearMapaDeJSP() {
				
		mapaDeNavegacion=new HashMap<String,Map<String, Map<String, String>>>();

		// Crear mapas auxiliares vacíos
		Map<String, Map<String, String>> opcionResJSP=new HashMap<String, Map<String, String>>();
		Map<String, String> resJSP=new HashMap<String, String>();

		// Mapa de navegación del público
		resJSP.put("FRACASO","/loginFracasado.jsp");//aqui exito nada ya que en el ValidarseAccion si tiene exito  
		opcionResJSP.put("validarse", resJSP);		//el rolDespues se cambia al registrado teniendo cuenta session.getAttributte("user") no es null
		
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/listaViajes.jsp");
		opcionResJSP.put("listarViajes", resJSP);
		
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/registradoConExito.jsp");//todos los registros con exitos llega hasta aqui
		resJSP.put("FRACASO","/registradoFracasado.jsp");//todos los registros fracasado llega hasta aqui
		opcionResJSP.put("registrarse", resJSP);
		
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/login.jsp");
		opcionResJSP.put("cerrarSesion", resJSP);//despues de ese action rolDespues cambia a publico
		
		//opcional
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/listaViajes.jsp");
		opcionResJSP.put("ordenacionOrigen", resJSP);
		
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/listaViajes.jsp");
		opcionResJSP.put("ordenacionDestino", resJSP);
		
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/listaViajes.jsp");
		opcionResJSP.put("ordenacionFecha", resJSP);
		
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/listaViajes.jsp");
		opcionResJSP.put("ordenacionPromotor", resJSP);
		
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/listaViajes.jsp");
		opcionResJSP.put("filtrarViajes", resJSP);
		
		mapaDeNavegacion.put("PUBLICO",opcionResJSP);
		
		
		
		// Crear mapas auxiliares vacíos
		opcionResJSP=new HashMap<String, Map<String, String>>();
		resJSP=new HashMap<String, String>();
		
		// Mapa de navegación de usuarios registrados
		resJSP.put("EXITO","/principal.jsp");//aqui fracaso nada ya que si un login es fracasado, 
		opcionResJSP.put("validarse", resJSP);  //el rolDespues se cambia al publico
		
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/principal.jsp");
		resJSP.put("FRACASO","/principal.jsp");
		opcionResJSP.put("modificarDatosPersonal", resJSP);
		
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/listaViajes.jsp");
		opcionResJSP.put("listarViajes", resJSP);
		
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/principal.jsp");
		opcionResJSP.put("irPrincipal", resJSP);
		
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/modificarDatosPersonal.jsp");
		opcionResJSP.put("irModificarDatosPersonal", resJSP);
		
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/registrarViaje.jsp");
		opcionResJSP.put("irRegistrarViaje", resJSP);
		
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/principal.jsp");
		resJSP.put("FRACASO","/principal.jsp");
		opcionResJSP.put("registrarViaje", resJSP);
		
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/mostrarViaje.jsp");
		opcionResJSP.put("mostrarViaje", resJSP);
		
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/principal.jsp");
		resJSP.put("FRACASO","/principal.jsp");
		opcionResJSP.put("solicitarPlaza", resJSP);//machacas al mostrarViaje su queryString, no puede hacer eso 
		
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/principal.jsp");
		opcionResJSP.put("cancelarSolicitud", resJSP);
		
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/misViajes.jsp");
		opcionResJSP.put("misViajes", resJSP);
		
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/comentarViaje.jsp");
		opcionResJSP.put("irComentarViaje", resJSP);
		
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/principal.jsp");
		resJSP.put("FRACASO","/principal.jsp");
		opcionResJSP.put("comentarViaje", resJSP);
		
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/principal.jsp");//al principal para mostrar mnensaje
		opcionResJSP.put("confirmarPasajero", resJSP);
		
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/principal.jsp");
		opcionResJSP.put("excluirPasajero", resJSP);
		
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/principal.jsp");
		opcionResJSP.put("dejarComentarioAlPromotor", resJSP);
		
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/principal.jsp");
		opcionResJSP.put("cancelarViaje", resJSP);
		
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/modificarViaje.jsp");
		opcionResJSP.put("irModificarViaje", resJSP);
		
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/principal.jsp");
		resJSP.put("FRACASO","/principal.jsp");
		opcionResJSP.put("modificarViaje", resJSP);
		
		//opcional
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/listaViajes.jsp");
		opcionResJSP.put("ordenacionOrigen", resJSP);
		
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/listaViajes.jsp");
		opcionResJSP.put("ordenacionDestino", resJSP);
		
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/listaViajes.jsp");
		opcionResJSP.put("ordenacionFecha", resJSP);
		
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/listaViajes.jsp");
		opcionResJSP.put("ordenacionPromotor", resJSP);
		
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/listaViajes.jsp");
		opcionResJSP.put("filtrarViajes", resJSP);
		
		mapaDeNavegacion.put("REGISTRADO",opcionResJSP);
	}
			
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {

		doGet(req, res);
	}

}