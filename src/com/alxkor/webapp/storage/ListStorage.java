package com.alxkor.webapp.storage;

import com.alxkor.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Integer getFindKey(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) return i;
        }
        return null;
    }

    @Override
    protected boolean isResumeExist(Object key) {
        return key != null;
    }

    @Override
    protected void doSaving(Resume r, Object key) {
        storage.add(r);
    }

    @Override
    protected void doUpdating(Resume r, Object key) {
        int index = (Integer) key;
        storage.set(index, r);
    }

    @Override
    protected Resume doGetting(Object key) {
        int index = (Integer) key;
        return storage.get(index);
    }

    @Override
    protected void doDeleting(Object key) {
        int index = (Integer) key;
        storage.remove(index);
    }
}
