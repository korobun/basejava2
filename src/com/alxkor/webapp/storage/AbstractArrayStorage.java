package com.alxkor.webapp.storage;

import com.alxkor.webapp.exception.StorageException;
import com.alxkor.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {

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
    protected void doSaving(Resume r, Object key) {
        if (size >= MAX_SIZE) {
            throw new StorageException("ERROR: Storage overflow", r.getUuid());
        }
        addResume(r, (Integer) key);
        size++;
    }

    @Override
    protected void doUpdating(Resume r, Object key) {
        int index = (Integer) key;
        storage[index] = r;
    }

    @Override
    protected Resume doGetting(Object key) {
        int index = (Integer) key;
        return storage[index];
    }

    @Override
    protected void doDeleting(Object key) {
        int index = (Integer) key;
        removeResume(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected List<Resume> getAllResumes() {
        Resume[] resumeArray = Arrays.copyOfRange(storage, 0, size);
        return new ArrayList<>(Arrays.asList(resumeArray));
    }

    @Override
    protected boolean isResumeExist(Object key) {
        return (Integer) key >= 0;
    }

    protected abstract void addResume(Resume r, Integer index);

    protected abstract void removeResume(int index);

}
