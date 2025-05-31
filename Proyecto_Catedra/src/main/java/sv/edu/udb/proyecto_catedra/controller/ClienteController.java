package sv.edu.udb.proyecto_catedra.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sv.edu.udb.proyecto_catedra.dao.ClienteDAO;
import sv.edu.udb.proyecto_catedra.dao.ClienteDAOImpl;
import sv.edu.udb.proyecto_catedra.model.Cliente;
import sv.edu.udb.proyecto_catedra.model.Estado;
import sv.edu.udb.proyecto_catedra.model.TipoPersona;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "ClienteController", urlPatterns = {"/clientes"})
public class ClienteController extends HttpServlet {
    private ClienteDAO clienteDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        clienteDAO = new ClienteDAOImpl();
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
                    eliminarCliente(request, response);
                    break;
                case "cambiarEstado":
                    cambiarEstadoCliente(request, response);
                    break;
                case "ver":
                    verCliente(request, response);
                    break;
                default:
                    listarClientes(request, response);
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
                agregarCliente(request, response);
            } else if ("editar".equals(action)) {
                editarCliente(request, response);
            } else {
                listarClientes(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Error al procesar la solicitud: " + e.getMessage(), e);
        }
    }

    private void listarClientes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Cliente> clientes = clienteDAO.listarClientes();
        request.setAttribute("clientes", clientes);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/cliente/listar.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioAgregar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("tipoPersonas", TipoPersona.values());
        request.setAttribute("estados", Estado.values());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/cliente/agregar.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idCliente = Integer.parseInt(request.getParameter("id"));
        Cliente cliente = clienteDAO.obtenerCliente(idCliente);

        if (cliente == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Cliente no encontrado");
            return;
        }

        request.setAttribute("cliente", cliente);
        request.setAttribute("tipoPersonas", TipoPersona.values());
        request.setAttribute("estados", Estado.values());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/cliente/editar.jsp");
        dispatcher.forward(request, response);
    }

    private void verCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idCliente = Integer.parseInt(request.getParameter("id"));
        Cliente cliente = clienteDAO.obtenerCliente(idCliente);

        if (cliente == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Cliente no encontrado");
            return;
        }

        request.setAttribute("cliente", cliente);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/cliente/ver.jsp");
        dispatcher.forward(request, response);
    }

    private void agregarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cliente cliente = new Cliente();
        mapearParametrosCliente(request, cliente);

        // Setear valores de auditoría
        cliente.setCreadoPor(obtenerUsuarioSesion(request));
        cliente.setFechaCreacion(new Date());

        clienteDAO.crearCliente(cliente);

        request.getSession().setAttribute("mensajeExito", "Cliente creado exitosamente");
        response.sendRedirect(request.getContextPath() + "/clientes");
    }

    private void editarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        Cliente clienteExistente = clienteDAO.obtenerCliente(idCliente);

        if (clienteExistente == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Cliente no encontrado");
            return;
        }

        Cliente cliente = new Cliente();
        cliente.setIdCliente(idCliente);
        mapearParametrosCliente(request, cliente);

        // Mantener valores de auditoría originales
        cliente.setCreadoPor(clienteExistente.getCreadoPor());
        cliente.setFechaCreacion(clienteExistente.getFechaCreacion());
        cliente.setFechaActualizacion(new Date());

        clienteDAO.editarCliente(cliente);

        request.getSession().setAttribute("mensajeExito", "Cliente actualizado exitosamente");
        response.sendRedirect(request.getContextPath() + "/clientes");
    }

    private void eliminarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idCliente = Integer.parseInt(request.getParameter("id"));
        Cliente cliente = clienteDAO.obtenerCliente(idCliente);

        if (cliente == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Cliente no encontrado");
            return;
        }

        clienteDAO.cambiarEstadoCliente(idCliente, Estado.INACTIVO);

        request.getSession().setAttribute("mensajeExito", "Cliente inactivado exitosamente");
        response.sendRedirect(request.getContextPath() + "/clientes");
    }

    private void cambiarEstadoCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idCliente = Integer.parseInt(request.getParameter("id"));
        String nuevoEstado = request.getParameter("estado");

        Cliente cliente = clienteDAO.obtenerCliente(idCliente);
        if (cliente == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Cliente no encontrado");
            return;
        }

        Estado estado = Estado.valueOf(nuevoEstado);
        clienteDAO.cambiarEstadoCliente(idCliente, estado);

        String mensaje = estado == Estado.ACTIVO ?
                "Cliente reactivado exitosamente" : "Cliente inactivado exitosamente";

        request.getSession().setAttribute("mensajeExito", mensaje);
        response.sendRedirect(request.getContextPath() + "/clientes");
    }

    // Método auxiliar para mapear parámetros del request a objeto Cliente
    private void mapearParametrosCliente(HttpServletRequest request, Cliente cliente) {
        cliente.setNombre(request.getParameter("nombre"));
        cliente.setDocumentoIdentificacion(request.getParameter("documentoIdentificacion"));
        cliente.setTipoPersona(TipoPersona.valueOf(request.getParameter("tipoPersona")));
        cliente.setTelefono(request.getParameter("telefono"));
        cliente.setCorreo(request.getParameter("correo"));
        cliente.setDireccion(request.getParameter("direccion"));
        cliente.setEstado(Estado.valueOf(request.getParameter("estado")));
    }

    // Método auxiliar para obtener el usuario de la sesión
    private String obtenerUsuarioSesion(HttpServletRequest request) {
        HttpSession session = request.getSession();
        // Asumiendo que el nombre de usuario está almacenado en la sesión como "username"
        return (String) session.getAttribute("username");
    }
}
