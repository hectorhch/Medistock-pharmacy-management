
import java.io.Serializable;
import java.time.LocalDate;

public class Lote implements Serializable {

    private String codigoMedicamento;
    private String numeroLote;
    private int cantidad;
    private LocalDate fechaVencimiento;

  


    public Lote(String codigoMedicamento, String numeroLote,
                int cantidad, LocalDate fechaVencimiento) {

        this.codigoMedicamento = codigoMedicamento;
        this.numeroLote = numeroLote;
        this.cantidad = cantidad;
        this.fechaVencimiento = fechaVencimiento;
    }

   


    public String getCodigoMedicamento() {
        return codigoMedicamento;
    }

    public String getNumeroLote() {
        return numeroLote;
    }

    public int getCantidad() {
        return cantidad;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

   


    public void aumentarCantidad(int cantidad) {
        if (cantidad > 0) {
            this.cantidad += cantidad;
        }
    }

    public boolean disminuirCantidad(int cantidad) {
        if (cantidad > 0 && cantidad <= this.cantidad) {
            this.cantidad -= cantidad;
            return true;
        }
        return false;
    }

   

    
    public boolean estaVencido() {
        return fechaVencimiento.isBefore(LocalDate.now());
    }

    public boolean proximoAVencer() {
        LocalDate hoy = LocalDate.now();

        return !estaVencido()
                && !fechaVencimiento.isAfter(hoy.plusDays(30));
    }
}
