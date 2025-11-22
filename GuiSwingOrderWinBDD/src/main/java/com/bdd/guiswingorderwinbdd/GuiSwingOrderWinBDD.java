package com.bdd.guiswingorderwinbdd;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;
import javax.swing.*;

/**
 * @author shiny
 * @version 1.0
 */
public class GuiSwingOrderWinBDD extends javax.swing.JFrame {

    public GuiSwingOrderWinBDD() {
        initComponents();
        configurarVentana(); // Carga icono y menús
    }

    /**
     * Configura el Icono de la ventana y la Barra de Menú.
     */
    private void configurarVentana() {
        // 1. Cargar Icono
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/img_BDD/icon-BDD.png"));
            setIconImage(icon.getImage());
        } catch (Exception e) {
            System.err.println("Aviso: No se pudo cargar el icono de la ventana.");
        }

        // 2. Barra de Menú
        JMenuBar menuBar = new JMenuBar();

        // --- Menú App ---
        JMenu menuApp = new JMenu("App");
        menuApp.setMnemonic(KeyEvent.VK_A);

        JMenuItem itemPedido = new JMenuItem("Ver Pedido Completo");
        itemPedido.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
        itemPedido.addActionListener(e -> btnVerPedidoActionPerformed(null));

        JMenuItem itemCalc = new JMenuItem("Calculadora");
        itemCalc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        itemCalc.addActionListener(e -> btnCalculadoraActionPerformed(null));

        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        itemSalir.addActionListener(e -> btnSalirActionPerformed(null));

        menuApp.add(itemPedido);
        menuApp.add(itemCalc);
        menuApp.addSeparator();
        menuApp.add(itemSalir);

        // --- Menú Ayuda ---
        JMenu menuAyuda = new JMenu("Ayuda");
        menuAyuda.setMnemonic(KeyEvent.VK_H);

