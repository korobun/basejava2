package com.alxkor.webapp.storage;

import com.alxkor.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {

    protected static final int MAX_SIZE = 10_000;
    protected static final Resume[] storage = new Resume[MAX_SIZE];
    protected int size = 0;

    public final void clear() {
        Arrays.fill(storage, 0, size - 1, null);
        size = 0;
    }

    public final void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            System.out.println("ERROR: Resume " + r.getUuid() + " already exist in storage");
            return;
        }

        if (size >= MAX_SIZE) {
            System.out.println("ERROR: Storage overflow");
            return;
        }

        index = -index - 1;
        addResume(r, index);
        size++;
    }

    public final void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            storage[index] = r;
        } else {
            System.out.println("ERROR: Resume " + r.getUuid() + " not exist in storage");
        }
    }

    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        return index >= 0 ? storage[index] : null;
    }

    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            removeResume(uuid, index);
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("ERROR: Resume " + uuid + " not exist in storage.");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public final Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public final int size() {
        return size;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void addResume(Resume r, int index);

    protected abstract void removeResume(String uuid, int index);

}
