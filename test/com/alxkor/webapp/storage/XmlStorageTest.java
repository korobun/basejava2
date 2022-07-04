package com.alxkor.webapp.storage;

import com.alxkor.webapp.storage.serializer.XmlStreamSerializer;

public class XmlStorageTest extends AbstractStorageTest {
    protected static final String DIR_STORAGE = ".\\storage";

    public XmlStorageTest() {
        super(new PathStorage(DIR_STORAGE, new XmlStreamSerializer()));
    }
}
