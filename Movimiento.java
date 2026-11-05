
import java.io.Serializable;
import java.time.LocalDateTime;

public class Movimiento implements Serializable {

    private String codigoMedicamento;
    private String numeroLote;
    private String tipo;
    private int cantidad;
    private LocalDateTime fecha;
    private String descripcion;

 


    public Movimiento(String codigoMedicamento,
                      String numeroLote,
                      String tipo,
                      int cantidad,
                      String descripcion) {

        this.codigoMedicamento = codigoMedicamento;
        this.numeroLote = numeroLote;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.fecha = LocalDateTime.now();
    }

   


    public String getCodigoMedicamento() {
        return codigoMedicamento;
    }

  


    public String getNumeroLote() {
        return numeroLote;
    }




    public String getTipo() {
        return tipo;
    }

  

    public int getCantidad() {
        return cantidad;
    }

    

    public LocalDateTime getFecha() {
        return fecha;
    }



    public String getDescripcion() {
        return descripcion;
    }



    
    @Override
    public String toString() {
        return "Medicamento: " + codigoMedicamento
                + " | Lote: " + numeroLote
                + " | Tipo: " + tipo
                + " | Cantidad: " + cantidad
                + " | Fecha: " + fecha
                + " | Descripcion: " + descripcion;
    }
}
