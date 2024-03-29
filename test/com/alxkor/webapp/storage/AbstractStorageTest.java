package com.alxkor.webapp.storage;

import com.alxkor.webapp.Config;
import com.alxkor.webapp.exception.ExistStorageException;
import com.alxkor.webapp.exception.NotExistStorageException;
import com.alxkor.webapp.model.ContactType;
import com.alxkor.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

import static com.alxkor.webapp.ResumeTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

abstract class AbstractStorageTest {
    protected final Storage storage;
    protected static final File STORAGE = Config.get().getStorageDir();
    protected static final String DB_URL = Config.get().getDbUrl();
    protected static final String DB_USER = Config.get().getDbUser();
    protected static final String DB_PASSWORD = Config.get().getDbPassword();

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    final void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    final void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    final void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertGetResume(RESUME_4);
    }

    @Test
    final void update() {
        Resume r = new Resume(UUID_1, "NewName");
        r.setContact(ContactType.ADDRESS, "newAddress");
        r.setContact(ContactType.EMAIL, "mail@gmail.com");
        storage.update(r);
        assertGetResume(r);
    }

    @Test
    final void get() {
        assertGetResume(RESUME_1);
    }

    @Test
    final void delete() {
        storage.delete(RESUME_1.getUuid());
        assertSize(2);
        assertThrows(NotExistStorageException.class, () -> storage.get(RESUME_1.getUuid()));
    }

    @Test
    final void getAllSorted() {
        assertEquals(Arrays.asList(RESUME_1, RESUME_2, RESUME_3), storage.getAllSorted());
    }

    @Test
    final void size() {
        assertSize(3);
    }

    @Test
    final void getIfNotExist() {
//        Assumptions.assumeFalse(this.getClass().equals(SqlStorageTest.class));
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    @Test
    final void deleteIfNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.delete("dummy"));
    }

    @Test
    final void updateIfNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.update(new Resume("dummy", "name")));
    }

    @Test
    final void saveIfAlreadyExist() {
        assertThrows(ExistStorageException.class, () -> storage.save(RESUME_1));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    private void assertGetResume(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }
}