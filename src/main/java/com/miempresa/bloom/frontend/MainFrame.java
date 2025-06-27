package com.miempresa.bloom.frontend;

import com.miempresa.bloom.backend.TestManager;
import com.miempresa.bloom.backend.model.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

public class MainFrame extends JFrame {
    private final JLabel lblInfo = new JLabel("Carga un archivo para comenzar");
    private final JButton btnSelectFile = new JButton("Seleccionar archivo");
    private final JButton btnStartTest = new JButton("Iniciar prueba");
    private final TestManager testManager = new TestManager();

    public MainFrame() {
        super("Aplicación de Pruebas Bloom");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelInfo = new JPanel();
        panelInfo.add(lblInfo);
        add(panelInfo, BorderLayout.CENTER);

        JPanel panelButtons = new JPanel();
        panelButtons.add(btnSelectFile);
        btnStartTest.setEnabled(false);
        panelButtons.add(btnStartTest);
        add(panelButtons, BorderLayout.SOUTH);

        btnSelectFile.addActionListener(this::onSelectFile);
        btnStartTest.addActionListener(this::onStartTest);

        setVisible(true);
    }

    private void onSelectFile(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                testManager.loadFromFile(file);
                lblInfo.setText(String.format(
                    "Ítems: %d | Tiempo: %.1f min",
                    testManager.getTotalItems(),
                    testManager.getTotalEstimatedTimeSec() / 60.0
                ));
                btnStartTest.setEnabled(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "Error al cargar el archivo: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void onStartTest(ActionEvent e) {
        List<Item> items = testManager.getItems();
        SwingUtilities.invokeLater(() -> new QuizFrame(items));
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
