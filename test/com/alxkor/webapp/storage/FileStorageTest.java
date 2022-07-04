package com.alxkor.webapp.storage;

import com.alxkor.webapp.storage.serializer.ObjectStreamSerializer;

import java.io.File;

class FileStorageTest extends AbstractStorageTest {
    protected static final File DIR_STORAGE = new File(".\\storage");

    public FileStorageTest() {
        super(new FileStorage(DIR_STORAGE, new ObjectStreamSerializer()));
    }
}