package com.alxkor.webapp.storage;

import com.alxkor.webapp.storage.serializer.DataStreamSerializer;
import com.alxkor.webapp.storage.serializer.JsonStreamSerializer;

import java.io.File;

class DataStreamStorageTest extends AbstractStorageTest {
    protected static final String DIR_STORAGE = ".\\storage";

    public DataStreamStorageTest() {
        super(new PathStorage(DIR_STORAGE, new DataStreamSerializer()));
    }
}