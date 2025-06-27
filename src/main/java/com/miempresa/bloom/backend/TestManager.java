package com.miempresa.bloom.backend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miempresa.bloom.backend.model.Item;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TestManager {
    private List<Item> items = Collections.emptyList();

    /**
     * Carga y deserializa el JSON con la estructura:
     * {
     *   "items": [ ... ]
     * }
     */
    public void loadFromFile(File jsonFile) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<Item>> root = mapper.readValue(
            jsonFile,
            new TypeReference<Map<String, List<Item>>>() {}
        );

        List<Item> loaded = root.get("items");
        if (loaded == null) {
            throw new IllegalArgumentException("Falta array 'items'.");
        }
        this.items = loaded;
        validateItems();
    }

    /** Valida los campos críticos de cada ítem */
    private void validateItems() {
        for (Item it : items) {
            if (it.getId() == null || it.getId().isBlank())
                throw new IllegalArgumentException("Ítem id vacío.");
            if (it.getQuestion() == null || it.getQuestion().isBlank())
                throw new IllegalArgumentException("Ítem " + it.getId() + ": falta pregunta.");
            if (it.getOptions() == null || it.getOptions().isEmpty())
                throw new IllegalArgumentException("Ítem " + it.getId() + ": opciones vacías.");
            if (it.getCorrectAnswer() < 0 || it.getCorrectAnswer() >= it.getOptions().size())
                throw new IllegalArgumentException("Ítem " + it.getId() + ": respuesta fuera de rango.");
            if (it.getEstimatedTimeSec() <= 0)
                throw new IllegalArgumentException("Ítem " + it.getId() + ": tiempo debe ser >0.");
        }
    }

    /** @return número total de ítems cargados */
    public int getTotalItems() {
        return items.size();
    }

    /** @return tiempo total estimado (en segundos) */
    public int getTotalEstimatedTimeSec() {
        return items.stream().mapToInt(Item::getEstimatedTimeSec).sum();
    }

    /** @return lista inmutable de ítems */
    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }
}
