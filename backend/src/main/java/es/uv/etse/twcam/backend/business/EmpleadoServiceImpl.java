package es.uv.etse.twcam.backend.business;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import es.uv.etse.twcam.backend.business.EmpleadoExceptions.EmpleadoDictionaryEmptyException;
import es.uv.etse.twcam.backend.business.EmpleadoExceptions.EmpleadoException;
import es.uv.etse.twcam.backend.business.EmpleadoExceptions.EmpleadoIncorrectDataException;

public class EmpleadoServiceImpl implements EmpleadoService {

    /**
     * Instancia única.
     */
    private static EmpleadoServiceImpl the;

    /**
     * Diccionario para el almacenamiento de productos.
     */
    protected Map<Integer, Empleado> dictionary;

    /*
     * Métodos propios de la clase
     */
    private EmpleadoServiceImpl() {
        dictionary = new Hashtable<>();
    }

    public static EmpleadoServiceImpl getInstance() {
        if (the == null) {
            the = new EmpleadoServiceImpl();
        }
        return the;
    }

    public void clearInstance() {
        if (the != null) {
            the.dictionary.clear();
            the = null;
        }
    }

    /*
     * Métodos de la interfaz
     */
    public List<Empleado> listAll() throws EmpleadoException {
        List<Empleado> listaEmpleados = new ArrayList<>();
        if(dictionary.values()!=null){
            listaEmpleados.addAll(dictionary.values());
            return listaEmpleados;
        }
        else{
            throw new EmpleadoDictionaryEmptyException("Diccionario vacío.");
        }        
    }

    public Empleado findById(Integer id) throws EmpleadoException{
        if(dictionary.containsValue(id)){
            return dictionary.get(id);
        }else{
            throw new EmpleadoDictionaryEmptyException("No se encuentra el ID dentro del diccionario.");
        }
    }

    public Empleado create(Empleado newEmpleado) throws EmpleadoException {
        if (newEmpleado != null && newEmpleado.getId() != null) {
            dictionary.put(newEmpleado.getId(), newEmpleado);
        }
        else {
            throw new EmpleadoIncorrectDataException("Eror de datos");
        }

        return newEmpleado;
    }

    public Empleado update(Empleado newEmpleadoData) throws EmpleadoException {
        if(newEmpleadoData!=null){
            if(dictionary.containsKey(newEmpleadoData.getId())){
                dictionary.put(newEmpleadoData.getId(), newEmpleadoData);
            }else{
                throw new EmpleadoDictionaryEmptyException("No se encuentra el ID dentro del diccionario.");
            }
        }else{
            throw new EmpleadoIncorrectDataException("Eror de datos");            
        }

        return newEmpleadoData;
    }

    public Boolean delete(Empleado empleadoToDelete) throws EmpleadoException {
        Boolean deleted = false;
        if(empleadoToDelete!=null){
            if(dictionary.containsKey(empleadoToDelete.getId())){
                dictionary.remove(empleadoToDelete.getId());
                deleted = true;
            }else{
                throw new EmpleadoDictionaryEmptyException("No se encuentra el ID dentro del diccionario.");
            }
        }else{
            throw new EmpleadoIncorrectDataException("Eror de datos");            
        }

        return deleted;
    }

}
