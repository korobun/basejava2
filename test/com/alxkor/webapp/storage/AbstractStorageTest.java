package com.alxkor.webapp.storage;

import com.alxkor.webapp.exception.ExistStorageException;
import com.alxkor.webapp.exception.NotExistStorageException;
import com.alxkor.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractStorageTest {
    protected final Storage storage;

    private static final String UUID_1 = "uuid_1";
    private static final String UUID_2 = "uuid_2";
    private static final String UUID_3 = "uuid_3";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final Resume RESUME_3 = new Resume(UUID_3);

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
        Resume r = new Resume("uuid_4");
        storage.save(r);
        assertSize(4);
        assertGetResume(r);
    }

    @Test
    final void update() {
        Resume r = new Resume(RESUME_1.getUuid());
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
    }

    @Test
    final void getAll() {
        assertArrayEquals(new Resume[]{RESUME_1, RESUME_2, RESUME_3}, storage.getAll());
    }

    @Test
    final void size() {
        assertSize(3);
    }

    @Test
    final void getIfNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    @Test
    final void deleteIfNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.delete("dummy"));
    }

    @Test
    final void updateIfNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.update(new Resume("dummy")));
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