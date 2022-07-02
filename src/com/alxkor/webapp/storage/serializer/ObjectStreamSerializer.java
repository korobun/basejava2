package com.alxkor.webapp.storage.serializer;

import com.alxkor.webapp.exception.StorageException;
import com.alxkor.webapp.model.Resume;

import java.io.*;

public class ObjectStreamSerializer implements StreamSerializer {
    @Override
    public void doWriting(Resume r, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(r);
        }
    }

    @Override
    public Resume doReading(InputStream is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error resume read", null, e);
        }
    }
}
