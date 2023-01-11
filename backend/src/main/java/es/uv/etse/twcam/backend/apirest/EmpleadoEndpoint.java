package es.uv.etse.twcam.backend.apirest;

import javax.servlet.http.HttpServlet;
import org.apache.logging.log4j.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.uv.etse.twcam.backend.business.EmpleadoService;
import es.uv.etse.twcam.backend.business.EmpleadoServiceImpl;

public class EmpleadoEndpoint extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LogManager.getLogger(ProductosEndpoint.class.getName()); // <7>

    /**
	 * Nombre del endpoint
	 */
	private static final String END_POINT_NAME = "empleados";

	/**
	 * Gson parser
	 */
	private final Gson g = new GsonBuilder().create();

    /**
	 * Servicio sobre productos.
	 */
	private static EmpleadoService service = EmpleadoServiceImpl.getInstance();

	/**
	 * @return 
	 */
	public void ProductosEndpoint() {
		logger.info("Product EndPoint creado"); // <7>
	}

}
