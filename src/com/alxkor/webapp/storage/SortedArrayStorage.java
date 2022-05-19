package com.alxkor.webapp.storage;

import com.alxkor.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume key = new Resume();
        key.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, key);
    }

    @Override
    protected void addResume(Resume r, int index) {
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = r;
    }

    @Override
    protected void removeResume(String uuid, int index) {
        System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
    }
}
