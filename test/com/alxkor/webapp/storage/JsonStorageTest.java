package com.alxkor.webapp.storage;

import com.alxkor.webapp.storage.serializer.JsonStreamSerializer;

public class JsonStorageTest extends AbstractStorageTest {
    protected static final String DIR_STORAGE = ".\\storage";

    public JsonStorageTest() {
        super(new PathStorage(DIR_STORAGE, new JsonStreamSerializer()));
    }
}
