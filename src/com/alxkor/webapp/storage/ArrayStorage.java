package com.alxkor.webapp.storage;

import com.alxkor.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object getFindKey(String uuid) {
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
    protected void removeResume(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected boolean isResumeExist(Resume r) {
        return (Integer) getFindKey(r.getUuid()) >= 0;
    }
}
