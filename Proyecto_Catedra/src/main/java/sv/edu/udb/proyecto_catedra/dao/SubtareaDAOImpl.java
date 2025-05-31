package sv.edu.udb.proyecto_catedra.dao;

import sv.edu.udb.proyecto_catedra.connection.Conexion;
import sv.edu.udb.proyecto_catedra.model.Subtarea;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubtareaDAOImpl implements SubtareaDAO {

    @Override
    public void crearSubtarea(Subtarea subtarea) {
        String sql = "INSERT INTO subtareas (idAsignacion, nombre, descripcion, estado) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, subtarea.getId());
            stmt.setString(2, subtarea.getNombre());
            stmt.setString(3, subtarea.getDescripcion());
            stmt.setString(4, subtarea.getEstado());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La creación de la subtarea falló, no se insertaron filas.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    subtarea.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("La creación de la subtarea falló, no se obtuvo el ID.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear la subtarea", e);
        }
    }

    @Override
    public void editarSubtarea(Subtarea subtarea) {
        String sql = "UPDATE subtareas SET idAsignacion = ?, nombre = ?, descripcion = ?, estado = ? "
                + "WHERE id = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, subtarea.getId());
            stmt.setString(2, subtarea.getNombre());
            stmt.setString(3, subtarea.getDescripcion());
            stmt.setString(4, subtarea.getEstado());
            stmt.setInt(5, subtarea.getId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La actualización de la subtarea falló, no se afectaron filas.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la subtarea", e);
        }
    }

    @Override
    public void eliminarSubtarea(int idSubtarea) {
        String sql = "DELETE FROM subtareas WHERE id = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSubtarea);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La eliminación de la subtarea falló, no se afectaron filas.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la subtarea", e);
        }
    }

    @Override
    public Subtarea obtenerSubtarea(int idSubtarea) {
        String sql = "SELECT * FROM subtareas WHERE id = ?";
        Subtarea subtarea = null;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSubtarea);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    subtarea = mapearSubtarea(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener la subtarea", e);
        }

        return subtarea;
    }

    @Override
    public List<Subtarea> listarSubtareasPorAsignacion(int idAsignacion) {
        String sql = "SELECT * FROM subtareas WHERE idAsignacion = ? ORDER BY nombre";
        List<Subtarea> subtareas = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAsignacion);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    subtareas.add(mapearSubtarea(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar las subtareas por asignación", e);
        }

        return subtareas;
    }

    private Subtarea mapearSubtarea(ResultSet rs) throws SQLException {
        Subtarea subtarea = new Subtarea();
        subtarea.setId(rs.getInt("id"));
        subtarea.setIdAsignacion(rs.getInt("idAsignacion"));
        subtarea.setNombre(rs.getString("nombre"));
        subtarea.setDescripcion(rs.getString("descripcion"));
        subtarea.setEstado(rs.getString("estado"));

        return subtarea;
    }
}
