package com.miempresa.bloom.frontend;

import com.miempresa.bloom.backend.model.Item;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class ReviewFrame extends JFrame {
    public ReviewFrame(List<Item> items, Map<String,Integer> respuestas) {
        super("Revisión");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(700, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0,1));
        for (Item it : items) {
            int sel = respuestas.get(it.getId());
            boolean correct = sel == it.getCorrectAnswer();
            JLabel q = new JLabel(String.format(
                "%s - Tu respuesta: %s (%s)",
                it.getQuestion(),
                it.getOptions().get(sel),
                correct ? "Correcto" : "Incorrecto"
            ));
            panel.add(q);
        }

        add(new JScrollPane(panel), BorderLayout.CENTER);
        setVisible(true);
    }
}
