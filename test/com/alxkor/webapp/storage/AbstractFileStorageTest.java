package com.alxkor.webapp.storage;

import java.io.File;

class AbstractFileStorageTest extends AbstractStorageTest {
    protected static final File DIR_STORAGE = new File(".\\storage");

    public AbstractFileStorageTest(Storage storage) {
        super(storage);
    }
}