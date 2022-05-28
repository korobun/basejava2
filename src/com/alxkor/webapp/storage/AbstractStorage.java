package com.alxkor.webapp.storage;

import com.alxkor.webapp.exception.ExistStorageException;
import com.alxkor.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    public final void save(Resume r) {
        checkIfResumeExist(r);
        doSave(r);
    }

    protected final void checkIfResumeExist(Resume r) {
        Object key = getFindKey(r);
        if (storageContainResume(key)) throw new ExistStorageException(r.getUuid());
    }

    protected abstract boolean storageContainResume(Object key);

    protected abstract Object getFindKey(Resume r);

    protected abstract void doSave(Resume r);
}
