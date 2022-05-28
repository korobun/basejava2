package com.alxkor.webapp.storage;

import com.alxkor.webapp.exception.ExistStorageException;
import com.alxkor.webapp.exception.NotExistStorageException;
import com.alxkor.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    @Override
    public final void save(Resume r) {
        Object key = getFindKey(r.getUuid());
        if (isResumeExist(r)) throw new ExistStorageException(r.getUuid());
        doSaving(r, key);
    }

    @Override
    public final void update(Resume r) {
        Object key = getFindKey(r.getUuid());
        if (!isResumeExist(r)) throw new NotExistStorageException(r.getUuid());
        doUpdating(r, key);
    }

    @Override
    public final Resume get(String uuid) {
        Object key = getFindKey(uuid);
        if (!isResumeExist(new Resume(uuid))) throw new NotExistStorageException(uuid);
        return doGetting(key);
    }

    @Override
    public final void delete(String uuid) {
        Object key = getFindKey(uuid);
        if (!isResumeExist(new Resume(uuid))) throw new NotExistStorageException(uuid);
        doDeleting(key);
    }

    protected abstract Object getFindKey(String uuid);

    protected abstract boolean isResumeExist(Resume r);

    protected abstract void doSaving(Resume r, Object key);

    protected abstract void doUpdating(Resume r, Object key);

    protected abstract Resume doGetting(Object key);

    protected abstract void doDeleting(Object key);
}
