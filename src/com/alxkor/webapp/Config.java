package com.alxkor.webapp;

import com.alxkor.webapp.storage.SqlStorage;
import com.alxkor.webapp.storage.Storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Properties;

public class Config {
    private static final File PROPS = new File(getHomeDir(), "config/resumes.config");
    private static final Config INSTANCE = new Config();
    private final File storageDir;
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;
    private final Storage storage;

    private Config() {
        try (InputStream is = Files.newInputStream(PROPS.toPath())) {
            Properties props = new Properties();
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            dbUrl = props.getProperty("db.url");
            dbUser = props.getProperty("db.user");
            dbPassword = props.getProperty("db.password");
            storage = new SqlStorage(dbUrl, dbUser, dbPassword);
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    public static Config get() {
        return INSTANCE;
    }

    public File getStorageDir() {
        return storageDir;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public Storage getStorage() {
        return storage;
    }

    private static File getHomeDir() {
        String prop = System.getProperty("homeDir");
        File home = new File(prop == null ? "." : prop);
        if (!home.isDirectory()) throw new IllegalStateException(prop + " is not directory");
        return home;
    }
}
