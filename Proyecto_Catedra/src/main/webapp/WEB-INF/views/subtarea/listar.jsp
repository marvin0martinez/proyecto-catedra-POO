<%--
  Created by IntelliJ IDEA.
  User: marvi
  Date: 26/5/2025
  Time: 16:53
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
    .subtarea-item {
        background: linear-gradient(135deg, #a8edea, #fed6e3);
        border-radius: 15px;
        padding: 20px;
        margin-bottom: 15px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        transition: transform 0.3s;
    }
    .subtarea-item:hover {
        transform: translateY(-2px);
    }
    .subtarea-info {
        flex-grow: 1;
    }
    .subtarea-title {
        font-weight: bold;
        color: #333;
        font-size: 16px;
        margin-bottom: 5px;
    }
    .subtarea-details {
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
    .status-pendiente {
        background: #ffd93d;
        color: #333;
    }
    .status-en-progreso {
        background: #4facfe;
        color: white;
    }
    .status-completada {
        background: #51cf66;
        color: white;
    }
</style>

<div class="container-fluid">
    <div class="page-title">
        SUBTAREAS
    </div>

    <a href="subtareas?action=agregar" class="btn btn-add">AGREGAR SUBTAREA</a>

    <div class="subtareas-list">
        <c:forEach var="subtarea" items="${subtareas}">
            <div class="subtarea-item">
                <div class="subtarea-info">
                    <div class="subtarea-title">
                            ${subtarea.titulo}
                        <span class="status-badge status-${subtarea.estado.toLowerCase().replace(' ', '-')}">${subtarea.estado}</span>
                    </div>
                    <div class="subtarea-details">
                        <strong>Asignación:</strong> ${subtarea.nombreAsignacion} |
                        <strong>Empleado:</strong> ${subtarea.nombreEmpleado} |
                        <strong>Fecha límite:</strong> ${subtarea.fechaLimite}
                    </div>
                </div>
                <div class="actions">
                    <a href="subtareas?action=ver&id=${subtarea.idSubtarea}" class="btn btn-action btn-view">VER</a>
                    <a href="subtareas?action=editar&id=${subtarea.idSubtarea}" class="btn btn-action btn-edit">EDITAR</a>
                    <a href="subtareas?action=eliminar&id=${subtarea.idSubtarea}" class="btn btn-action btn-delete" onclick="return confirm('¿Está seguro de eliminar esta subtarea?')">ELIMINAR</a>
                </div>
            </div>
        </c:forEach>
    </div>

    <c:if test="${empty subtareas}">
        <div class="text-center mt-5">
            <h5 class="text-muted">No hay subtareas registradas</h5>
            <p class="text-muted">Haga clic en "Agregar Subtarea" para crear la primera subtarea</p>
        </div>
    </c:if>
</div>

<jsp:include page="/WEB-INF/views/templates/footer.jsp"/>
