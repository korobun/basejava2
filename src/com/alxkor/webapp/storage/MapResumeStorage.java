package com.alxkor.webapp.storage;

import com.alxkor.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    protected final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Resume getFindKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isResumeExist(Resume key) {
        return key != null;
    }

    @Override
    protected void doSaving(Resume r, Resume key) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void doUpdating(Resume r, Resume key) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGetting(Resume key) {
        return key;
    }

    @Override
    protected void doDeleting(Resume key) {
        storage.remove(key.getUuid());
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
