package sv.edu.udb.proyecto_catedra.dao;

import sv.edu.udb.proyecto_catedra.model.Subtarea;

import java.util.List;

public interface SubtareaDAO {

    void crearSubtarea(Subtarea subtarea);
    void editarSubtarea(Subtarea subtarea);
    void eliminarSubtarea(int idSubtarea);
    Subtarea obtenerSubtarea(int idSubtarea);
    List<Subtarea> listarSubtareasPorAsignacion(int idAsignacion);
}
