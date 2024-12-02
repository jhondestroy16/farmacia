package org.farmacia;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FarmaciaApp {
    private final List<Pedido> historialPedidos = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FarmaciaApp().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Sistema de Pedidos - Farmacia");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));
        frame.setResizable(false);

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        frame.add(mainPanel, BorderLayout.CENTER);

        // Panel de datos
        JPanel panelDatos = new JPanel(new GridLayout(6, 2, 10, 10));
        panelDatos.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Datos del Pedido", TitledBorder.LEFT, TitledBorder.TOP));
        mainPanel.add(panelDatos);

        // Nombre del medicamento
        panelDatos.add(new JLabel("Nombre del Medicamento:"));
        JTextField txtNombreMedicamento = new JTextField();
        panelDatos.add(txtNombreMedicamento);

        // Tipo de medicamento
        panelDatos.add(new JLabel("Tipo del Medicamento:"));
        JComboBox<String> cmbTipoMedicamento = new JComboBox<>(new String[]{
                "Seleccione el tipo de medicamento", "Analgesico", "Analéptico", "Anestésico",
                "Antiácido", "Antidepresivo", "Antibiótico"});
        cmbTipoMedicamento.setSelectedIndex(0); // Mostrar "Seleccione el tipo de medicamento"
        panelDatos.add(cmbTipoMedicamento);

        // Cantidad
        panelDatos.add(new JLabel("Cantidad:"));
        JTextField txtCantidad = new JTextField();
        panelDatos.add(txtCantidad);

        // Distribuidor con radio buttons
        panelDatos.add(new JLabel("Distribuidor:"));
        JPanel distribuidorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JRadioButton rbCofarma = new JRadioButton("Cofarma");
        JRadioButton rbEmpsephar = new JRadioButton("Empsephar");
        JRadioButton rbCemefar = new JRadioButton("Cemefar");
        ButtonGroup distribuidorGroup = new ButtonGroup();
        distribuidorGroup.add(rbCofarma);
        distribuidorGroup.add(rbEmpsephar);
        distribuidorGroup.add(rbCemefar);
        distribuidorPanel.add(rbCofarma);
        distribuidorPanel.add(rbEmpsephar);
        distribuidorPanel.add(rbCemefar);
        panelDatos.add(distribuidorPanel);

        // Sucursal
        panelDatos.add(new JLabel("Sucursal:"));
        JCheckBox chkPrincipal = new JCheckBox("Principal");
        JCheckBox chkSecundaria = new JCheckBox("Secundaria");
        JPanel panelSucursales = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSucursales.add(chkPrincipal);
        panelSucursales.add(chkSecundaria);
        panelDatos.add(panelSucursales);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnBorrar = new JButton("Borrar");
        JButton btnConfirmar = new JButton("Confirmar");
        JButton btnVerHistorial = new JButton("Ver Historial");
        panelBotones.add(btnBorrar);
        panelBotones.add(btnConfirmar);
        panelBotones.add(btnVerHistorial);
        mainPanel.add(panelBotones);

        // Botón Borrar
        btnBorrar.addActionListener(e -> limpiarCampos(txtNombreMedicamento, cmbTipoMedicamento, txtCantidad,
                distribuidorGroup, chkPrincipal, chkSecundaria));

        // Botón Confirmar
        btnConfirmar.addActionListener(e -> {
            StringBuilder errores = new StringBuilder();
            String nombreMedicamento = txtNombreMedicamento.getText().trim();
            String tipoMedicamento = (String) cmbTipoMedicamento.getSelectedItem();
            String cantidadStr = txtCantidad.getText().trim();
            String distribuidor = null;

            if (rbCofarma.isSelected()) {
                distribuidor = "Cofarma";
            } else if (rbEmpsephar.isSelected()) {
                distribuidor = "Empsephar";
            } else if (rbCemefar.isSelected()) {
                distribuidor = "Cemefar";
            }

            boolean principal = chkPrincipal.isSelected();
            boolean secundaria = chkSecundaria.isSelected();

            // Validacion nombre de medicamento vacio
            if (nombreMedicamento.isEmpty() || !nombreMedicamento.matches("[a-zA-Z0-9 ]+")) {
                errores.append("- El nombre del medicamento es inválido.\n");
            }
            // Validacion Tipo de medicamento.
            if (tipoMedicamento.equals("Seleccione el tipo de medicamento")) {
                errores.append("- Debe seleccionar un tipo de medicamento.\n");
            }
            // Validacion cantidad que no sea nulo ni numero negativo
            if (cantidadStr.isEmpty() || !cantidadStr.matches("\\d+") || Integer.parseInt(cantidadStr) <= 0) {
                errores.append("- La cantidad debe ser un número entero positivo.\n");
            }
            // Validacion distribuidor vacio
            if (distribuidor == null) {
                errores.append("- Debe seleccionar un distribuidor.\n");
            }
            // Validacion sucursal vacia
            if (!principal && !secundaria) {
                errores.append("- Debe seleccionar al menos una sucursal.\n");
            }

            // Si hay errores, mostrarlos en un mensaje
            if (!errores.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Errores encontrados:\n" + errores, "Errores", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Object[] opciones = {"Confirmar", "Cancelar"};
            // Confirmación del pedido
            int confirm = JOptionPane.showOptionDialog(frame,
                    "¿Confirmar pedido?\n"
                            + "Medicamento: " + nombreMedicamento + "\n"
                            + "Tipo: " + tipoMedicamento + "\n"
                            + "Cantidad: " + cantidadStr + "\n"
                            + "Distribuidor: " + distribuidor + "\n"
                            + "Sucursal: " + (principal ? "Principal " : "") + (secundaria ? "Secundaria" : ""),
                    "Confirmar Pedido",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]); // Selección predeterminada: "Confirmar"

            if (confirm == JOptionPane.YES_OPTION) {
                historialPedidos.add(new Pedido(nombreMedicamento, tipoMedicamento, Integer.parseInt(cantidadStr), distribuidor, principal, secundaria));
                JOptionPane.showMessageDialog(frame, "Pedido confirmado.");
                System.out.println("Pedido confirmado.");
                limpiarCampos(txtNombreMedicamento, cmbTipoMedicamento, txtCantidad, distribuidorGroup, chkPrincipal, chkSecundaria);
            }
        });

        // Botón Ver Historial
        btnVerHistorial.addActionListener(e -> mostrarHistorial());

        // Final setup
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void limpiarCampos(JTextField txtNombreMedicamento, JComboBox<String> cmbTipoMedicamento,
                               JTextField txtCantidad, ButtonGroup distribuidorGroup,
                               JCheckBox chkPrincipal, JCheckBox chkSecundaria) {
        txtNombreMedicamento.setText("");
        cmbTipoMedicamento.setSelectedIndex(0); // Vuelve a "Seleccione el tipo de medicamento"
        txtCantidad.setText("");
        distribuidorGroup.clearSelection();
        chkPrincipal.setSelected(false);
        chkSecundaria.setSelected(false);
    }

    private void mostrarHistorial() {
        JFrame historialFrame = new JFrame("Historial de Pedidos");
        historialFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"Medicamento", "Tipo", "Cantidad", "Distribuidor", "Sucursal"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        for (Pedido pedido : historialPedidos) {
            tableModel.addRow(new Object[]{
                    pedido.getNombreMedicamento(),
                    pedido.getTipoMedicamento(),
                    pedido.getCantidad(),
                    pedido.getDistribuidor(),
                    (pedido.isPrincipal() ? "Principal " : "") + (pedido.isSecundaria() ? "Secundaria" : "")
            });
        }

        historialFrame.add(new JScrollPane(table));
        historialFrame.pack();
        historialFrame.setLocationRelativeTo(null);
        historialFrame.setVisible(true);
    }
}
