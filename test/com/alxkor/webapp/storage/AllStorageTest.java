package com.alxkor.webapp.storage;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        MapUuidStorageTest.class,
        MapResumeStorageTest.class,
        FileStorageTest.class,
        PathStorageTest.class,
        XmlStorageTest.class,
        JsonStorageTest.class,
        DataStreamStorageTest.class,
        SqlStorageTest.class})
public class AllStorageTest {
}
