package com.alxkor.webapp.storage;

import com.alxkor.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final int MAX_SIZE = 10000;
    private Resume[] storage = new Resume[MAX_SIZE];

    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size - 1, null);
        size = 0;
    }

    public void save(Resume r) {
        if (size >= MAX_SIZE) {
            System.out.println("ERROR: Storage overflow");
            return;
        }

        if (getIndex(r.getUuid()) >= 0) {
            System.out.println("ERROR: Resume " + r.getUuid() + " already exist in storage");
            return;
        }

        storage[size] = r;
        size++;
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            storage[index] = r;
        } else {
            System.out.println("ERROR: Resume " + r.getUuid() + " not exist in storage");
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        return index >= 0 ? storage[index] : null;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
            size--;
        } else {
            System.out.println("ERROR: Resume " + uuid + " not exist in storage.");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
