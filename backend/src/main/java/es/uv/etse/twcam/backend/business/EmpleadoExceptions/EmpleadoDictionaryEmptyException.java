package es.uv.etse.twcam.backend.business.EmpleadoExceptions;

public class EmpleadoDictionaryEmptyException extends EmpleadoException {

    public EmpleadoDictionaryEmptyException(String msg) {
        super(msg+ "El diccionario está vaćio o no contiene IDs.");
    }
    
}
