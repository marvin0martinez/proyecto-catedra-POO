package sv.edu.udb.proyecto_catedra.model;

import java.util.Date;
import java.util.List;

public class Asignacion {
    private int idAsignacion;
    private int idCotizacion;
    private int idEmpleado;
    private String tituloActividad;
    private List<String> listaActividades;
    private Date fechaInicio;
    private Date fechaFin;
    private float duracionHoras;
    private float costoPorHora;
    private List<Subtarea> subtareas;
    private float costoBase;

    public int getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(int idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    public int getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(int idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getTituloActividad() {
        return tituloActividad;
    }

    public void setTituloActividad(String tituloActividad) {
        this.tituloActividad = tituloActividad;
    }

    public List<String> getListaActividades() {
        return listaActividades;
    }

    public void setListaActividades(List<String> listaActividades) {
        this.listaActividades = listaActividades;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public float getDuracionHoras() {
        return duracionHoras;
    }

    public void setDuracionHoras(float duracionHoras) {
        this.duracionHoras = duracionHoras;
    }

    public float getCostoPorHora() {
        return costoPorHora;
    }

    public void setCostoPorHora(float costoPorHora) {
        this.costoPorHora = costoPorHora;
    }

    public List<Subtarea> getSubtareas() {
        return subtareas;
    }

    public void setSubtareas(List<Subtarea> subtareas) {
        this.subtareas = subtareas;
    }

    public float getCostoBase() {
        return costoBase;
    }

    public void setCostoBase(float costoBase) {
        this.costoBase = costoBase;
    }
}
