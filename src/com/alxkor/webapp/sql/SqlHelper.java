package com.alxkor.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void execute(String statement) {
        execute(statement, PreparedStatement::execute);
    }

    public <T> T execute(String statement, SqlExecutor<T> executor) {
        try (PreparedStatement ps = connectionFactory.getConnection().prepareStatement(statement)) {
            return executor.execute(ps);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }
}