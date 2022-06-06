package com.alxkor.webapp.storage;

import com.alxkor.webapp.exception.ExistStorageException;
import com.alxkor.webapp.exception.NotExistStorageException;
import com.alxkor.webapp.model.Resume;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    @Override
    public final void save(Resume r) {
        Object key = getFindKey(r.getUuid());
        if (isResumeExist(key)) throw new ExistStorageException(r.getUuid());
        doSaving(r, key);
    }

    @Override
    public final void update(Resume r) {
        Object key = getFindKey(r.getUuid());
        if (!isResumeExist(key)) throw new NotExistStorageException(r.getUuid());
        doUpdating(r, key);
    }

    @Override
    public final Resume get(String uuid) {
        Object key = getFindKey(uuid);
        if (!isResumeExist(key)) throw new NotExistStorageException(uuid);
        return doGetting(key);
    }

    @Override
    public final void delete(String uuid) {
        Object key = getFindKey(uuid);
        if (!isResumeExist(key)) throw new NotExistStorageException(uuid);
        doDeleting(key);
    }

    @Override
    public final List<Resume> getAllSorted() {
        List<Resume> allResumes = getAllResumes();
        Comparator<Resume> comparator = Comparator.comparing(Resume::getFullName);
        comparator.thenComparing(Resume::getUuid);

        Collections.sort(allResumes, comparator);
        return allResumes;
    }

    protected abstract Object getFindKey(String uuid);

    protected abstract boolean isResumeExist(Object key);

    protected abstract void doSaving(Resume r, Object key);

    protected abstract void doUpdating(Resume r, Object key);

    protected abstract Resume doGetting(Object key);

    protected abstract void doDeleting(Object key);

    protected abstract List<Resume> getAllResumes();
}
