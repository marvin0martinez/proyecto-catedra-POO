<%--
  Created by IntelliJ IDEA.
  User: marvi
  Date: 26/5/2025
  Time: 16:35
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
    .employee-item {
        background: linear-gradient(135deg, #a8edea, #fed6e3);
        border-radius: 15px;
        padding: 20px;
        margin-bottom: 15px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        transition: transform 0.3s;
    }
    .employee-item:hover {
        transform: translateY(-2px);
    }
    .employee-name {
        font-weight: bold;
        color: #333;
        font-size: 16px;
    }
    .btn-action {
        border-radius: 20px;
        padding: 8px 20px;
        font-size: 12px;
        margin: 0 2px;
        border: none;
    }
    .btn-edit {
        background: #ffd93d;
        color: #333;
    }
    .btn-delete {
        background: #ff6b6b;
        color: white;
    }
</style>

<div class="container-fluid">
    <div class="page-title">
        EMPLEADOS
    </div>

    <a href="empleados?action=agregar" class="btn btn-add">AGREGAR</a>

    <div class="employees-list">
        <c:forEach var="empleado" items="${empleados}">
            <div class="employee-item">
                <div class="employee-name">
                    NOMBRE DEL EMPLEADO: ${empleado.nombre}
                </div>
                <div class="actions">
                    <a href="empleados?action=ver&id=${empleado.idEmpleado}" class="btn btn-action btn-edit">VER</a>
                    <a href="empleados?action=editar&id=${empleado.idEmpleado}" class="btn btn-action btn-edit">EDITAR</a>
                    <a href="empleados?action=eliminar&id=${empleado.idEmpleado}" class="btn btn-action btn-delete" onclick="return confirm('¿Está seguro de eliminar este empleado?')">ELIMINAR</a>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<jsp:include page="/WEB-INF/views/templates/footer.jsp"/>
