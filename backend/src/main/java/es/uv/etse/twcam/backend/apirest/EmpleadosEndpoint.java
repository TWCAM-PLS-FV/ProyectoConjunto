package es.uv.etse.twcam.backend.apirest;

import javax.servlet.http.HttpServlet;
import org.apache.logging.log4j.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.uv.etse.twcam.backend.business.Empleado;
import es.uv.etse.twcam.backend.business.EmpleadoService;
import es.uv.etse.twcam.backend.business.EmpleadoServiceImpl;
import es.uv.etse.twcam.backend.business.EmpleadoExceptions.EmpleadoException;

@WebServlet("/api/empleados/*")
public class EmpleadosEndpoint extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger(ProductosEndpoint.class.getName());
	/**
	 * Gson parser
	 */
	private final Gson g = new GsonBuilder().create();

	/**
	 * Servicio sobre Empleados.
	 */
	private static EmpleadoService service = EmpleadoServiceImpl.getInstance();

	/**
	 * 
	 */
	public EmpleadosEndpoint() {
		super();
		logger.info("Empleado EndPoint creado");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		Empleado resultEmpleado = null;
		Integer id = null;
		/*
		 * Validar datos de la petición
		 */
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			logger.error("BadRequest: " + e.getMessage());
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			try {
				response.getWriter().println("Petición incorrecta");
			} catch (IOException ioe) {
				logger.error("Exception: " + ioe.getMessage());
			}
		}
		/*
		 * Obtener los datos según el ID
		 */
		logger.info("doGet id: " + id);
		try {
			resultEmpleado = service.findById(id);
		} catch (EmpleadoException e) {
			logger.error("Empleado Exception: " + e.getMessage());
		}
		/*
		 * Devolver los resultados
		 */
		try {
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().println(g.toJson(resultEmpleado));
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			try {
				response.getWriter().println(e.getMessage());
			} catch (IOException ioe) {
				logger.error("Exception: " + ioe.getMessage());
			}
		}
	}

}
