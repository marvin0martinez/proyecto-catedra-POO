package sv.edu.udb.proyecto_catedra.dao;

import sv.edu.udb.proyecto_catedra.model.Asignacion;

import java.util.List;

public interface AsignacionDAO {

    void crearAsignacion(Asignacion asignacion);
    void editarAsignacion(Asignacion asignacion);
    void eliminarAsignacion(int idAsignacion);
    Asignacion obtenerAsignacion(int idAsignacion);
    List<Asignacion> listarAsignaciones();
    List<Asignacion> listarAsignacionesPorCotizacion(int idCotizacion);
    List<Asignacion> listarAsignacionesPorEmpleado(int idEmpleado);
}
