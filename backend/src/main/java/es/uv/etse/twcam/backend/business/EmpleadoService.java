package es.uv.etse.twcam.backend.business;

import java.util.List;

import es.uv.etse.twcam.backend.business.EmpleadoExceptions.EmpleadoException;

public interface EmpleadoService{

    /*
     * Lista de todos los Empleados
     */
    public List<Empleado> listAll() throws EmpleadoException;

    /*
     * Obtener empeado mediante ID
     */
    public Empleado findById(Integer id) throws EmpleadoException;

    /*
     * Crear empleado
     */
    public Empleado create(Empleado newEmpleado) throws EmpleadoException;

    /*
     * Actualizar datos de empleado
     */
    public Empleado update(Empleado newData) throws EmpleadoException;

    /*
     * Eliminar empleado
     */
    public void delete(Empleado empleado) throws EmpleadoException;






    
}