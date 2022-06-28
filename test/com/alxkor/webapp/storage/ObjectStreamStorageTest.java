package com.alxkor.webapp.storage;

class ObjectStreamStorageTest extends AbstractFileStorageTest {

    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(DIR_STORAGE));
    }
}