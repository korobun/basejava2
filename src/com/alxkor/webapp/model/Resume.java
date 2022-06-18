package com.alxkor.webapp.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume {

    // Unique identifier
    private final String uuid;

    private final String fullName;

    private Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);

    private Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(uuid, "full name must  not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return getUuid() + '(' + fullName + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) && fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public String getInfo() {
        StringBuilder info = new StringBuilder();
        info.append(fullName + "\n");

        info.append("КОНТАКТЫ\n");
        for (ContactType contact : ContactType.values()) {
            if (contacts.get(contact) != null) {
                info.append(contact.getTitle() + ": " + contacts.get(contact) + "\n");
            }
        }

        for (SectionType section : SectionType.values()) {
            info.append(section.getTitle().toUpperCase() + "\n" + sections.get(section).toString() + "\n");
        }

        return info.toString();
    }

    public void addContacts(ContactType key, String contact) {
        contacts.put(key, contact);
    }

    public void addSection(SectionType key, Section section) {
        sections.put(key, section);
    }
}
