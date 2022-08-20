package com.alxkor.webapp;

import com.alxkor.webapp.model.*;

import java.time.Month;
import java.util.UUID;

public class ResumeTestData {
    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();
    public static final Resume RESUME_1 = ResumeTestData.createResume(UUID_1, "Name1");
    public static final Resume RESUME_2 = ResumeTestData.createResume(UUID_2, "Name2");
    public static final Resume RESUME_3 = ResumeTestData.createResume(UUID_3, "Name3");
    public static final Resume RESUME_4 = ResumeTestData.createResume(UUID_4, "Name4");

    public static Resume createResume(String fullName, String uuid) {
        Resume resume = new Resume(fullName, uuid);

        resume.setContact(ContactType.ADDRESS, "City");
        resume.setContact(ContactType.PHONE, "+7(999) 123-4567");
        resume.setContact(ContactType.SKYPE, "skype");
        resume.setContact(ContactType.EMAIL, "aaa@bbbb.com");
        resume.setContact(ContactType.LINKEDIN, "https://www.linkedin.com/resume");
        resume.setContact(ContactType.GITHUB, "https://github.com/resume");

        Organization.Position record1 = new Organization.Position(2010, Month.AUGUST, "Position1", "Description1");
        Organization.Position record2 = new Organization.Position(2005, Month.AUGUST, 2010, Month.JANUARY, "Position2", "Description2");
        Organization.Position record3 = new Organization.Position(2000, Month.AUGUST, 2005, Month.JANUARY, "Position3", "Description3");
        Organization.Position record4 = new Organization.Position(1985, Month.JANUARY, 1990, Month.JUNE, "Student", null);

        Organization organization1 = new Organization("Organization1", "url1", record1, record2);
        Organization organization2 = new Organization("Organization2", "url2", record3);
        Organization organization3 = new Organization("University", "url3", record4);

        Section sectionExperience = new ListOrganization(organization1, organization2);
        Section sectionEducation = new ListOrganization(organization3);
        Section sectionObjective = new TextContent("Position");
        Section sectionPersonal = new TextContent("Personal");
        Section sectionAchievement = new ListContent("Achievement1", "Achievement2", "Achievement2");
        Section sectionQualification = new ListContent("Java", "SQL", "Spring", "JPA", "Hibernate");

        resume.setSection(SectionType.ACHIEVEMENT, sectionAchievement);
        resume.setSection(SectionType.EDUCATION, sectionEducation);
        resume.setSection(SectionType.EXPERIENCE, sectionExperience);
        resume.setSection(SectionType.OBJECTIVE, sectionObjective);
        resume.setSection(SectionType.PERSONAL, sectionPersonal);
        resume.setSection(SectionType.QUALIFICATIONS, sectionQualification);

        return resume;
    }
}
