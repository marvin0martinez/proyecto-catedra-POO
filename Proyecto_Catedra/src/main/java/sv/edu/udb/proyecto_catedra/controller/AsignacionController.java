package sv.edu.udb.proyecto_catedra.controller;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sv.edu.udb.proyecto_catedra.dao.*;
import sv.edu.udb.proyecto_catedra.model.Asignacion;
import sv.edu.udb.proyecto_catedra.model.Empleado;
import sv.edu.udb.proyecto_catedra.model.Subtarea;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "AsignacionController", urlPatterns = {"/asignaciones"})
public class AsignacionController extends HttpServlet {
    private AsignacionDAO asignacionDAO;
    private CotizacionDAO cotizacionDAO;
    private EmpleadoDAO empleadoDAO;
    private SubtareaDAO subtareaDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        asignacionDAO = new AsignacionDAOImpl();
        cotizacionDAO = new CotizacionDAOImpl();
        empleadoDAO = new EmpleadoDAOImpl();
        subtareaDAO = new SubtareaDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "listar";
        }

        try {
            switch (action) {
                case "agregar":
                    mostrarFormularioAgregar(request, response);
                    break;
                case "editar":
                    mostrarFormularioEditar(request, response);
                    break;
                case "eliminar":
                    eliminarAsignacion(request, response);
                    break;
                case "ver":
                    verAsignacion(request, response);
                    break;
                case "calcularCosto":
                    calcularCostoActividad(request, response);
                    break;
                default:
                    listarAsignaciones(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Error al procesar la solicitud: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("agregar".equals(action)) {
                agregarAsignacion(request, response);
            } else if ("editar".equals(action)) {
                editarAsignacion(request, response);
            } else {
                listarAsignaciones(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Error al procesar la solicitud: " + e.getMessage(), e);
        }
    }

    private void listarAsignaciones(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Asignacion> asignaciones = asignacionDAO.listarAsignaciones();
        request.setAttribute("asignaciones", asignaciones);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/asignacion/listar.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioAgregar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idCotizacion = Integer.parseInt(request.getParameter("idCotizacion"));
        List<Empleado> empleados = empleadoDAO.listarEmpleados();

        request.setAttribute("idCotizacion", idCotizacion);
        request.setAttribute("empleados", empleados);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/asignacion/agregar.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idAsignacion = Integer.parseInt(request.getParameter("id"));
        Asignacion asignacion = asignacionDAO.obtenerAsignacion(idAsignacion);

        if (asignacion == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Asignación no encontrada");
            return;
        }

        List<Empleado> empleados = empleadoDAO.listarEmpleados();
        List<Subtarea> subtareas = subtareaDAO.listarSubtareasPorAsignacion(idAsignacion);

        request.setAttribute("asignacion", asignacion);
        request.setAttribute("empleados", empleados);
        request.setAttribute("subtareas", subtareas);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/asignacion/editar.jsp");
        dispatcher.forward(request, response);
    }

    private void verAsignacion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idAsignacion = Integer.parseInt(request.getParameter("id"));
        Asignacion asignacion = asignacionDAO.obtenerAsignacion(idAsignacion);

        if (asignacion == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Asignación no encontrada");
            return;
        }

        List<Subtarea> subtareas = subtareaDAO.listarSubtareasPorAsignacion(idAsignacion);
        Empleado empleado = empleadoDAO.obtenerEmpleado(asignacion.getIdEmpleado());

        request.setAttribute("asignacion", asignacion);
        request.setAttribute("subtareas", subtareas);
        request.setAttribute("empleado", empleado);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/asignacion/ver.jsp");
        dispatcher.forward(request, response);
    }

    private void agregarAsignacion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Asignacion asignacion = new Asignacion();
        mapearParametrosAsignacion(request, asignacion);

        asignacionDAO.crearAsignacion(asignacion);

        request.getSession().setAttribute("mensajeExito", "Asignación creada exitosamente");
        response.sendRedirect(request.getContextPath() + "/cotizaciones?action=ver&id=" + asignacion.getIdCotizacion());
    }

    private void editarAsignacion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idAsignacion = Integer.parseInt(request.getParameter("idAsignacion"));
        Asignacion asignacionExistente = asignacionDAO.obtenerAsignacion(idAsignacion);

        if (asignacionExistente == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Asignación no encontrada");
            return;
        }

        Asignacion asignacion = new Asignacion();
        asignacion.setIdAsignacion(idAsignacion);
        mapearParametrosAsignacion(request, asignacion);

        asignacionDAO.editarAsignacion(asignacion);

        request.getSession().setAttribute("mensajeExito", "Asignación actualizada exitosamente");
        response.sendRedirect(request.getContextPath() + "/asignaciones?action=ver&id=" + idAsignacion);
    }

    private void eliminarAsignacion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idAsignacion = Integer.parseInt(request.getParameter("id"));
        Asignacion asignacion = asignacionDAO.obtenerAsignacion(idAsignacion);

        if (asignacion == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Asignación no encontrada");
            return;
        }

        int idCotizacion = asignacion.getIdCotizacion();
        asignacionDAO.eliminarAsignacion(idAsignacion);

        request.getSession().setAttribute("mensajeExito", "Asignación eliminada exitosamente");
        response.sendRedirect(request.getContextPath() + "/cotizaciones?action=ver&id=" + idCotizacion);
    }

    private void calcularCostoActividad(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idAsignacion = Integer.parseInt(request.getParameter("id"));
        Asignacion asignacion = asignacionDAO.obtenerAsignacion(idAsignacion);

        if (asignacion == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Asignación no encontrada");
            return;
        }

        float costo = asignacion.getDuracionHoras() * asignacion.getCostoPorHora();
        asignacion.setCostoBase(costo);
        asignacionDAO.editarAsignacion(asignacion);

        request.getSession().setAttribute("mensajeExito", "Costo de actividad calculado: $" + costo);
        response.sendRedirect(request.getContextPath() + "/asignaciones?action=ver&id=" + idAsignacion);
    }

    private void mapearParametrosAsignacion(HttpServletRequest request, Asignacion asignacion) {
        asignacion.setIdCotizacion(Integer.parseInt(request.getParameter("idCotizacion")));
        asignacion.setIdEmpleado(Integer.parseInt(request.getParameter("idEmpleado")));
        asignacion.setTituloActividad(request.getParameter("tituloActividad"));
        asignacion.setFechaInicio(new Date(Long.parseLong(request.getParameter("fechaInicio"))));

        if (request.getParameter("fechaFin") != null && !request.getParameter("fechaFin").isEmpty()) {
            asignacion.setFechaFin(new Date(Long.parseLong(request.getParameter("fechaFin"))));
        }

        asignacion.setDuracionHoras(Float.parseFloat(request.getParameter("duracionHoras")));
        asignacion.setCostoPorHora(Float.parseFloat(request.getParameter("costoPorHora")));
    }
}
