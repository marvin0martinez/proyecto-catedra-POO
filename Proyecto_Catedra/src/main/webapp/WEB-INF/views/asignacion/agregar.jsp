<%--
  Created by IntelliJ IDEA.
  User: marvi
  Date: 26/5/2025
  Time: 16:41
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
    .total-display {
        background: linear-gradient(135deg, #ffd93d, #ff9a9e);
        color: #333;
        font-weight: bold;
        text-align: center;
        padding: 15px;
        border-radius: 15px;
        margin: 20px 0;
    }
</style>

<div class="container-fluid">
    <div class="form-title">
        AGREGAR ASIGNACIÓN
    </div>

    <form action="asignaciones" method="post">
        <input type="hidden" name="action" value="guardar">

        <div class="row">
            <div class="col-md-6">
                <div class="mb-3">
                    <input type="text" class="form-control" name="tituloActividad" placeholder="TÍTULO DE LA ACTIVIDAD" required>
                </div>

                <div class="mb-3">
                    <select class="form-control" name="trabajadorAsignado" required>
                        <option value="">TRABAJADOR ASIGNADO</option>
                        <c:forEach var="empleado" items="${empleados}">
                            <option value="${empleado.idEmpleado}">${empleado.nombre}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-3">
                    <input type="text" class="form-control" name="areaAsignada" placeholder="ÁREA ASIGNADA" required>
                </div>

                <div class="mb-3">
                    <input type="number" step="0.01" class="form-control" name="costoPorHora" placeholder="COSTO POR HORA" required>
                </div>

                <div class="mb-3">
                    <input type="date" class="form-control" name="fechaHoraInicio" placeholder="FECHA Y HORA DE INICIO" required>
                </div>
            </div>

            <div class="col-md-6">
                <div class="mb-3">
                    <input type="date" class="form-control" name="fechaHoraFin" placeholder="FECHA Y HORA DE FIN" required>
                </div>

                <div class="mb-3">
                    <input type="number" class="form-control" name="cantidadHoras" placeholder="CANTIDAD DE HORAS" required>
                </div>

                <div class="mb-3">
                    <input type="number" step="0.01" class="form-control" name="costoBase" placeholder="COSTO BASE" required>
                </div>

                <div class="mb-3">
                    <input type="number" step="0.01" class="form-control" name="incrementoExtra" placeholder="INCREMENTO EXTRA" required>
                </div>
            </div>
        </div>

        <div class="total-display">
            <h4>TOTAL: $<span id="totalCalculado">0.00</span></h4>
        </div>

        <div class="text-center mt-4">
            <button type="submit" class="btn btn-custom btn-save">GUARDAR</button>
            <a href="asignaciones" class="btn btn-custom btn-cancel">CANCELAR</a>
        </div>
    </form>
</div>

<script>
    // Calcular total automáticamente
    document.addEventListener('DOMContentLoaded', function() {
        const cantidadHoras = document.querySelector('input[name="cantidadHoras"]');
        const costoPorHora = document.querySelector('input[name="costoPorHora"]');
        const costoBase = document.querySelector('input[name="costoBase"]');
        const incrementoExtra = document.querySelector('input[name="incrementoExtra"]');
        const totalDisplay = document.getElementById('totalCalculado');

        function calcularTotal() {
            const horas = parseFloat(cantidadHoras.value) || 0;
            const costo = parseFloat(costoPorHora.value) || 0;
            const base = parseFloat(costoBase.value) || 0;
            const extra = parseFloat(incrementoExtra.value) || 0;

            const total = (horas * costo) + base + extra;
            totalDisplay.textContent = total.toFixed(2);
        }

        cantidadHoras.addEventListener('input', calcularTotal);
        costoPorHora.addEventListener('input', calcularTotal);
        costoBase.addEventListener('input', calcularTotal);
        incrementoExtra.addEventListener('input', calcularTotal);
    });
</script>

<jsp:include page="/WEB-INF/views/templates/footer.jsp"/>
