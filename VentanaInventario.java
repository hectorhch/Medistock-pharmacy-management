
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class VentanaInventario extends JFrame {

    private Inventario inventario;

    private JTable tabla;
    private DefaultTableModel modelo;

    private JTextField txtCodigo;
    private JTextField txtLote;
    private JTextField txtCantidad;
    private JTextField txtVencimiento;


    public VentanaInventario() {

        inventario = ArchivoInventario.cargar();

        setTitle("MEDISTOCK - Control de Inventario");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        crearInterfaz();
        actualizarTabla();
    }



    private void crearInterfaz() {

        setLayout(new BorderLayout(10, 10));

     
        JLabel titulo = new JLabel(
                "MEDISTOCK - CONTROL DE INVENTARIO",
                SwingConstants.CENTER
        );

        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(25, 75, 120));

        titulo.setBorder(
                BorderFactory.createEmptyBorder(20, 10, 20, 10)
        );

        add(titulo, BorderLayout.NORTH);

       
        JPanel panelRegistro = new JPanel(
                new GridLayout(5, 2, 8, 8)
        );

        panelRegistro.setBorder(
                BorderFactory.createTitledBorder(
                        "Registro de medicamentos"
                )
        );

        txtCodigo = new JTextField();
        txtLote = new JTextField();
        txtCantidad = new JTextField();
        txtVencimiento = new JTextField();

        panelRegistro.add(
                new JLabel("Código del medicamento:")
        );
        panelRegistro.add(txtCodigo);

        panelRegistro.add(
                new JLabel("Número de lote:")
        );
        panelRegistro.add(txtLote);

        panelRegistro.add(
                new JLabel("Cantidad:")
        );
        panelRegistro.add(txtCantidad);

        panelRegistro.add(
                new JLabel("Vencimiento (AAAA-MM-DD):")
        );
        panelRegistro.add(txtVencimiento);

        
        JButton btnEntrada = new BotonCapsula(
                "Registrar entrada", "+"
        );

        JButton btnSalida = new BotonCapsula(
                "Registrar salida", "−"
        );

        panelRegistro.add(btnEntrada);
        panelRegistro.add(btnSalida);

     


        String[] columnas = {
                "Código",
                "Lote",
                "Cantidad",
                "Vencimiento",
                "Estado"
        };

        modelo = new DefaultTableModel(columnas, 0) {

            @Override
            public boolean isCellEditable(
                    int fila, int columna) {
                return false;
            }
        };

        tabla = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabla);

        JPanel panelCentro = new JPanel(
                new BorderLayout(5, 5)
        );

        panelCentro.setBorder(
                BorderFactory.createTitledBorder(
                        "Inventario disponible"
                )
        );

        panelCentro.add(scroll, BorderLayout.CENTER);

        


        JPanel panelBotones = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 7, 5)
        );

        JButton btnStock = new BotonCapsula(
                "Stock bajo", "!"
        );

        JButton btnVencimientos = new BotonCapsula(
                "Próximos a vencer", "◷"
        );

        JButton btnVencidos = new BotonCapsula(
                "Medicamentos vencidos", "×"
        );

        JButton btnHistorial = new BotonCapsula(
                "Historial", "≡"
        );

        JButton btnActualizar = new BotonCapsula(
                "Actualizar tabla", "↻"
        );

        panelBotones.add(btnStock);
        panelBotones.add(btnVencimientos);
        panelBotones.add(btnVencidos);
        panelBotones.add(btnHistorial);
        panelBotones.add(btnActualizar);

        // Panel inferior
        JPanel panelInferior = new JPanel(
                new BorderLayout()
        );

        panelInferior.add(
                panelRegistro, BorderLayout.CENTER
        );

        panelInferior.add(
                panelBotones, BorderLayout.SOUTH
        );

        add(panelCentro, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        



        btnEntrada.addActionListener(
                e -> registrarEntrada()
        );

        btnSalida.addActionListener(
                e -> registrarSalida()
        );

        btnStock.addActionListener(
                e -> mostrarStockBajo()
        );

        btnVencimientos.addActionListener(
                e -> mostrarProximosAVencer()
        );

        btnVencidos.addActionListener(
                e -> mostrarVencidos()
        );

        btnHistorial.addActionListener(
                e -> mostrarHistorial()
        );

        btnActualizar.addActionListener(
                e -> actualizarTabla()
        );
    }

   



    private void registrarEntrada() {

        try {

            String codigo = txtCodigo.getText().trim();
            String lote = txtLote.getText().trim();

            int cantidad = Integer.parseInt(
                    txtCantidad.getText()
            );

            LocalDate vencimiento = LocalDate.parse(
                    txtVencimiento.getText()
            );

            if (codigo.isEmpty() || lote.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Complete todos los campos."
                );

                return;
            }

            boolean registrado = inventario.registrarEntrada(
                    codigo,
                    lote,
                    cantidad,
                    vencimiento
            );

            if (registrado) {

                ArchivoInventario.guardar(inventario);

                actualizarTabla();

                JOptionPane.showMessageDialog(
                        this,
                        "Entrada registrada correctamente."
                );

                limpiarCampos();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo registrar la entrada."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese una cantidad válida."
            );

        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Use el formato de fecha AAAA-MM-DD."
            );
        }
    }

    



    private void registrarSalida() {

        try {

            String codigo = txtCodigo.getText().trim();
            String lote = txtLote.getText().trim();

            int cantidad = Integer.parseInt(
                    txtCantidad.getText()
            );

            if (codigo.isEmpty() || lote.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ingrese el código y el lote."
                );

                return;
            }

            boolean registrado = inventario.registrarSalida(
                    codigo,
                    lote,
                    cantidad
            );

            if (registrado) {

                ArchivoInventario.guardar(inventario);

                actualizarTabla();

                JOptionPane.showMessageDialog(
                        this,
                        "Salida registrada correctamente."
                );

                limpiarCampos();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo realizar la salida. "
                        + "Verifique el lote, stock y vencimiento."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese una cantidad válida."
            );
        }
    }

    



    private void actualizarTabla() {

        modelo.setRowCount(0);

        ArrayList<Lote> lotes = inventario.getLotes();

        for (Lote lote : lotes) {

            String estado;

            if (lote.estaVencido()) {

                estado = "VENCIDO";

            } else if (lote.proximoAVencer()) {

                estado = "PRÓXIMO A VENCER";

            } else {

                estado = "VIGENTE";
            }

            modelo.addRow(new Object[]{

                    lote.getCodigoMedicamento(),
                    lote.getNumeroLote(),
                    lote.getCantidad(),
                    lote.getFechaVencimiento(),
                    estado
            });
        }
    }

    



    private void mostrarStockBajo() {

        ArrayList<Lote> lista =
                inventario.consultarStockBajo();

        mostrarLista(
                "MEDICAMENTOS CON STOCK BAJO",
                lista
        );
    }

    



    private void mostrarProximosAVencer() {

        ArrayList<Lote> lista =
                inventario.consultarProximosAVencer();

        mostrarLista(
                "PRÓXIMOS A VENCER",
                lista
        );
    }

    



    private void mostrarVencidos() {

        ArrayList<Lote> lista =
                inventario.consultarVencidos();

        mostrarLista(
                "MEDICAMENTOS VENCIDOS",
                lista
        );
    }





    private void mostrarLista(
            String titulo,
            ArrayList<Lote> lista) {

        if (lista.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se encontraron registros."
            );

            return;
        }

        StringBuilder texto = new StringBuilder();

        for (Lote lote : lista) {

            texto.append("Código: ")
                    .append(lote.getCodigoMedicamento())
                    .append(" | Lote: ")
                    .append(lote.getNumeroLote())
                    .append(" | Cantidad: ")
                    .append(lote.getCantidad())
                    .append(" | Vencimiento: ")
                    .append(lote.getFechaVencimiento())
                    .append("\n");
        }

        JTextArea area = new JTextArea(
                texto.toString(), 12, 55
        );

        area.setEditable(false);

        JOptionPane.showMessageDialog(
                this,
                new JScrollPane(area),
                titulo,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

 



    private void mostrarHistorial() {

        ArrayList<Movimiento> movimientos =
                inventario.consultarMovimientos();

        if (movimientos.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Todavía no hay movimientos registrados."
            );

            return;
        }

        StringBuilder texto = new StringBuilder();

        for (Movimiento movimiento : movimientos) {

            texto.append(movimiento.toString())
                    .append("\n\n");
        }

        JTextArea area = new JTextArea(
                texto.toString(), 15, 60
        );

        area.setEditable(false);

        JOptionPane.showMessageDialog(
                this,
                new JScrollPane(area),
                "HISTORIAL DE MOVIMIENTOS",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

  



    private class BotonCapsula extends JButton {

        private Color colorNormal =
                new Color(36, 99, 160);

        private Color colorHover =
                new Color(65, 135, 195);

        private Color colorPresionado =
                new Color(20, 65, 110);

        private javax.swing.Timer animacion;

        public BotonCapsula(String texto, String simbolo) {

            super(texto);

            setFont(
                    new Font("Segoe UI", Font.BOLD, 12)
            );

            setForeground(Color.WHITE);
            setBackground(colorNormal);

            setIcon(new IconoCircular(simbolo));
            setIconTextGap(9);

            setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            setHorizontalTextPosition(
                    SwingConstants.RIGHT
            );

            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);

            setBorder(
                    BorderFactory.createEmptyBorder(
                            5, 12, 5, 12
                    )
            );

            setCursor(
                    new Cursor(Cursor.HAND_CURSOR)
            );

            // Detectar movimientos del mouse
            addMouseListener(new MouseAdapter() {

                @Override
                public void mouseEntered(MouseEvent e) {

                    cambiarColor(colorHover);
                }

                @Override
                public void mouseExited(MouseEvent e) {

                    cambiarColor(colorNormal);
                }

                @Override
                public void mousePressed(MouseEvent e) {

                    cambiarColor(colorPresionado);
                }

                @Override
                public void mouseReleased(MouseEvent e) {

                    if (contains(e.getPoint())) {

                        cambiarColor(colorHover);

                    } else {

                        cambiarColor(colorNormal);
                    }
                }
            });
        }

     


        private void cambiarColor(Color destino) {

            if (animacion != null
                    && animacion.isRunning()) {

                animacion.stop();
            }

            Color inicio = getBackground();

            int[] paso = {0};

            animacion = new javax.swing.Timer(15, e -> {

                paso[0]++;

                float progreso = paso[0] / 10f;

                int rojo = (int) (
                        inicio.getRed()
                        + (destino.getRed()
                        - inicio.getRed()) * progreso
                );

                int verde = (int) (
                        inicio.getGreen()
                        + (destino.getGreen()
                        - inicio.getGreen()) * progreso
                );

                int azul = (int) (
                        inicio.getBlue()
                        + (destino.getBlue()
                        - inicio.getBlue()) * progreso
                );

                setBackground(
                        new Color(rojo, verde, azul)
                );

                if (paso[0] >= 10) {

                    ((javax.swing.Timer) e.getSource()).stop();
                }
            });

            animacion.start();
        }

       


        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(getBackground());

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    getHeight(),
                    getHeight()
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    


    private class IconoCircular implements Icon {

        private String simbolo;

        public IconoCircular(String simbolo) {

            this.simbolo = simbolo;
        }

        @Override
        public int getIconWidth() {

            return 28;
        }

        @Override
        public int getIconHeight() {

            return 28;
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            // Círculo blanco
            g2.setColor(Color.WHITE);

            g2.fillOval(
                    x,
                    y,
                    28,
                    28
            );

            // Símbolo azul
            g2.setColor(
                    new Color(36, 99, 160)
            );

            g2.setFont(
                    new Font("Segoe UI", Font.BOLD, 17)
            );

            FontMetrics fm = g2.getFontMetrics();

            int ancho = fm.stringWidth(simbolo);
            int alto = fm.getAscent();

            g2.drawString(
                    simbolo,
                    x + (28 - ancho) / 2,
                    y + (28 - fm.getHeight()) / 2 + alto
            );

            g2.dispose();
        }
    }

    



    private void limpiarCampos() {

        txtCodigo.setText("");
        txtLote.setText("");
        txtCantidad.setText("");
        txtVencimiento.setText("");
    }

    

    

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            VentanaInventario ventana =
                    new VentanaInventario();

            ventana.setVisible(true);
        });
    }
}
