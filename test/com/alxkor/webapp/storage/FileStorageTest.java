package com.alxkor.webapp.storage;

import com.alxkor.webapp.Config;
import com.alxkor.webapp.storage.serializer.ObjectStreamSerializer;

import java.io.File;

class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(STORAGE, new ObjectStreamSerializer()));
    }
}