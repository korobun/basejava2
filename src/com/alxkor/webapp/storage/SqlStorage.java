package com.alxkor.webapp.storage;

import com.alxkor.webapp.exception.NotExistStorageException;
import com.alxkor.webapp.exception.StorageException;
import com.alxkor.webapp.model.Resume;
import com.alxkor.webapp.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        executeStatement(connectionFactory, "DELETE FROM resume;", PreparedStatement::execute);
    }

    @Override
    public void save(Resume r) {
        executeStatement(connectionFactory, "INSERT INTO resume VALUES (?, ?);", ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            return ps.execute();
        });
    }

    @Override
    public void update(Resume r) {
        executeStatement(connectionFactory, "UPDATE resume SET full_name = ? WHERE uuid = ?;", ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            return ps.execute();
        });
    }

    @Override
    public Resume get(String uuid) {
        return executeStatement(connectionFactory, "SELECT * FROM resume r WHERE uuid = ?;",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet resultSet = ps.executeQuery();
                    if (!resultSet.next()) throw new NotExistStorageException(uuid);
                    return new Resume(uuid, resultSet.getString("full_name"));
                });
    }

    @Override
    public void delete(String uuid) {
        executeStatement(connectionFactory, "DELETE FROM resume WHERE uuid = ?;", ps -> {
            ps.setString(1, uuid);
            return ps.execute();
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return executeStatement(connectionFactory, "SELECT * FROM resume;", ps->{
            ResultSet resultSet = ps.executeQuery();
            List<Resume> allSorted = new ArrayList<>();
            while (resultSet.next()) {
                allSorted.add(new Resume(resultSet.getString("uuid").trim(),
                        resultSet.getString("full_name")));
            }
            return allSorted;
        });
    }

    @Override
    public int size() {
        return getAllSorted().size();
    }

    private interface Executor<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }

    private <T> T executeStatement(ConnectionFactory connFactory, String statement, Executor<T> executor) {
        try (Connection conn = connFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(statement)) {
            return executor.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}