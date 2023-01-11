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

@WebServlet("/api/empleados/")
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
	private static EmpleadoService service;

	/**
	 * 
	 */
	public EmpleadosEndpoint() {
		super();
		logger.info("Empleado EndPoint creado");
		service = EmpleadoServiceImpl.getInstance();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		logger.info("Get receive...");
		Empleado resultEmpleado = null;
		Integer id = 0;
		/*
		 * Validar datos de la petición
		 */
		try {
			id = Integer.parseInt(request.getParameter("id"));
			logger.info("GET at {} with ID: {}", request.getContextPath(), id);
		} catch (Exception e) {
			logger.error("BadRequest: " + e.getMessage());
			response.addHeader("Content-Type", "text/html");
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
		resultEmpleado = service.findById(id);
		if (resultEmpleado == null) {
			response.addHeader("Content-Type", "application/json");
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			try {
				response.getWriter().println("El empleado no existe.");
			} catch (IOException ioe) {
				logger.error("Exception: " + ioe.getMessage());
			}
			return;
		}
		/*
		 * Devolver los resultados
		 */
		try {
			response.addHeader("Content-Type", "application/json");
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().println(g.toJson(resultEmpleado));
		} catch (Exception e) {
			response.addHeader("Content-Type", "text/html");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			try {
				response.getWriter().println(e.getMessage());
			} catch (IOException ioe) {
				logger.error("Exception: " + ioe.getMessage());
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		logger.info("Post receive...");
		/*
		 * Variables Empleado
		 */
		Integer id = 0;
		String nombre = null;
		String imagen = null;
		String cargo = null;
		Empleado empleado = null;
		/*
		 * Validar datos de la petición
		 */
		try {
			id = Integer.parseInt(request.getParameter("id"));
			nombre = request.getParameter("nombre");
			imagen = request.getParameter("imagen");
			cargo = request.getParameter("cargo");
			logger.info("Data:" + id + "," + nombre + "," + imagen + "," + cargo + ",");
		} catch (Exception e) {
			logger.error("BadRequest: " + e.getMessage());
			response.addHeader("Content-Type", "text/html");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			try {
				response.getWriter().println("Petición incorrecta: faltan datos");
			} catch (IOException ioe) {
				logger.error("Exception: " + ioe.getMessage());
			}
		}
		/*
		 * Validar que no exista el Empleado
		 */
		empleado = service.findById(id);
		if (empleado != null) {
			response.addHeader("Content-Type", "application/json");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			try {
				response.getWriter().println("El empleado ya existe.");
				response.getWriter().println(g.toJson(empleado));
			} catch (IOException ioe) {
				logger.error("Exception: " + ioe.getMessage());
			}
			return;
		}
		/*
		 * Creando el empleado Si no existe
		 */
		try {
			empleado = service.create(id, nombre, imagen, cargo);
			response.addHeader("Content-Type", "application/json");
			response.setStatus(HttpServletResponse.SC_CREATED);
			try {
				response.getWriter().println(g.toJson(empleado));
			} catch (IOException ioe) {
				logger.error("Exception: " + ioe.getMessage());
			}
		} catch (EmpleadoException e) {
			logger.error("Empleado Exception: " + e.getMessage());
		}
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) {
		logger.info("Put receive...");
		/*
		 * Variables Empleado
		 */
		Integer id = 0;
		String nombre = null;
		String imagen = null;
		String cargo = null;
		Empleado empleado = null;
		/*
		 * Validar datos de la petición
		 */
		try {
			id = Integer.parseInt(request.getParameter("id"));
			nombre = request.getParameter("nombre");
			imagen = request.getParameter("imagen");
			cargo = request.getParameter("cargo");
		} catch (Exception e) {
			logger.error("BadRequest: " + e.getMessage());
			response.addHeader("Content-Type", "text/html");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			try {
				response.getWriter().println("Petición incorrecta");
			} catch (IOException ioe) {
				logger.error("Exception: " + ioe.getMessage());
			}
		}
		/*
		 * Validar que si exista el Empleado
		 */
		empleado = service.findById(id);
		if (empleado == null) {
			response.addHeader("Content-Type", "application/json");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			try {
				response.getWriter().println("El empleado no existe.");
			} catch (IOException ioe) {
				logger.error("Exception: " + ioe.getMessage());
			}
			return;
		}
		/*
		 * Actualizando el empleado
		 */
		Empleado newEmpleado = new Empleado(id, nombre, imagen, cargo);
		try {
			empleado = service.update(newEmpleado);
			response.setStatus(HttpServletResponse.SC_CREATED);
			try {
				response.getWriter().println(g.toJson(empleado));
			} catch (IOException ioe) {
				logger.error("Exception: " + ioe.getMessage());
			}
		} catch (EmpleadoException e) {
			logger.error("Empleado Exception: " + e.getMessage());
		}

	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
		logger.info("Delete receive...");
		Empleado resultEmpleado = null;
		Integer id = 0;
		/*
		 * Validar datos de la petición
		 */
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			logger.error("BadRequest: " + e.getMessage());
			response.addHeader("Content-Type", "text/html");
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
		resultEmpleado = service.findById(id);
		/*
		 * Eliminar el empleado
		 */
		if (resultEmpleado == null) {
			response.addHeader("Content-Type", "application/json");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			try {
				response.getWriter().println("El empleado no existe.");
			} catch (IOException ioe) {
				logger.error("Exception: " + ioe.getMessage());
			}
			return;
		}
		try {
			response.addHeader("Content-Type", "application/json");
			if (service.delete(resultEmpleado)) {
				response.setStatus(HttpServletResponse.SC_OK);
				try {
					response.getWriter().println(g.toJson(resultEmpleado));
				} catch (IOException ioe) {
					logger.error("Exception: " + ioe.getMessage());
				}
			} else {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				try {
					response.getWriter().println(g.toJson("El empleado no existe."));
				} catch (IOException ioe) {
					logger.error("Exception: " + ioe.getMessage());
				}
			}
		} catch (EmpleadoException e) {
			logger.error("Empleado Exception: " + e.getMessage());
		}
	}
}
