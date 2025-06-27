package com.miempresa.bloom.frontend;

import com.miempresa.bloom.backend.model.Item;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SummaryFrame extends JFrame {
    public SummaryFrame(List<Item> items, Map<String,Integer> respuestas) {
        super("Resumen");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 300);
        setLayout(new BorderLayout(5,5));
        setLocationRelativeTo(null);

        // Construir HTML con estadísticas
        StringBuilder sb = new StringBuilder("<html><body>");
        sb.append("<h3>% de aciertos por nivel Bloom:</h3>");
        items.stream()
             .collect(Collectors.groupingBy(Item::getBloomLevel))
             .forEach((lvl, list) -> {
                 long correct = list.stream()
                     .filter(it -> respuestas.get(it.getId()) == it.getCorrectAnswer())
                     .count();
                 sb.append(String.format("%s: %.1f%%<br>", lvl, correct * 100.0 / list.size()));
             });

        sb.append("<h3>% de aciertos por tipo de ítem:</h3>");
        items.stream()
             .collect(Collectors.groupingBy(it -> it.getItemType().name()))
             .forEach((typ, list) -> {
                 long correct = list.stream()
                     .filter(it -> respuestas.get(it.getId()) == it.getCorrectAnswer())
                     .count();
                 sb.append(String.format("%s: %.1f%%<br>", typ, correct * 100.0 / list.size()));
             });
        sb.append("</body></html>");

        JLabel lblSummary = new JLabel(sb.toString());
        add(new JScrollPane(lblSummary), BorderLayout.CENTER);

        JButton btnReview = new JButton("Revisar respuestas");
        btnReview.addActionListener(e -> new ReviewFrame(items, respuestas));
        add(btnReview, BorderLayout.SOUTH);

        setVisible(true);
    }
}
