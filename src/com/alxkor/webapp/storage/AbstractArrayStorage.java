package com.alxkor.webapp.storage;

import com.alxkor.webapp.exception.StorageException;
import com.alxkor.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    protected static final int MAX_SIZE = 10_000;
    protected final Resume[] storage = new Resume[MAX_SIZE];
    protected int size = 0;

    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public final int size() {
        return size;
    }

    @Override
    protected void doSaving(Resume r, Integer key) {
        if (size >= MAX_SIZE) {
            throw new StorageException("ERROR: Storage overflow", r.getUuid());
        }
        addResume(r, key);
        size++;
    }

    @Override
    protected void doUpdating(Resume r, Integer key) {
        storage[key] = r;
    }

    @Override
    protected Resume doGetting(Integer key) {
        return storage[key];
    }

    @Override
    protected void doDeleting(Integer key) {
        removeResume(key);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected List<Resume> getCopyAll() {
        return new ArrayList<>(Arrays.asList(Arrays.copyOfRange(storage, 0, size)));
    }

    @Override
    protected boolean isResumeExist(Integer key) {
        return key >= 0;
    }

    protected abstract void addResume(Resume r, Integer index);

    protected abstract void removeResume(int index);

}
