package com.alxkor.webapp.storage;

class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage(DB_URL, DB_USER, DB_PASSWORD));
    }
}