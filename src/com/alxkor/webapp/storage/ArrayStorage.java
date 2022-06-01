package com.alxkor.webapp.storage;

import com.alxkor.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer getFindKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) return i;
        }
        return -1;
    }

    @Override
    protected void addResume(Resume r, Integer index) {
        storage[size] = r;
    }

    @Override
    protected void removeResume(int index) {
        storage[index] = storage[size - 1];
    }
}
