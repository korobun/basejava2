package com.alxkor.webapp.storage;

import com.alxkor.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    protected final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
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
    protected boolean isResumeExist(Integer key) {
        return key != null;
    }

    @Override
    protected void doSaving(Resume r, Integer key) {
        storage.add(r);
    }

    @Override
    protected void doUpdating(Resume r, Integer key) {
        storage.set(key, r);
    }

    @Override
    protected Resume doGetting(Integer key) {
        return storage.get(key);
    }

    @Override
    protected void doDeleting(Integer key) {
        storage.remove(key.intValue());
    }

    @Override
    protected List<Resume> getCopyAll() {
        return new ArrayList<>(storage);
    }
}
