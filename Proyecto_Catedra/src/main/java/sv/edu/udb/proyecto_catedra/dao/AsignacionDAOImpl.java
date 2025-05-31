package sv.edu.udb.proyecto_catedra.dao;

import sv.edu.udb.proyecto_catedra.connection.Conexion;
import sv.edu.udb.proyecto_catedra.model.Asignacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsignacionDAOImpl implements AsignacionDAO {

    @Override
    public void crearAsignacion(Asignacion asignacion) {
        String sql = "INSERT INTO asignaciones (idCotizacion, idEmpleado, tituloActividad, "
                + "listaActividades, fechaInicio, fechaFin, duracionHoras, costoPorHora, costoBase) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, asignacion.getIdCotizacion());
            stmt.setInt(2, asignacion.getIdEmpleado());
            stmt.setString(3, asignacion.getTituloActividad());
            stmt.setString(4, String.join(",", asignacion.getListaActividades()));
            stmt.setTimestamp(5, new Timestamp(asignacion.getFechaInicio().getTime()));

            if (asignacion.getFechaFin() != null) {
                stmt.setTimestamp(6, new Timestamp(asignacion.getFechaFin().getTime()));
            } else {
                stmt.setNull(6, Types.TIMESTAMP);
            }

            stmt.setFloat(7, asignacion.getDuracionHoras());
            stmt.setFloat(8, asignacion.getCostoPorHora());
            stmt.setFloat(9, asignacion.getCostoBase());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La creación de la asignación falló, no se insertaron filas.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    asignacion.setIdAsignacion(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("La creación de la asignación falló, no se obtuvo el ID.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear la asignación", e);
        }
    }

    @Override
    public void editarAsignacion(Asignacion asignacion) {
        String sql = "UPDATE asignaciones SET idCotizacion = ?, idEmpleado = ?, tituloActividad = ?, "
                + "listaActividades = ?, fechaInicio = ?, fechaFin = ?, duracionHoras = ?, "
                + "costoPorHora = ?, costoBase = ? WHERE idAsignacion = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, asignacion.getIdCotizacion());
            stmt.setInt(2, asignacion.getIdEmpleado());
            stmt.setString(3, asignacion.getTituloActividad());
            stmt.setString(4, String.join(",", asignacion.getListaActividades()));
            stmt.setTimestamp(5, new Timestamp(asignacion.getFechaInicio().getTime()));

            if (asignacion.getFechaFin() != null) {
                stmt.setTimestamp(6, new Timestamp(asignacion.getFechaFin().getTime()));
            } else {
                stmt.setNull(6, Types.TIMESTAMP);
            }

            stmt.setFloat(7, asignacion.getDuracionHoras());
            stmt.setFloat(8, asignacion.getCostoPorHora());
            stmt.setFloat(9, asignacion.getCostoBase());
            stmt.setInt(10, asignacion.getIdAsignacion());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La actualización de la asignación falló, no se afectaron filas.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la asignación", e);
        }
    }

    @Override
    public void eliminarAsignacion(int idAsignacion) {
        String sql = "DELETE FROM asignaciones WHERE idAsignacion = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAsignacion);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La eliminación de la asignación falló, no se afectaron filas.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la asignación", e);
        }
    }

    @Override
    public Asignacion obtenerAsignacion(int idAsignacion) {
        String sql = "SELECT * FROM asignaciones WHERE idAsignacion = ?";
        Asignacion asignacion = null;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAsignacion);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    asignacion = mapearAsignacion(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener la asignación", e);
        }

        return asignacion;
    }

    @Override
    public List<Asignacion> listarAsignaciones() {
        String sql = "SELECT * FROM asignaciones ORDER BY fechaInicio DESC";
        List<Asignacion> asignaciones = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                asignaciones.add(mapearAsignacion(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar las asignaciones", e);
        }

        return asignaciones;
    }

    @Override
    public List<Asignacion> listarAsignacionesPorCotizacion(int idCotizacion) {
        String sql = "SELECT * FROM asignaciones WHERE idCotizacion = ? ORDER BY fechaInicio DESC";
        List<Asignacion> asignaciones = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCotizacion);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    asignaciones.add(mapearAsignacion(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar las asignaciones por cotización", e);
        }

        return asignaciones;
    }

    @Override
    public List<Asignacion> listarAsignacionesPorEmpleado(int idEmpleado) {
        String sql = "SELECT * FROM asignaciones WHERE idEmpleado = ? ORDER BY fechaInicio DESC";
        List<Asignacion> asignaciones = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEmpleado);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    asignaciones.add(mapearAsignacion(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar las asignaciones por empleado", e);
        }

        return asignaciones;
    }

    private Asignacion mapearAsignacion(ResultSet rs) throws SQLException {
        Asignacion asignacion = new Asignacion();
        asignacion.setIdAsignacion(rs.getInt("idAsignacion"));
        asignacion.setIdCotizacion(rs.getInt("idCotizacion"));
        asignacion.setIdEmpleado(rs.getInt("idEmpleado"));
        asignacion.setTituloActividad(rs.getString("tituloActividad"));

        String actividades = rs.getString("listaActividades");
        if (actividades != null && !actividades.isEmpty()) {
            asignacion.setListaActividades(List.of(actividades.split(",")));
        }

        asignacion.setFechaInicio(rs.getTimestamp("fechaInicio"));
        asignacion.setFechaFin(rs.getTimestamp("fechaFin"));
        asignacion.setDuracionHoras(rs.getFloat("duracionHoras"));
        asignacion.setCostoPorHora(rs.getFloat("costoPorHora"));
        asignacion.setCostoBase(rs.getFloat("costoBase"));

        return asignacion;
    }
}
