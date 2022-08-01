package com.alxkor.webapp.sql;

import com.alxkor.webapp.exception.ExistStorageException;
import com.alxkor.webapp.exception.StorageException;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;

public class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static StorageException convertException(SQLException e) {
        if (e instanceof PSQLException) {
//            https://www.postgresql.org/docs/14/errcodes-appendix.html
            if (e.getSQLState().equals("23505")) return new ExistStorageException(null);
        }
        return new StorageException(e);
    }
}
