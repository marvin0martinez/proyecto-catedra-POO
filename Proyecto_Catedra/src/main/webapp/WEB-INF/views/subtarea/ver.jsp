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
    .detail-title {
        background: linear-gradient(135deg, #667eea, #764ba2);
        color: white;
        text-align: center;
        padding: 15px;
        border-radius: 10px;
        margin-bottom: 30px;
        font-weight: bold;
    }
    .detail-card {
        background: #f8f9fa;
        border-radius: 15px;
        padding: 20px;
        margin-bottom: 15px;
        border: 2px solid #e2e8f0;
    }
    .detail-label {
        font-weight: bold;
        color: #4a5568;
        margin-bottom: 5px;
        font-size: 14px;
    }
    .detail-value {
        background: white;
        padding: 10px 15px;
        border-radius: 20px;
        border: 1px solid #e2e8f0;
        min-height: 45px;
        display: flex;
        align-items: center;
    }
    .detail-value.large {
        min-height: 80px;
        align-items: flex-start;
        padding-top: 15px;
    }
    .btn-custom {
        border-radius: 25px;
        padding: 12px 30px;
        font-weight: bold;
        margin: 5px;
    }
    .btn-edit {
        background: #ffd93d;
        border: none;
        color: #333;
    }
    .btn-delete {
        background: #ff6b6b;
        border: none;
        color: white;
    }
    .btn-back {
        background: #4facfe;
        border: none;
        color: white;
    }
    .status-badge {
        padding: 8px 20px;
        border-radius: 25px;
        font-size: 14px;
        font-weight: bold;
        display: inline-block;
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
    .status-cancelada {
        background: #ff6b6b;
        color: white;
    }
    .priority-badge {
        padding: 5px 15px;
        border-radius: 20px;
        font-size: 12px;
        font-weight: bold;
        display: inline-block;
    }
    .priority-alta {
        background: #ff6b6b;
        color: white;
    }
    .priority-media {
        background: #ffd93d;
        color: #333;
    }
    .priority-baja {
        background: #51cf66;
        color: white;
    }
    .progress-section {
        background: linear-gradient(135deg, #a8edea, #fed6e3);
        border-radius: 15px;
        padding: 20px;
        margin: 20px 0;
    }
    .progress-bar-container {
        background: white;
        border-radius: 25px;
        height: 30px;
        overflow: hidden;
        margin-top: 10px;
    }
    .progress-bar {
        height: 100%;
        background: linear-gradient(135deg, #4facfe, #00f2fe);
        border-radius: 25px;
        display: flex;
        align-items: center;
        justify-content: center;
        color: white;
        font-weight: bold;
        transition: width 0.3s ease;
    }
</style>

<div class="container-fluid">
    <div class="detail-title">
        DETALLE DE SUBTAREA
    </div>

    <div class="row">
        <div class="col-md-6">
            <div class="detail-card">
                <div class="detail-label">ID SUBTAREA</div>
                <div class="detail-value">${subtarea.idSubtarea}</div>
            </div>

            <div class="detail-card">
                <div class="detail-label">TÍTULO</div>
                <div class="detail-value">${subtarea.titulo}</div>
            </div>

            <div class="detail-card">
                <div class="detail-label">DESCRIPCIÓN</div>
                <div class="detail-value large">${subtarea.descripcion}</div>
            </div>

            <div class="detail-card">
                <div class="detail-label">ASIGNACIÓN RELACIONADA</div>
                <div class="detail-value">${subtarea.nombreAsignacion}</div>
            </div>

            <div class="detail-card">
                <div class="detail-label">EMPLEADO RESPONSABLE</div>
                <div class="detail-value">${subtarea.nombreEmpleado}</div>
            </div>

            <div class="detail-card">
                <div class="detail-label">FECHA DE INICIO</div>
                <div class="detail-value">${subtarea.fechaInicio}</div>
            </div>
        </div>

        <div class="col-md-6">
            <div class="detail-card">
                <div class="detail-label">FECHA LÍMITE</div>
                <div class="detail-value">${subtarea.fechaLimite}</div>
            </div>

            <div class="detail-card">
                <div class="detail-label">ESTADO</div>
                <div class="detail-value">
                    <span class="status-badge status-${subtarea.estado.toLowerCase().replace(' ', '-')}">${subtarea.estado}</span>
                </div>
            </div>

            <div class="detail-card">
                <div class="detail-label">PRIORIDAD</div>
                <div class="detail-value">
                    <span class="priority-badge priority-${subtarea.prioridad.toLowerCase()}">${subtarea.prioridad}</span>
                </div>
            </div>

            <div class="detail-card">
                <div class="detail-label">HORAS ESTIMADAS</div>
                <div class="detail-value">${subtarea.horasEstimadas} horas</div>
            </div>

            <div class="detail-card">
                <div class="detail-label">HORAS REALES</div>
                <div class="detail-value">${subtarea.horasReales} horas</div>
            </div>

            <div class="detail-card">
                <div class="detail-label">OBSERVACIONES</div>
                <div class="detail-value large">
                    <c:choose>
                        <c:when test="${not empty subtarea.observaciones}">
                            ${subtarea.observaciones}
                        </c:when>
                        <c:otherwise>
                            <em class="text-muted">Sin observaciones</em>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>

    <!-- Sección de Progreso -->
    <div class="progress-section">
        <h5><strong>PROGRESO DE LA SUBTAREA</strong></h5>
        <div class="row">
            <div class="col-md-6">
                <p><strong>Tiempo utilizado:</strong> ${subtarea.horasReales} de ${subtarea.horasEstimadas} horas</p>
            </div>
            <div class="col-md-6">
                <p><strong>Porcentaje completado:</strong>
                    <c:set var="porcentaje" value="${(subtarea.horasReales / subtarea.horasEstimadas) * 100}" />
                    <c:choose>
                        <c:when test="${porcentaje > 100}">100%</c:when>
                        <c:otherwise>${porcentaje}%</c:otherwise>
                    </c:choose>
                </p>
            </div>
        </div>
        <div class="progress-bar-container">
            <div class="progress-bar" style="width: ${porcentaje > 100 ? 100 : porcentaje}%">
                ${porcentaje > 100 ? 100 : Math.round(porcentaje)}%
            </div>
        </div>
    </div>

    <div class="text-center mt-4">
        <a href="subtareas?action=editar&id=${subtarea.idSubtarea}" class="btn btn-custom btn-edit">EDITAR</a>
        <a href="subtareas?action=eliminar&id=${subtarea.idSubtarea}" class="btn btn-custom btn-delete" onclick="return confirm('¿Está seguro de eliminar esta subtarea?')">ELIMINAR</a>
        <a href="subtareas" class="btn btn-custom btn-back">VOLVER A LISTA</a>
    </div>
</div>

<jsp:include page="/WEB-INF/views/templates/footer.jsp"/>
