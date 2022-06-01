package com.alxkor.webapp.storage;

import com.alxkor.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    protected final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected String getFindKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isResumeExist(Object key) {
        return storage.containsKey((String) key);
    }

    @Override
    protected void doSaving(Resume r, Object key) {
        storage.put((String) key, r);
    }

    @Override
    protected void doUpdating(Resume r, Object key) {
        storage.put((String) key, r);
    }

    @Override
    protected Resume doGetting(Object key) {
        return storage.get((String) key);
    }

    @Override
    protected void doDeleting(Object key) {
        storage.remove((String) key);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
