package sv.edu.udb.proyecto_catedra.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    // Formatos de fecha comunes
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String ISO_DATE_FORMAT = "yyyy-MM-dd";
    public static final String ISO_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DISPLAY_DATE_FORMAT = "EEEE, d MMMM yyyy";
    public static final String DISPLAY_DATETIME_FORMAT = "EEEE, d MMMM yyyy 'a las' HH:mm";

    /**
     * Convierte un String a Date usando el formato especificado
     * @param dateStr String con la fecha
     * @param format Formato de la fecha (ej. "dd/MM/yyyy")
     * @return Objeto Date
     * @throws ParseException Si ocurre un error al parsear la fecha
     */
    public static Date stringToDate(String dateStr, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false); // Validación estricta de fechas
        return sdf.parse(dateStr);
    }

    /**
     * Convierte un Date a String usando el formato especificado
     * @param date Objeto Date
     * @param format Formato de salida (ej. "dd/MM/yyyy")
     * @return String con la fecha formateada
     */
    public static String dateToString(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * Convierte un timestamp de milisegundos a Date
     * @param milliseconds Tiempo en milisegundos desde epoch
     * @return Objeto Date
     */
    public static Date millisecondsToDate(long milliseconds) {
        return new Date(milliseconds);
    }

    /**
     * Convierte un Date a timestamp de milisegundos
     * @param date Objeto Date
     * @return Tiempo en milisegundos desde epoch
     */
    public static long dateToMilliseconds(Date date) {
        if (date == null) {
            return 0;
        }
        return date.getTime();
    }

    /**
     * Obtiene la fecha actual
     * @return Objeto Date con la fecha y hora actual
     */
    public static Date getCurrentDate() {
        return new Date();
    }

    /**
     * Obtiene la fecha actual como String en el formato especificado
     * @param format Formato de salida (ej. "dd/MM/yyyy")
     * @return String con la fecha actual formateada
     */
    public static String getCurrentDateAsString(String format) {
        return dateToString(getCurrentDate(), format);
    }

    /**
     * Valida si un String es una fecha válida según el formato especificado
     * @param dateStr String con la fecha
     * @param format Formato de la fecha (ej. "dd/MM/yyyy")
     * @return true si es válida, false si no
     */
    public static boolean isValidDate(String dateStr, String format) {
        try {
            stringToDate(dateStr, format);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Compara dos fechas ignorando la hora
     * @param date1 Primera fecha
     * @param date2 Segunda fecha
     * @return 0 si son iguales, <0 si date1 es anterior, >0 si date1 es posterior
     */
    public static int compareDates(Date date1, Date date2) {
        SimpleDateFormat sdf = new SimpleDateFormat(ISO_DATE_FORMAT);
        String strDate1 = sdf.format(date1);
        String strDate2 = sdf.format(date2);
        return strDate1.compareTo(strDate2);
    }

    /**
     * Calcula la diferencia en días entre dos fechas
     * @param startDate Fecha de inicio
     * @param endDate Fecha de fin
     * @return Diferencia en días (positiva si endDate es posterior)
     */
    public static long daysBetween(Date startDate, Date endDate) {
        long diffInMillis = endDate.getTime() - startDate.getTime();
        return diffInMillis / (1000 * 60 * 60 * 24);
    }

    /**
     * Calcula la diferencia en horas entre dos fechas
     * @param startDate Fecha de inicio
     * @param endDate Fecha de fin
     * @return Diferencia en horas (positiva si endDate es posterior)
     */
    public static long hoursBetween(Date startDate, Date endDate) {
        long diffInMillis = endDate.getTime() - startDate.getTime();
        return diffInMillis / (1000 * 60 * 60);
    }

    /**
     * Agrega días a una fecha
     * @param date Fecha base
     * @param days Número de días a agregar (puede ser negativo)
     * @return Nueva fecha con los días agregados
     */
    public static Date addDays(Date date, int days) {
        long newTime = date.getTime() + (days * 24L * 60 * 60 * 1000);
        return new Date(newTime);
    }

    /**
     * Agrega horas a una fecha
     * @param date Fecha base
     * @param hours Número de horas a agregar (puede ser negativo)
     * @return Nueva fecha con las horas agregadas
     */
    public static Date addHours(Date date, int hours) {
        long newTime = date.getTime() + (hours * 60L * 60 * 1000);
        return new Date(newTime);
    }

    /**
     * Formatea una fecha para mostrarla de manera amigable al usuario
     * @param date Fecha a formatear
     * @return String con formato "Martes, 3 de Enero de 2023"
     */
    public static String toDisplayDate(Date date) {
        return dateToString(date, DISPLAY_DATE_FORMAT);
    }

    /**
     * Formatea una fecha y hora para mostrarla de manera amigable al usuario
     * @param date Fecha a formatear
     * @return String con formato "Martes, 3 de Enero de 2023 a las 14:30"
     */
    public static String toDisplayDateTime(Date date) {
        return dateToString(date, DISPLAY_DATETIME_FORMAT);
    }

    /**
     * Convierte una fecha a formato ISO (yyyy-MM-dd)
     * @param date Fecha a convertir
     * @return String con formato ISO
     */
    public static String toIsoDate(Date date) {
        return dateToString(date, ISO_DATE_FORMAT);
    }

    /**
     * Convierte una fecha y hora a formato ISO (yyyy-MM-dd HH:mm:ss)
     * @param date Fecha a convertir
     * @return String con formato ISO
     */
    public static String toIsoDateTime(Date date) {
        return dateToString(date, ISO_DATETIME_FORMAT);
    }
}
