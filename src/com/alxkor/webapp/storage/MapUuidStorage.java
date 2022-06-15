package com.alxkor.webapp.storage;

import com.alxkor.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {
    protected final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected String getFindKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isResumeExist(String key) {
        return storage.containsKey(key);
    }

    @Override
    protected void doSaving(Resume r, String key) {
        storage.put(key, r);
    }

    @Override
    protected void doUpdating(Resume r, String key) {
        storage.put(key, r);
    }

    @Override
    protected Resume doGetting(String key) {
        return storage.get(key);
    }

    @Override
    protected void doDeleting(String key) {
        storage.remove(key);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected List<Resume> getCopyAll() {
        return new ArrayList<>(storage.values());
    }
}
