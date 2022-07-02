package com.alxkor.webapp.storage;

import com.alxkor.webapp.storage.serializer.ObjectStreamSerializer;

public class PathStorageTest extends AbstractStorageTest {
    protected static final String DIR_STORAGE = ".\\storage";

    public PathStorageTest() {
        super(new AbstractPathStorage(DIR_STORAGE, new ObjectStreamSerializer()));
    }
}
