package com.alxkor.webapp.storage;

import com.alxkor.webapp.exception.StorageException;
import com.alxkor.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void addResume(Resume r, int index) {
        storage[size] = r;
    }

    @Override
    protected void removeResume(String uuid, int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected boolean storageContainResume(Object key) {
        return (Integer) key >= 0;
    }

    @Override
    protected Object getFindKey(Resume r) {
        return getIndex(r.getUuid());
    }

    @Override
    protected void doSave(Resume r) {
        if (size >= MAX_SIZE) {
            throw new StorageException("ERROR: Storage overflow", r.getUuid());
        }

        addResume(r, index);
        size++;
    }
}
