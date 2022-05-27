package com.alxkor.webapp.storage;

import com.alxkor.webapp.exception.ExistStorageException;
import com.alxkor.webapp.exception.NotExistStorageException;
import com.alxkor.webapp.exception.StorageException;
import com.alxkor.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.alxkor.webapp.storage.AbstractArrayStorage.MAX_SIZE;
import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private final String UUID_1 = "uuid_1";
    private final String UUID_2 = "uuid_2";
    private final String UUID_3 = "uuid_3";
    private final Resume r1 = new Resume(UUID_1);
    private final Resume r2 = new Resume(UUID_2);
    private final Resume r3 = new Resume(UUID_3);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    final void setUp() {
        storage.clear();
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
    }

    @Test
    final void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    final void save() {
        storage.save(new Resume("uuid_4"));
        assertEquals(4, storage.size());
    }

    @Test
    final void update() {
        Resume r = new Resume(r1.getUuid());
        storage.update(r);
        assertEquals(r, storage.get(r.getUuid()));
    }

    @Test
    final void get() {
        String uuid = r1.getUuid();
        assertEquals(r1, storage.get(uuid));
    }

    @Test
    final void delete() {
        storage.delete(r1.getUuid());
        assertEquals(2, storage.size());
    }

    @Test
    final void getAll() {
        assertArrayEquals(new Resume[]{r1, r2, r3}, storage.getAll());
    }

    @Test
    final void size() {
        assertEquals(3, storage.size());
    }

    @Test
    final void getIfNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    @Test
    final void saveIfAlreadyExist() {
        assertThrows(ExistStorageException.class, () -> storage.save(r1));
    }

    @Test
    final void storageOverflow() {
        storage.clear();
        assertDoesNotThrow(() -> {
            for (int i = 0; i < MAX_SIZE; i++) storage.save(new Resume());
        }, "Overflow occurred ahead of time");
        assertThrows(StorageException.class, () -> storage.save(new Resume()));
    }
}