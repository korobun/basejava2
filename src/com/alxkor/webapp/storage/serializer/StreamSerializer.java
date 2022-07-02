package com.alxkor.webapp.storage.serializer;

import com.alxkor.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StreamSerializer {
    void doWriting(Resume r, OutputStream os) throws IOException;

    Resume doReading(InputStream is) throws IOException;
}
