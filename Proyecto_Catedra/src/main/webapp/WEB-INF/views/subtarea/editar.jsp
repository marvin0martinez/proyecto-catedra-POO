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
    .form-title {
        background: linear-gradient(135deg, #667eea, #764ba2);
        color: white;
        text-align: center;
        padding: 15px;
        border-radius: 10px;
        margin-bottom: 30px;
        font-weight: bold;
    }
    .form-control {
        border-radius: 25px;
        padding: 12px 20px;
        border: 2px solid #e2e8f0;
        margin-bottom: 15px;
    }
    .form-control:focus {
        border-color: #667eea;
        box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
    }
    .form-control.textarea {
        border-radius: 15px;
        min-height: 100px;
        resize: vertical;
    }
    .btn-custom {
        border-radius: 25px;
        padding: 12px 30px;
        font-weight: bold;
        margin: 5px;
    }
    .btn-save {
        background: #4facfe;
        border: none;
        color: white;
    }
    .btn-cancel {
        background: #ff6b6b;
        border: none;
        color: white;
    }
    .priority-selector {
        display: flex;
        gap: 10px;
        margin-bottom: 15px;
    }
    .priority-option {
        flex: 1;
        padding: 10px;
        border: 2px solid #e2e8f0;
        border-radius: 20px;
        text-align: center;
        cursor: pointer;
        transition: all 0.3s;
    }
    .priority-option.selected {
        border-color: #667eea;
        background: #667eea;
        color: white;
    }
    .priority-alta { border-color: #ff6b6b; }
    .priority-alta.selected { background: #ff6b6b; }
    .priority-media { border-color: #ffd93d; }
    .priority-media.selected { background: #ffd93d; color: #333; }
    .priority-baja { border-color: #51cf66; }
    .priority-baja.selected { background: #51cf66; }
</style>

<div class="container-fluid">
    <div class="form-title">
        EDITAR SUBTAREA
    </div>

    <form action="subtareas" method="post">
        <input type="hidden" name="action" value="actualizar">
        <input type="hidden" name="id" value="${subtarea.idSubtarea}">
        <input type="hidden" name="prioridad" id="prioridadInput" value="${subtarea.prioridad}">

        <div class="row">
            <div class="col-md-6">
                <div class="mb-3">
                    <input type="text" class="form-control" name="id" value="${subtarea.idSubtarea}" placeholder="ID" readonly>
                </div>

                <div class="mb-3">
                    <input type="text" class="form-control" name="titulo" value="${subtarea.titulo}" placeholder="TÍTULO DE LA SUBTAREA" required>
                </div>

                <div class="mb-3">
                    <textarea class="form-control textarea" name="descripcion" placeholder="DESCRIPCIÓN DE LA SUBTAREA" required>${subtarea.descripcion}</textarea>
                </div>

                <div class="mb-3">
                    <select class="form-control" name="asignacion" required>
                        <option value="">SELECCIONAR ASIGNACIÓN</option>
                        <c:forEach var="asignacion" items="${asignaciones}">
                            <option value="${asignacion.idAsignacion}" ${asignacion.idAsignacion == subtarea.idAsignacion ? 'selected' : ''}>${asignacion.tituloActividad}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-3">
                    <select class="form-control" name="empleado" required>
                        <option value="">EMPLEADO RESPONSABLE</option>
                        <c:forEach var="empleado" items="${empleados}">
                            <option value="${empleado.idEmpleado}" ${empleado.idEmpleado == subtarea.idEmpleado ? 'selected' : ''}>${empleado.nombre}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-3">
                    <input type="date" class="form-control" name="fechaInicio" value="${subtarea.fechaInicio}" placeholder="FECHA DE INICIO" required>
                </div>
            </div>

            <div class="col-md-6">
                <div class="mb-3">
                    <input type="date" class="form-control" name="fechaLimite" value="${subtarea.fechaLimite}" placeholder="FECHA LÍMITE" required>
                </div>

                <div class="mb-3">
                    <select class="form-control" name="estado" required>
                        <option value="">ESTADO</option>
                        <option value="Pendiente" ${subtarea.estado == 'Pendiente' ? 'selected' : ''}>Pendiente</option>
                        <option value="En Progreso" ${subtarea.estado == 'En Progreso' ? 'selected' : ''}>En Progreso</option>
                        <option value="Completada" ${subtarea.estado == 'Completada' ? 'selected' : ''}>Completada</option>
                        <option value="Cancelada" ${subtarea.estado == 'Cancelada' ? 'selected' : ''}>Cancelada</option>
                    </select>
                </div>

                <div class="mb-3">
                    <label class="form-label"><strong>PRIORIDAD</strong></label>
                    <div class="priority-selector">
                        <div class="priority-option priority-alta ${subtarea.prioridad == 'Alta' ? 'selected' : ''}" data-priority="Alta">
                            ALTA
                        </div>
                        <div class="priority-option priority-media ${subtarea.prioridad == 'Media' ? 'selected' : ''}" data-priority="Media">
                            MEDIA
                        </div>
                        <div class="priority-option priority-baja ${subtarea.prioridad == 'Baja' ? 'selected' : ''}" data-priority="Baja">
                            BAJA
                        </div>
                    </div>
                </div>

                <div class="mb-3">
                    <input type="number" step="0.01" class="form-control" name="horasEstimadas" value="${subtarea.horasEstimadas}" placeholder="HORAS ESTIMADAS" required>
                </div>

                <div class="mb-3">
                    <input type="number" step="0.01" class="form-control" name="horasReales" value="${subtarea.horasReales}" placeholder="HORAS REALES">
                </div>

                <div class="mb-3">
                    <textarea class="form-control" name="observaciones" placeholder="OBSERVACIONES (OPCIONAL)" rows="3">${subtarea.observaciones}</textarea>
                </div>
            </div>
        </div>

        <div class="text-center mt-4">
            <button type="submit" class="btn btn-custom btn-save">ACTUALIZAR SUBTAREA</button>
            <a href="subtareas" class="btn btn-custom btn-cancel">CANCELAR</a>
        </div>
    </form>
</div>

<script>
    // Manejo de selección de prioridad
    document.addEventListener('DOMContentLoaded', function() {
        const priorityOptions = document.querySelectorAll('.priority-option');
        const priorityInput = document.getElementById('prioridadInput');

        priorityOptions.forEach(option => {
            option.addEventListener('click', function() {
                // Remover selección anterior
                priorityOptions.forEach(opt => opt.classList.remove('selected'));

                // Agregar selección actual
                this.classList.add('selected');

                // Actualizar input hidden
                priorityInput.value = this.dataset.priority;
            });
        });
    });
</script>

<jsp:include page="/WEB-INF/views/templates/footer.jsp"/>
