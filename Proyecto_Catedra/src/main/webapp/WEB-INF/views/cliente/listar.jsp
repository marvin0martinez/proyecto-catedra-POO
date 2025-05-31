<%--
  Created by IntelliJ IDEA.
  User: MINEDUCYT
  Date: 10/05/2025
  Time: 12:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/templates/header.jsp"/>
<jsp:include page="/WEB-INF/views/templates/menu.jsp"/>

<style>
    .page-title {
        background: linear-gradient(135deg, #667eea, #764ba2);
        color: white;
        text-align: center;
        padding: 15px;
        border-radius: 10px;
        margin-bottom: 30px;
        font-weight: bold;
    }
    .btn-add {
        background: #4facfe;
        border: none;
        color: white;
        border-radius: 25px;
        padding: 12px 30px;
        font-weight: bold;
        margin-bottom: 20px;
    }
    .cliente-item {
        background: linear-gradient(135deg, #a8edea, #fed6e3);
        border-radius: 15px;
        padding: 20px;
        margin-bottom: 15px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        transition: transform 0.3s;
    }
    .cliente-item:hover {
        transform: translateY(-2px);
    }
    .cliente-info {
        flex-grow: 1;
    }
    .cliente-name {
        font-weight: bold;
        color: #333;
        font-size: 16px;
        margin-bottom: 5px;
    }
    .cliente-details {
        color: #666;
        font-size: 14px;
    }
    .btn-action {
        border-radius: 20px;
        padding: 8px 20px;
        font-size: 12px;
        margin: 0 2px;
        border: none;
    }
    .btn-view {
        background: #4facfe;
        color: white;
    }
    .btn-edit {
        background: #ffd93d;
        color: #333;
    }
    .btn-delete {
        background: #ff6b6b;
        color: white;
    }
    .status-badge {
        padding: 5px 15px;
        border-radius: 20px;
        font-size: 12px;
        font-weight: bold;
        margin-left: 10px;
    }
    .status-activo {
        background: #51cf66;
        color: white;
    }
    .status-inactivo {
        background: #ff6b6b;
        color: white;
    }
</style>

<div class="container-fluid">
    <div class="page-title">
        CLIENTES
    </div>

    <a href="clientes?action=agregar" class="btn btn-add">AGREGAR CLIENTE</a>

    <div class="clientes-list">
        <c:forEach var="cliente" items="${clientes}">
            <div class="cliente-item">
                <div class="cliente-info">
                    <div class="cliente-name">
                            ${cliente.nombre}
                        <span class="status-badge status-${cliente.estado.toLowerCase()}">${cliente.estado}</span>
                    </div>
                    <div class="cliente-details">
                        <strong>Tipo:</strong> ${cliente.tipoPersona} |
                        <strong>Email:</strong> ${cliente.correo} |
                        <strong>Teléfono:</strong> ${cliente.telefono}
                    </div>
                </div>
                <div class="actions">
                    <a href="clientes?action=ver&id=${cliente.idCliente}" class="btn btn-action btn-view">VER</a>
                    <a href="clientes?action=editar&id=${cliente.idCliente}" class="btn btn-action btn-edit">EDITAR</a>
                    <a href="clientes?action=eliminar&id=${cliente.idCliente}" class="btn btn-action btn-delete" onclick="return confirm('¿Está seguro de eliminar este cliente?')">ELIMINAR</a>
                </div>
            </div>
        </c:forEach>
    </div>

    <c:if test="${empty clientes}">
        <div class="text-center mt-5">
            <h5 class="text-muted">No hay clientes registrados</h5>
            <p class="text-muted">Haga clic en "Agregar Cliente" para crear el primer cliente</p>
        </div>
    </c:if>
</div>

<jsp:include page="/WEB-INF/views/templates/footer.jsp"/>
