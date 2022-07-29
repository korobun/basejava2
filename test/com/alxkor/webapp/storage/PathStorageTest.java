package com.alxkor.webapp.storage;

import com.alxkor.webapp.Config;
import com.alxkor.webapp.storage.serializer.ObjectStreamSerializer;

public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(STORAGE.getAbsolutePath(), new ObjectStreamSerializer()));
    }
}
