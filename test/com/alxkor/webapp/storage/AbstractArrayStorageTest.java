package com.alxkor.webapp.storage;

import com.alxkor.webapp.exception.StorageException;
import com.alxkor.webapp.model.Resume;
import org.junit.jupiter.api.Test;

import static com.alxkor.webapp.storage.AbstractArrayStorage.MAX_SIZE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    final void storageOverflow() {
        storage.clear();
        assertDoesNotThrow(() -> {
            for (int i = 0; i < MAX_SIZE; i++) storage.save(new Resume("Name" + i));
        }, "Overflow occurred ahead of time");
        assertThrows(StorageException.class, () -> storage.save(new Resume("Overflow")));
    }
}