package es.uv.etse.twcam.backend.business;

/**
 * @author Felipe Valencia - fevabac@alumni.uv.es
 */
public class Empleado {

    /*
     * Atributos
     */
    private Integer id;
    private String nombre;
    private String imagen;
    private String puesto;

    /*
     * Constructor
     */
    public Empleado(Integer id, String nombre, String imagen, String puesto) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.puesto = puesto;
    }

    /*
     * Getters y Setters
     */

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return this.imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getPuesto() {
        return this.puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

}