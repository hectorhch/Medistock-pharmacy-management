
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Inventario implements Serializable {

    private ArrayList<Lote> lotes;
    private ArrayList<Movimiento> movimientos;

    private int stockMinimo = 10;



    
    public Inventario() {
        lotes = new ArrayList<>();
        movimientos = new ArrayList<>();
    }


    public boolean registrarEntrada(String codigoMedicamento,
                                    String numeroLote,
                                    int cantidad,
                                    LocalDate fechaVencimiento) {

        if (cantidad <= 0 || fechaVencimiento == null) {
            return false;
        }

        if (fechaVencimiento.isBefore(LocalDate.now())) {
            return false;
        }

        Lote lote = buscarLote(codigoMedicamento, numeroLote);

        if (lote != null) {

            if (!lote.getFechaVencimiento().equals(fechaVencimiento)) {
                return false;
            }

            lote.aumentarCantidad(cantidad);

        } else {

            lote = new Lote(
                codigoMedicamento,
                numeroLote,
                cantidad,
                fechaVencimiento
            );

            lotes.add(lote);
        }

        movimientos.add(new Movimiento(
            codigoMedicamento,
            numeroLote,
            "ENTRADA",
            cantidad,
            "Ingreso de medicamentos"
        ));

        return true;
    }

    


    public boolean registrarSalida(String codigoMedicamento,
                                   String numeroLote,
                                   int cantidad) {

        if (cantidad <= 0) {
            return false;
        }

        Lote lote = buscarLote(codigoMedicamento, numeroLote);

        if (lote == null || lote.estaVencido()) {
            return false;
        }

        if (lote.disminuirCantidad(cantidad)) {

            movimientos.add(new Movimiento(
                codigoMedicamento,
                numeroLote,
                "SALIDA",
                cantidad,
                "Salida de medicamentos"
            ));

            return true;
        }

        return false;
    }

  


    public Lote buscarLote(String codigoMedicamento,
                           String numeroLote) {

        for (Lote lote : lotes) {

            if (lote.getCodigoMedicamento().equalsIgnoreCase(codigoMedicamento)
                && lote.getNumeroLote().equalsIgnoreCase(numeroLote)) {

                return lote;
            }
        }

        return null;
    }

    


    public int consultarStock(String codigoMedicamento) {

        int total = 0;

        for (Lote lote : lotes) {

            if (lote.getCodigoMedicamento().equalsIgnoreCase(codigoMedicamento)
                && !lote.estaVencido()) {

                total += lote.getCantidad();
            }
        }

        return total;
    }

    


    public ArrayList<Lote> consultarStockBajo() {

        ArrayList<Lote> resultado = new ArrayList<>();

        for (Lote lote : lotes) {

            if (!lote.estaVencido()
                && lote.getCantidad() <= stockMinimo) {

                resultado.add(lote);
            }
        }

        return resultado;
    }

   


    public ArrayList<Lote> consultarProximosAVencer() {

        ArrayList<Lote> resultado = new ArrayList<>();

        for (Lote lote : lotes) {

            if (lote.getCantidad() > 0 && lote.proximoAVencer()) {
                resultado.add(lote);
            }
        }

        return resultado;
    }

   



    public ArrayList<Lote> consultarVencidos() {

        ArrayList<Lote> resultado = new ArrayList<>();

        for (Lote lote : lotes) {

            if (lote.estaVencido() && lote.getCantidad() > 0) {
                resultado.add(lote);
            }
        }

        return resultado;
    }

 


    public ArrayList<Movimiento> consultarMovimientos() {
        return new ArrayList<>(movimientos);
    }

   


    public ArrayList<Lote> getLotes() {
        return new ArrayList<>(lotes);
    }

   

    
    public void setStockMinimo(int stockMinimo) {
        if (stockMinimo >= 0) {
            this.stockMinimo = stockMinimo;
        }
    }

    public int getStockMinimo() {
        return stockMinimo;
    }
}
