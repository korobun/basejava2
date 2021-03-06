package com.alxkor.webapp.storage;

import com.alxkor.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer getFindKey(String uuid) {
        Resume key = new Resume(uuid, "dummy");
        return Arrays.binarySearch(storage, 0, size, key, Comparator.comparing(Resume::getUuid));
    }

    @Override
    protected void addResume(Resume r, Integer index) {
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
