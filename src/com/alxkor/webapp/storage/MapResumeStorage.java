package com.alxkor.webapp.storage;

import com.alxkor.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    protected final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Object getFindKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isResumeExist(Object key) {
        return key != null;
    }

    @Override
    protected void doSaving(Resume r, Object key) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void doUpdating(Resume r, Object key) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGetting(Object key) {
        return (Resume) key;
    }

    @Override
    protected void doDeleting(Object key) {
        Resume r = (Resume) key;
        storage.remove(r.getUuid());
    }

    @Override
    protected List<Resume> getCopyAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }
}
