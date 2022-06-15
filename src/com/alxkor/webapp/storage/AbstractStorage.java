package com.alxkor.webapp.storage;

import com.alxkor.webapp.exception.ExistStorageException;
import com.alxkor.webapp.exception.NotExistStorageException;
import com.alxkor.webapp.model.Resume;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<K> implements Storage {
    //protected final Logger log = Logger.getLogger(getClass().getName());
    private static final Logger log = Logger.getLogger(AbstractStorage.class.getName());

    @Override
    public final void save(Resume r) {
        log.info("Save " + r);
        K key = getKeyIfResumeNotExist(r.getUuid());
        doSaving(r, key);
    }

    @Override
    public final void update(Resume r) {
        log.info("Update " + r);
        K key = getKeyIfResumeExist(r.getUuid());
        doUpdating(r, key);
    }

    @Override
    public final Resume get(String uuid) {
        log.info("Get " + uuid);
        K key = getKeyIfResumeExist(uuid);
        return doGetting(key);
    }

    @Override
    public final void delete(String uuid) {
        log.info("Delete " + uuid);
        K key = getKeyIfResumeExist(uuid);
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

    private K getKeyIfResumeNotExist(String uuid) {
        K key = getFindKey(uuid);
        if (isResumeExist(key)) {
            log.warning("Resume " + uuid + " already exist in storage");
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    private K getKeyIfResumeExist(String uuid) {
        K key = getFindKey(uuid);
        if (!isResumeExist(key)) {
            log.warning("Resume " + uuid + " not exist in storage");
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    protected abstract K getFindKey(String uuid);

    protected abstract boolean isResumeExist(K key);

    protected abstract void doSaving(Resume r, K key);

    protected abstract void doUpdating(Resume r, K key);

    protected abstract Resume doGetting(K key);

    protected abstract void doDeleting(K key);

    protected abstract List<Resume> getCopyAll();
}
