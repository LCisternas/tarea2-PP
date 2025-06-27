package com.miempresa.bloom.events;

import java.io.File;

public class ArchivoCargadoEvent implements Event {
    private final File file;
    private final int totalItems;
    private final int totalTimeSec;

    public ArchivoCargadoEvent(File file, int totalItems, int totalTimeSec) {
        this.file = file;
        this.totalItems = totalItems;
        this.totalTimeSec = totalTimeSec;
    }

    public File getFile() { return file; }
    public int getTotalItems() { return totalItems; }
    public int getTotalTimeSec() { return totalTimeSec; }
}
