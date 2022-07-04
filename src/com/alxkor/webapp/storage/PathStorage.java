package com.alxkor.webapp.storage;

import com.alxkor.webapp.exception.StorageException;
import com.alxkor.webapp.model.Resume;
import com.alxkor.webapp.storage.serializer.StreamSerializer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;

    private StreamSerializer serializer;

    public PathStorage(String dir, StreamSerializer serializer) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "Directory path must not be null");
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(directory.toAbsolutePath() + " is not a directory");
        }
        if (!Files.isReadable(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(directory.toAbsolutePath() + " is not readable/writable");
        }

        Objects.requireNonNull(serializer, "Serializer must not be null");
        this.serializer = serializer;
    }

    @Override
    protected Path getFindKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isResumeExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void doSaving(Resume r, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Create file error: " + path.toAbsolutePath(), r.getUuid(), e);
        }
        doUpdating(r, path);
    }

    @Override
    protected void doUpdating(Resume r, Path path) {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
            serializer.doWriting(r, oos);
        } catch (IOException e) {
            throw new StorageException("Write file error", r.getUuid(), e);
        }
    }

    @Override
    protected Resume doGetting(Path path) {
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path))) {
            return serializer.doReading(ois);
        } catch (IOException e) {
            throw new StorageException("Read file error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void doDeleting(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("File delete error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected List<Resume> getCopyAll() {
        return getAllPath().map(this::doGetting).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getAllPath().forEach(this::doDeleting);
    }

    @Override
    public int size() {
        return (int) getAllPath().count();
    }

    private Stream<Path> getAllPath() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory read error", null, e);
        }
    }
}
