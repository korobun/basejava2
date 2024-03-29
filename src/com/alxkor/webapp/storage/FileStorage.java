package com.alxkor.webapp.storage;

import com.alxkor.webapp.exception.StorageException;
import com.alxkor.webapp.model.Resume;
import com.alxkor.webapp.storage.serializer.StreamSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;

    private StreamSerializer serializer;

    protected FileStorage(File directory, StreamSerializer serializer) {
        Objects.requireNonNull(directory, "Directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not a directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;

        Objects.requireNonNull(serializer, "Serializer must not be null");
        this.serializer = serializer;
    }

    @Override
    protected File getFindKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isResumeExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSaving(Resume r, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Create file error: " + file.getAbsolutePath(), r.getUuid(), e);
        }
        doUpdating(r, file);
    }

    @Override
    protected void doUpdating(Resume r, File file) {
        try {
            serializer.doWriting(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Write file error", r.getUuid(), e);
        }
    }

    @Override
    protected Resume doGetting(File file) {
        try {
            return serializer.doReading(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Read file error", file.getName(), e);
        }
    }

    @Override
    protected void doDeleting(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error", file.getName());
        }
    }

    @Override
    protected List<Resume> getCopyAll() {
        File[] listFiles = directory.listFiles();
        List<Resume> listResumes = new ArrayList<>();
        if (listFiles == null) throw new StorageException("Directory read error");
        for (File file : listFiles) {
            listResumes.add(doGetting(file));
        }
        return listResumes;
    }

    @Override
    public void clear() {
        File[] list = directory.listFiles();
        if (list != null) {
            for (File file : list) {
                doDeleting(file);
            }
        }
    }

    @Override
    public int size() {
        String[] files = directory.list();
        if (files == null) throw new StorageException("Directory read error");
        return files.length;
    }
}