        JMenuItem itemInfo = new JMenuItem("Información");
        itemInfo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));
        itemInfo.addActionListener(e -> btnInfoActionPerformed(null));

        JMenuItem itemCompania = new JMenuItem("Compañía");
        itemCompania.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));
        itemCompania.addActionListener(e -> {
            dlgLogoBDD dialog = new dlgLogoBDD(this, true);
            dialog.setVisible(true);
        });

        JMenuItem itemAcerca = new JMenuItem("Acerca de");
        itemAcerca.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
        itemAcerca.addActionListener(e -> btnAcercaDeActionPerformed(null));

        menuAyuda.add(itemInfo);
        menuAyuda.add(itemCompania);
        menuAyuda.add(itemAcerca);

        menuBar.add(menuApp);
        menuBar.add(menuAyuda);
        setJMenuBar(menuBar);
    }

    // --- Lógica de Botones y Eventos ---

    private void btnActualizarFechaActionPerformed(java.awt.event.ActionEvent evt) {
        lblFechaHora.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
    }

    private void radioRestauranteActionPerformed(java.awt.event.ActionEvent evt) {
        txtNumeroMesa.setEnabled(true);
        txtNumeroMesa.setText("");
    }

    private void radioParaLlevarActionPerformed(java.awt.event.ActionEvent evt) {
        txtNumeroMesa.setEnabled(false);
        txtNumeroMesa.setText("0");
    }

    private void btnComprobarEmailActionPerformed(java.awt.event.ActionEvent evt) {
        String email = txtEmail.getText().trim();
        if (email.isEmpty()) {
            mostrarMensaje("El campo email está vacío.", JOptionPane.INFORMATION_MESSAGE);
        } else if (esEmailValido(email)) {
            mostrarMensaje("Email válido.", JOptionPane.INFORMATION_MESSAGE);
        } else {
            mostrarMensaje("Email incorrecto.", JOptionPane.ERROR_MESSAGE);
            txtEmail.setText("");
        }
    }

    private void btnVerPedidoActionPerformed(java.awt.event.ActionEvent evt) {
        if (!validarFormulario()) return;

        StringBuilder resumen = new StringBuilder();
        resumen.append("<html><b>RESUMEN DEL PEDIDO</b><br><br>");
        resumen.append("<b>Cliente: </b>").append(txtNombreCliente.getText().trim()).append("<br>");
        resumen.append("<b>Email: </b>").append(txtEmail.getText().trim()).append("<br>");
        resumen.append("<b>Total: </b>").append(txtTotal.getText().trim()).append("<br>");

        if (radioRestaurante.isSelected()) {
            resumen.append("<b>Tipo: </b> En restaurante<br>");
            resumen.append("<b>Mesa: </b> ").append(txtNumeroMesa.getText().trim()).append("<br>");
        } else {
            resumen.append("<b>Tipo: </b> Para llevar<br>");
        }

        resumen.append("<b>Pago: </b>").append(radioEfectivo.isSelected() ? "Efectivo" : "Tarjeta").append("<br>");
        resumen.append("<b>Propina: </b>").append(comboPropina.getSelectedItem()).append("<br>");
        resumen.append("<b>Factura: </b>").append(checkFactura.isSelected() ? "Sí" : "No").append("<br>");
        
        resumen.append("<br><b>Comentarios: </b><br>").append(txtComentarios.getText().replace("\n", "<br>"));
        resumen.append("</html>");

        mostrarMensaje(resumen.toString(), JOptionPane.INFORMATION_MESSAGE);
    }

    private void btnCalculadoraActionPerformed(java.awt.event.ActionEvent evt) {
        String os = System.getProperty("os.name").toLowerCase();
        String cmd = os.contains("win") ? "calc" : (os.contains("linux") ? "mate-calculator" : "");

        if (!cmd.isEmpty()) {
            try {
                Runtime.getRuntime().exec(cmd);
            } catch (Exception e) {
                mostrarMensaje("Error al abrir calculadora.", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            mostrarMensaje("Calculadora no soportada en este sistema.", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void btnInfoActionPerformed(java.awt.event.ActionEvent evt) {
        String info = "<html><b>Bienvenido</b><br>"
                + "<b>Sistema:</b> " + System.getProperty("os.name") + "<br>"
                + "<b>Usuario:</b> " + System.getProperty("user.name") + "</html>";
        mostrarMensaje(info, JOptionPane.INFORMATION_MESSAGE);
    }

    private void btnAcercaDeActionPerformed(java.awt.event.ActionEvent evt) {
        mostrarMensaje("<html><b>Desarrollador:</b> Blagovest Doukov Dimitrova<br><b>App:</b> TakeOrderApp v1.0</html>", JOptionPane.INFORMATION_MESSAGE);
    }

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {
        if (JOptionPane.showConfirmDialog(this, "¿Seguro que quieres salir?", "Salir", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            mostrarMensaje("¡Hasta pronto!", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    // --- Helpers y Validaciones ---

    private void mostrarMensaje(String msg, int tipo) {
        JOptionPane.showMessageDialog(this, msg, "Información", tipo);
    }

    private boolean esEmailValido(String email) {
        return email != null && Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$").matcher(email).matches();
    }

    private boolean validarFormulario() {
        if (txtNombreCliente.getText().trim().isEmpty()) {
            mostrarMensaje("Nombre obligatorio.", JOptionPane.ERROR_MESSAGE);
            txtNombreCliente.requestFocus();
            return false;
        }
        if (buttonGroup1.getSelection() == null) {
            mostrarMensaje("Selecciona dónde comer.", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (radioRestaurante.isSelected()) {
            try {
                if (Integer.parseInt(txtNumeroMesa.getText().trim()) <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                mostrarMensaje("Número de mesa inválido.", JOptionPane.ERROR_MESSAGE);
                txtNumeroMesa.requestFocus();
                return false;
            }
        }
        if (txtTotal.getText().trim().isEmpty()) {
            mostrarMensaje("Total obligatorio.", JOptionPane.ERROR_MESSAGE);
            txtTotal.requestFocus();
            return false;
        }
        if (buttonGroup2.getSelection() == null) {
            mostrarMensaje("Selecciona método de pago.", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // --- Código Generado por NetBeans (Eventos vacíos obligatorios) ---
    
    private void txtNumeroMesaActionPerformed(java.awt.event.ActionEvent evt) {}
    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {}
    private void comboPropinaActionPerformed(java.awt.event.ActionEvent evt) {}
    private void checkFacturaActionPerformed(java.awt.event.ActionEvent evt) {}

    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        java.awt.EventQueue.invokeLater(() -> new GuiSwingOrderWinBDD().setVisible(true));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        lblFechaHora = new javax.swing.JLabel();
        btnActualizarFecha = new javax.swing.JButton();
        radioRestaurante = new javax.swing.JRadioButton();
        radioParaLlevar = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        txtNumeroMesa = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNombreCliente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        btnComprobarEmail = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtComentarios = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        radioTarjeta = new javax.swing.JRadioButton();
        radioEfectivo = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        comboPropina = new javax.swing.JComboBox<>();
        checkFactura = new javax.swing.JCheckBox();
        btnInfo = new javax.swing.JButton();
        btnVerPedido = new javax.swing.JButton();
        btnAcercaDe = new javax.swing.JButton();
        btnCalculadora = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TakeOrderAp v.1 por Blagovest Doukov Dimitrova");
        setPreferredSize(new java.awt.Dimension(563, 663));
        setResizable(false);

        jLabel1.setText("Fecha y Hora:");

        lblFechaHora.setText("\" \"");

        btnActualizarFecha.setText("Actualizar");
        btnActualizarFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarFechaActionPerformed(evt);
            }
        });

        buttonGroup1.add(radioRestaurante);
        radioRestaurante.setText("Para comer aqui");
        radioRestaurante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioRestauranteActionPerformed(evt);
            }
        });

        buttonGroup1.add(radioParaLlevar);
        radioParaLlevar.setText("Para llevar");
        radioParaLlevar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioParaLlevarActionPerformed(evt);
            }
        });

        jLabel2.setText("Número de mesa:");

        txtNumeroMesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroMesaActionPerformed(evt);
            }
        });

        jLabel3.setText("Nombre Cliente: ");

        jLabel4.setText("Email:");

        btnComprobarEmail.setText("Comprobar Email");
        btnComprobarEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprobarEmailActionPerformed(evt);
            }
        });

        jLabel5.setText("Comentarios:");

        txtComentarios.setColumns(20);
        txtComentarios.setRows(5);
        jScrollPane2.setViewportView(txtComentarios);

        jLabel6.setText("Total: ");

        txtTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalActionPerformed(evt);
            }
        });

        buttonGroup2.add(radioTarjeta);
        radioTarjeta.setText("Tarjeta");

        buttonGroup2.add(radioEfectivo);
        radioEfectivo.setText("Efectivo");

        jLabel7.setText("Propina:");

        comboPropina.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sin Propina :(", "5%", "10%", "15%", "20%" }));
        comboPropina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPropinaActionPerformed(evt);
            }
        });

        checkFactura.setText("Solicitar Factura");
        checkFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkFacturaActionPerformed(evt);
            }
        });

        btnInfo.setText("Informacion");
        btnInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInfoActionPerformed(evt);
            }
        });

        btnVerPedido.setText("Ver Pedido");
        btnVerPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerPedidoActionPerformed(evt);
            }
        });

        btnAcercaDe.setText("Acerca de");
        btnAcercaDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcercaDeActionPerformed(evt);
            }
        });

        btnCalculadora.setText("Calculadora");
        btnCalculadora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalculadoraActionPerformed(evt);
            }
        });

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Savate", 1, 16)); // NOI18N
        jLabel8.setText("GUI SWING ORDER WIN BY BLAGO");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnInfo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVerPedido)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAcercaDe)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCalculadora)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(btnSalir))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblFechaHora))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnActualizarFecha)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel8))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnComprobarEmail)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtNombreCliente)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtNumeroMesa, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(radioRestaurante)
                                    .addComponent(radioParaLlevar)
                                    .addComponent(jLabel5)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(radioEfectivo)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(radioTarjeta)
                                        .addGap(62, 62, 62)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(checkFactura)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(comboPropina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(75, 75, 75)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblFechaHora))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnActualizarFecha)
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnComprobarEmail)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radioRestaurante)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radioParaLlevar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNumeroMesa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(radioEfectivo)
                            .addComponent(jLabel7)
                            .addComponent(comboPropina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(radioTarjeta)
                            .addComponent(checkFactura))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnInfo)
                            .addComponent(btnVerPedido)
                            .addComponent(btnAcercaDe)
                            .addComponent(btnCalculadora)
                            .addComponent(btnSalir))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel8)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAcercaDe;
    private javax.swing.JButton btnActualizarFecha;
    private javax.swing.JButton btnCalculadora;
    private javax.swing.JButton btnComprobarEmail;
    private javax.swing.JButton btnInfo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnVerPedido;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JCheckBox checkFactura;
    private javax.swing.JComboBox<String> comboPropina;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblFechaHora;
    private javax.swing.JRadioButton radioEfectivo;
    private javax.swing.JRadioButton radioParaLlevar;
    private javax.swing.JRadioButton radioRestaurante;
    private javax.swing.JRadioButton radioTarjeta;
    private javax.swing.JTextArea txtComentarios;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNumeroMesa;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}