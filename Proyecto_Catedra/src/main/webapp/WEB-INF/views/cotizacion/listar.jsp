<%--
  Created by IntelliJ IDEA.
  User: marvi
  Date: 26/5/2025
  Time: 16:39
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
    .cotizacion-item {
        background: linear-gradient(135deg, #a8edea, #fed6e3);
        border-radius: 15px;
        padding: 20px;
        margin-bottom: 15px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        transition: transform 0.3s;
    }
    .cotizacion-item:hover {
        transform: translateY(-2px);
    }
    .cotizacion-name {
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
        COTIZACIONES
    </div>

    <a href="cotizaciones?action=agregar" class="btn btn-add">AGREGAR</a>

    <div class="cotizaciones-list">
        <c:forEach var="cotizacion" items="${cotizaciones}">
            <div class="cotizacion-item">
                <div class="cotizacion-name">
                    NOMBRE DEL COTIZANTE: ${cotizacion.nombreCotizante}
                </div>
                <div class="actions">
                    <a href="cotizaciones?action=ver&id=${cotizacion.idCotizacion}" class="btn btn-action btn-edit">VER</a>
                    <a href="cotizaciones?action=editar&id=${cotizacion.idCotizacion}" class="btn btn-action btn-edit">EDITAR</a>
                    <a href="cotizaciones?action=eliminar&id=${cotizacion.idCotizacion}" class="btn btn-action btn-delete" onclick="return confirm('¿Está seguro de eliminar esta cotización?')">ELIMINAR</a>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<jsp:include page="/WEB-INF/views/templates/footer.jsp"/>
