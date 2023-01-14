package es.uv.etse.twcam.backend.apirest;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.logging.log4j.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.uv.etse.twcam.backend.business.Producto;
import es.uv.etse.twcam.backend.business.Empleado;
import es.uv.etse.twcam.backend.business.EmpleadoService;
import es.uv.etse.twcam.backend.business.EmpleadoServiceImpl;
import es.uv.etse.twcam.backend.business.ProductException;
import es.uv.etse.twcam.backend.business.ProductsService;
import es.uv.etse.twcam.backend.business.ProductsServiceDictionaryImpl;
import es.uv.etse.twcam.backend.business.EmpleadoExceptions.EmpleadoException;

/**
 * Servlet de inicializaci&oacute;n
 * 
 * @author <a href="mailto:raul.penya@uv.es">Ra&uacute;l Pe&ntilde;a-Ortiz</a>
 */
public class InitServlet extends HttpServlet {

    /**
     * Identificador de versi&oacute;n
     */
    private static final long serialVersionUID = 1L;

    /**
     * Logger
     */
    protected static Logger logger = LogManager.getLogger(InitServlet.class.getName());

    @Override
    public void init() throws ServletException {
        try {

            logger.info("Starting angular-j2e-example apirest ...");

            String jsonFile = getServletConfig().getInitParameter("json-database"); // <1>

            InputStream jsonStream = getServletContext().getResourceAsStream(jsonFile); // <2>

            initEmpleadoService(jsonStream); // <3>

            logger.info("plc-pls-mps-tutorial apirest is started");

        } catch (Exception e) {
            logger.error("plc-pls-mps-tutorial apirest is not able to be started: ", e);
            throw new ServletException(e);
        }
    }

    /**
     * Crea el servicio de productos y lo inicializa a partir de un stream JSON.
     * 
     * @param jsonStream Stream JSON
     * @throws Exception Indicador de errores
     */
    public static EmpleadoService initEmpleadoService(InputStream jsonStream)
            throws EmpleadoException { // <3>

        EmpleadoServiceImpl service = EmpleadoServiceImpl.getInstance();

        Reader jsonReader = new InputStreamReader(jsonStream);

        Gson gson = new GsonBuilder().create();

        Empleado[] empleados = gson.fromJson(jsonReader, Empleado[].class);

        for (Empleado empleado : empleados) {
            service.create(empleado);
        }

        logger.info("Cargados {} empleados", empleados.length);

        return service;
    }
}