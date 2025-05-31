package sv.edu.udb.proyecto_catedra.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sv.edu.udb.proyecto_catedra.dao.AsignacionDAO;
import sv.edu.udb.proyecto_catedra.dao.AsignacionDAOImpl;
import sv.edu.udb.proyecto_catedra.dao.SubtareaDAO;
import sv.edu.udb.proyecto_catedra.dao.SubtareaDAOImpl;
import sv.edu.udb.proyecto_catedra.model.Subtarea;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "SubtareaController", urlPatterns = {"/subtareas"})
public class SubTareaController extends HttpServlet {
    private SubtareaDAO subtareaDAO;
    private AsignacionDAO asignacionDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        subtareaDAO = new SubtareaDAOImpl();
        asignacionDAO = new AsignacionDAOImpl();
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
                    eliminarSubtarea(request, response);
                    break;
                case "cambiarEstado":
                    cambiarEstadoSubtarea(request, response);
                    break;
                default:
                    listarSubtareas(request, response);
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
                agregarSubtarea(request, response);
            } else if ("editar".equals(action)) {
                editarSubtarea(request, response);
            } else {
                listarSubtareas(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Error al procesar la solicitud: " + e.getMessage(), e);
        }
    }

    private void listarSubtareas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idAsignacion = Integer.parseInt(request.getParameter("idAsignacion"));
        List<Subtarea> subtareas = subtareaDAO.listarSubtareasPorAsignacion(idAsignacion);

        request.setAttribute("subtareas", subtareas);
        request.setAttribute("idAsignacion", idAsignacion);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/subtarea/listar.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioAgregar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idAsignacion = Integer.parseInt(request.getParameter("idAsignacion"));
        request.setAttribute("idAsignacion", idAsignacion);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/subtarea/agregar.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idSubtarea = Integer.parseInt(request.getParameter("id"));
        Subtarea subtarea = subtareaDAO.obtenerSubtarea(idSubtarea);

        if (subtarea == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Subtarea no encontrada");
            return;
        }

        request.setAttribute("subtarea", subtarea);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/subtarea/editar.jsp");
        dispatcher.forward(request, response);
    }

    private void agregarSubtarea(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Subtarea subtarea = new Subtarea();
        mapearParametrosSubtarea(request, subtarea);

        subtareaDAO.crearSubtarea(subtarea);

        request.getSession().setAttribute("mensajeExito", "Subtarea creada exitosamente");
        response.sendRedirect(request.getContextPath() + "/asignaciones?action=ver&id=" + subtarea.getId());
    }

    private void editarSubtarea(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idSubtarea = Integer.parseInt(request.getParameter("idSubtarea"));
        Subtarea subtareaExistente = subtareaDAO.obtenerSubtarea(idSubtarea);

        if (subtareaExistente == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Subtarea no encontrada");
            return;
        }

        Subtarea subtarea = new Subtarea();
        subtarea.setId(idSubtarea);
        mapearParametrosSubtarea(request, subtarea);

        subtareaDAO.editarSubtarea(subtarea);

        request.getSession().setAttribute("mensajeExito", "Subtarea actualizada exitosamente");
        response.sendRedirect(request.getContextPath() + "/asignaciones?action=ver&id=" + subtarea.getId());
    }

    private void eliminarSubtarea(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idSubtarea = Integer.parseInt(request.getParameter("id"));
        Subtarea subtarea = subtareaDAO.obtenerSubtarea(idSubtarea);

        if (subtarea == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Subtarea no encontrada");
            return;
        }

        int idAsignacion = subtarea.getId();
        subtareaDAO.eliminarSubtarea(idSubtarea);

        request.getSession().setAttribute("mensajeExito", "Subtarea eliminada exitosamente");
        response.sendRedirect(request.getContextPath() + "/asignaciones?action=ver&id=" + idAsignacion);
    }

    private void cambiarEstadoSubtarea(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idSubtarea = Integer.parseInt(request.getParameter("id"));
        String nuevoEstado = request.getParameter("estado");

        Subtarea subtarea = subtareaDAO.obtenerSubtarea(idSubtarea);
        if (subtarea == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Subtarea no encontrada");
            return;
        }

        subtarea.setEstado(nuevoEstado);
        subtareaDAO.editarSubtarea(subtarea);

        request.getSession().setAttribute("mensajeExito", "Estado de subtarea actualizado a: " + nuevoEstado);
        response.sendRedirect(request.getContextPath() + "/asignaciones?action=ver&id=" + subtarea.getId());
    }

    private void mapearParametrosSubtarea(HttpServletRequest request, Subtarea subtarea) {
        subtarea.setIdAsignacion(Integer.parseInt(request.getParameter("idAsignacion")));
        subtarea.setNombre(request.getParameter("nombre"));
        subtarea.setDescripcion(request.getParameter("descripcion"));
        subtarea.setEstado(request.getParameter("estado"));
    }
}
