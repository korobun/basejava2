package com.alxkor.webapp.storage;

import com.alxkor.webapp.exception.NotExistStorageException;
import com.alxkor.webapp.model.*;
import com.alxkor.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume;");
    }

    @Override
    public void save(Resume r) {
        sqlHelper.executeTransaction(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?);")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            insertContacts(conn, r);
            insertSections(conn, r);
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.executeTransaction(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?;")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) throw new NotExistStorageException(r.getUuid());
            }
            deleteRecords(conn, r);
            insertContacts(conn, r);
            insertSections(conn, r);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
//        return sqlHelper.execute(
//                "SELECT * FROM resume r " +
//                        "LEFT JOIN contact c " +
//                        "ON r.uuid = c.resume_uuid " +
//                        "LEFT JOIN section s " +
//                        "ON r.uuid = s.resume_uuid " +
//                        "WHERE r.uuid=?;",
//                ps -> {
//                    ps.setString(1, uuid);
//                    ResultSet resultSet = ps.executeQuery();
//                    if (!resultSet.next()) throw new NotExistStorageException(uuid);
//                    Resume r = new Resume(uuid, resultSet.getString("full_name"));
//                    do {
//                        addContact(resultSet, r);
//                        addSection(resultSet, r);
//                    } while (resultSet.next());
//                    return r;
//                });
        return sqlHelper.executeTransaction(conn -> {
            Resume r;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume WHERE uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) throw new NotExistStorageException(uuid);
                r = new Resume(uuid, rs.getString("full_name"));
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact WHERE resume_uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) addContact(rs, r);
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section WHERE resume_uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) addSection(rs, r);
            }
            return r;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?;", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) throw new NotExistStorageException(uuid);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
//        return sqlHelper.execute(
//                "SELECT * FROM resume " +
//                        "LEFT JOIN contact " +
//                        "ON resume.uuid = contact.resume_uuid " +
//                        "ORDER BY full_name, uuid;", ps -> {
//                    ResultSet resultSet = ps.executeQuery();
//                    Map<String, Resume> map = new LinkedHashMap<>();
//                    while (resultSet.next()) {
//                        String uuid = resultSet.getString("uuid");
//                        Resume r = map.get(uuid);
//                        if (r == null) {
//                            r = new Resume(uuid, resultSet.getString("full_name"));
//                            map.put(uuid, r);
//                        }
//                        addContact(resultSet, r);
//                        addSection(resultSet, r);
//                    }
//                    return new ArrayList<>(map.values());
//                });
        Map<String, Resume> map = new LinkedHashMap<>();
        sqlHelper.execute(
                "SELECT * FROM resume", ps -> {
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid");
                        map.put(uuid, new Resume(uuid, rs.getString("full_name")));
                    }
                    return null;
                }
        );
        sqlHelper.execute(
                "SELECT * FROM contact", ps -> {
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
//                        String uuid = rs.getString("resume_uuid");
//                        map.get(uuid).addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
                        addContact(rs, map.get(rs.getString("resume_uuid")));
                    }
                    return null;
                }
        );
        sqlHelper.execute(
                "SELECT * FROM section", ps -> {
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        addSection(rs, map.get(rs.getString("resume_uuid")));
                    }
                    return null;
                }
        );
        return new ArrayList<>(map.values());
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", ps -> {
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        });
    }

    private void insertContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?);")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSections(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> e : r.getSections().entrySet()) {
                ps.setString(1, r.getUuid());
                SectionType sectionType = e.getKey();
                ps.setString(2, sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        ps.setString(3, ((TextContent) e.getValue()).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        StringBuilder sb = new StringBuilder();
                        for (String s : ((ListContent) e.getValue()).getItems()) {
                            sb.append(s).append("/n");
                        }
                        ps.setString(3, sb.toString());
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        break;
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteRecords(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid=?")) {
            ps.setString(1, r.getUuid());
            ps.executeUpdate();
        }
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section WHERE resume_uuid=?")) {
            ps.setString(1, r.getUuid());
            ps.executeUpdate();
        }
    }

    private void addContact(ResultSet resultSet, Resume r) throws SQLException {
        String type = resultSet.getString("type");
        if (type != null) {
            r.addContact(ContactType.valueOf(type), resultSet.getString("value"));
        }
    }

    private void addSection(ResultSet resultSet, Resume r) throws SQLException {
        SectionType type = SectionType.valueOf(resultSet.getString("type"));
        if (type != null) {
            switch (type) {
                case PERSONAL:
                case OBJECTIVE:
                    r.addSection(type, new TextContent(resultSet.getString("value")));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    String value = resultSet.getString("value");
//                    List<String> listValues = Arrays.asList(value.split("/n"));
                    r.addSection(type, new ListContent(value.split("/n")));
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    break;
            }
        }
    }
}