package es.uv.etse.twcam.backend.business.EmpleadoExceptions;

public class EmpleadoIncorrectDataException extends EmpleadoException {

    public EmpleadoIncorrectDataException(String msg) {
        super("Error:" +msg);
    }
    
}