package es.uv.etse.twcam.backend.business.EmpleadoExceptions;

public class EmpleadoAlreadyExistException extends EmpleadoException {

    public EmpleadoAlreadyExistException(String msg) {
        super(msg+ "Empleado existente en el sistema.");
    }
    
}
