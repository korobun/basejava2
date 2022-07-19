package com.alxkor.webapp.storage.serializer;

import com.alxkor.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWriting(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            writeCollection(dos, contacts.entrySet(), (item) -> {
                dos.writeUTF(item.getKey().name());
                dos.writeUTF(item.getValue());
            });
            Map<SectionType, Section> sections = r.getSections();
            writeCollection(dos, sections.entrySet(), (item) -> {
                SectionType type = item.getKey();
                Section section = item.getValue();
                dos.writeUTF(type.name());
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextContent) section).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeCollection(dos, ((ListContent) section).getItems(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeCollection(dos, ((ListOrganization) section).getItems(), (organization) -> {
                            dos.writeUTF(organization.getHomepage().getTitle());
                            dos.writeUTF(organization.getHomepage().getUrl());
                            writeCollection(dos, organization.getPositions(), (position) -> {
                                writeLocalDate(dos, position.getFrom());
                                writeLocalDate(dos, position.getTo());
                                dos.writeUTF(position.getPosition());
                                dos.writeUTF(position.getDescription());
                            });
                        });
                        break;
                }
            });
        }
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate ld) throws IOException {
        dos.writeInt(ld.getYear());
        dos.writeInt(ld.getMonth().getValue());
    }

    public interface Writer<T> {
        void write(T item) throws IOException;
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> items, Writer<T> writer) throws IOException {
        dos.writeInt(items.size());
        for (T item : items) {
            writer.write(item);
        }
    }

    @Override
    public Resume doReading(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readItems(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readItems(dis, () -> {
                SectionType type = SectionType.valueOf(dis.readUTF());
                resume.addSection(type, readSection(dis, type));
            });
            return resume;
        }
    }

    private Section readSection(DataInputStream dis, SectionType type) throws IOException {
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                return new TextContent(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListContent(readList(dis, dis::readUTF));
            case EXPERIENCE:
            case EDUCATION:
                return new ListOrganization(readList(dis, () -> new Organization(new Link(dis.readUTF(),
                        dis.readUTF()), readList(dis, () -> new Organization.Position(readLocalData(dis),
                        readLocalData(dis),
                        dis.readUTF(),
                        dis.readUTF())))));
            default:
                throw new IllegalStateException();
        }
    }


    private LocalDate readLocalData(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }

    private <T> List<T> readList(DataInputStream dis, Reader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> items = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            items.add(reader.read());
        }
        return items;
    }

    public interface Reader<T> {
        T read() throws IOException;
    }

    private void readItems(DataInputStream dis, Processor processor) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            processor.process();
        }
    }

    public interface Processor {
        void process() throws IOException;
    }
}
