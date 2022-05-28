package com.alxkor.webapp.storage;

import com.alxkor.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object getFindKey(String uuid) {
        Resume key = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, key);
    }

    @Override
    protected boolean isResumeExist(Resume r) {
        return (Integer) getFindKey(r.getUuid()) >= 0;
    }

    @Override
    protected void addResume(Resume r, int index) {
        int insertPoint = -index - 1;
        System.arraycopy(storage, insertPoint, storage, insertPoint + 1, size - insertPoint);
        storage[insertPoint] = r;
    }

    @Override
    protected void removeResume(int index) {
        int numMoved = size - 1 - index;
        if (numMoved > 0) System.arraycopy(storage, index + 1, storage, index, numMoved);
    }
}
