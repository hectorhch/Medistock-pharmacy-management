
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class ArchivoInventario {

    private static final String RUTA = "inventario.dat";


    
    public static void guardar(Inventario inventario) {

        try (ObjectOutputStream salida =
                new ObjectOutputStream(
                    new FileOutputStream(RUTA))) {

            salida.writeObject(inventario);

            System.out.println("Inventario guardado correctamente.");

        } catch (IOException e) {

            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

   

    public static Inventario cargar() {

        try (ObjectInputStream entrada =
                new ObjectInputStream(
                    new FileInputStream(RUTA))) {

            Inventario inventario =
                    (Inventario) entrada.readObject();

            System.out.println("Inventario cargado correctamente.");

            return inventario;

        } catch (java.io.FileNotFoundException e) {

            System.out.println("No existe un inventario anterior.");
            return new Inventario();

        } catch (IOException | ClassNotFoundException e) {

            System.out.println("Error al cargar: " + e.getMessage());
            return new Inventario();
        }
    }
}

