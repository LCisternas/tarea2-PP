package com.miempresa.bloom.frontend;

import com.miempresa.bloom.backend.model.Item;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizFrame extends JFrame {
    private final List<Item> items;
    private final Map<String,Integer> respuestas = new HashMap<>();
    private int current = 0;

    private final JLabel lblQ = new JLabel();
    private final JRadioButton[] opts = new JRadioButton[4];
    private final ButtonGroup bg = new ButtonGroup();
    private final JButton btnBack = new JButton("Volver");
    private final JButton btnNext = new JButton("Siguiente");

    public QuizFrame(List<Item> items) {
        super("Prueba");
        this.items = items;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 300);
        setLayout(new BorderLayout(5,5));
        setLocationRelativeTo(null);

        // Pregunta y opciones
        JPanel pnlQ = new JPanel(new BorderLayout());
        pnlQ.add(lblQ, BorderLayout.NORTH);

        JPanel pnlOpts = new JPanel(new GridLayout(4,1));
        for (int i = 0; i < opts.length; i++) {
            opts[i] = new JRadioButton();
            bg.add(opts[i]);
            pnlOpts.add(opts[i]);
        }
        pnlQ.add(pnlOpts, BorderLayout.CENTER);
        add(pnlQ, BorderLayout.CENTER);

        // Botones
        JPanel pnlBtns = new JPanel();
        pnlBtns.add(btnBack);
        pnlBtns.add(btnNext);
        add(pnlBtns, BorderLayout.SOUTH);

        btnBack.addActionListener(e -> onNav(-1));
        btnNext.addActionListener(e -> onNav(1));

        render();
        setVisible(true);
    }

    private void render() {
        Item it = items.get(current);
        lblQ.setText("(" + (current+1) + "/" + items.size() + ") ["
            + it.getBloomLevel() + " - " + it.getItemType() + "] " + it.getQuestion()
        );

        // Mostrar opciones
        var opciones = it.getOptions();
        for (int i = 0; i < opts.length; i++) {
            if (i < opciones.size()) {
                opts[i].setText(opciones.get(i));
                opts[i].setVisible(true);
            } else {
                opts[i].setVisible(false);
            }
            opts[i].setSelected(false);
        }

        // Restaurar selección previa
        Integer prev = respuestas.get(it.getId());
        if (prev != null && prev < opts.length) {
            opts[prev].setSelected(true);
        }

        btnBack.setEnabled(current > 0);
        btnNext.setText(current < items.size()-1 ? "Siguiente" : "Enviar respuestas");
    }

    private void onNav(int dir) {
        int sel = -1;
        for (int i = 0; i < opts.length; i++) {
            if (opts[i].isVisible() && opts[i].isSelected()) {
                sel = i;
            }
        }
        if (sel < 0) {
            JOptionPane.showMessageDialog(this,
                "Debes seleccionar una opción.",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        respuestas.put(items.get(current).getId(), sel);

        if (dir > 0 && current == items.size()-1) {
            new SummaryFrame(items, respuestas);
            dispose();
            return;
        }

        current += dir;
        render();
    }
}
