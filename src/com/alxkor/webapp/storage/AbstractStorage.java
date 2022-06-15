package com.alxkor.webapp.storage;

import com.alxkor.webapp.exception.ExistStorageException;
import com.alxkor.webapp.exception.NotExistStorageException;
import com.alxkor.webapp.model.Resume;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<K> implements Storage {
    @Override
    public final void save(Resume r) {
        K key = getFindKey(r.getUuid());
        if (isResumeExist(key)) throw new ExistStorageException(r.getUuid());
        doSaving(r, key);
    }

    @Override
    public final void update(Resume r) {
        K key = getFindKey(r.getUuid());
        if (!isResumeExist(key)) throw new NotExistStorageException(r.getUuid());
        doUpdating(r, key);
    }

    @Override
    public final Resume get(String uuid) {
        K key = getFindKey(uuid);
        if (!isResumeExist(key)) throw new NotExistStorageException(uuid);
        return doGetting(key);
    }

    @Override
    public final void delete(String uuid) {
        K key = getFindKey(uuid);
        if (!isResumeExist(key)) throw new NotExistStorageException(uuid);
        doDeleting(key);
    }

    @Override
    public final List<Resume> getAllSorted() {
        List<Resume> allResumes = getCopyAll();
        Comparator<Resume> comparator = Comparator.comparing(Resume::getFullName);
        comparator.thenComparing(Resume::getUuid);

        Collections.sort(allResumes, comparator);
        return allResumes;
    }

    protected abstract K getFindKey(String uuid);

    protected abstract boolean isResumeExist(K key);

    protected abstract void doSaving(Resume r, K key);

    protected abstract void doUpdating(Resume r, K key);

    protected abstract Resume doGetting(K key);

    protected abstract void doDeleting(K key);

    protected abstract List<Resume> getCopyAll();
}
