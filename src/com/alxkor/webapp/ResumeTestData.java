package com.alxkor.webapp;

import com.alxkor.webapp.model.*;

public class ResumeTestData {
    public static Resume createResume(String fullName, String uuid) {
        Resume resume = new Resume(fullName, uuid);

        resume.addContact(ContactType.ADDRESS, "City");
        resume.addContact(ContactType.PHONE, "+7(999) 123-4567");
        resume.addContact(ContactType.SKYPE, "skype");
        resume.addContact(ContactType.EMAIL, "aaa@bbbb.com");
        resume.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/resume");
        resume.addContact(ContactType.GITHUB, "https://github.com/resume");

//        Organization.Position record1 = new Organization.Position(2010, Month.AUGUST, "Position1", "Description1");
//        Organization.Position record2 = new Organization.Position(2005, Month.AUGUST, 2010, Month.JANUARY, "Position2", "Description2");
//        Organization.Position record3 = new Organization.Position(2000, Month.AUGUST, 2005, Month.JANUARY, "Position3", "Description3");
//        Organization.Position record4 = new Organization.Position(1985, Month.JANUARY, 1990, Month.JUNE, "Student", null);
//
//        Organization organization1 = new Organization("Organization1", "url1", record1, record2);
//        Organization organization2 = new Organization("Organization2", "url2", record3);
//        Organization organization3 = new Organization("University", "url3", record4);
//
//        Section sectionExperience = new ListOrganization(organization1, organization2);
//        Section sectionEducation = new ListOrganization(organization3);
        Section sectionObjective = new TextContent("Position");
        Section sectionPersonal = new TextContent("Personal");
        Section sectionAchievement = new ListContent("Achievement1", "Achievement2", "Achievement2");
        Section sectionQualification = new ListContent("Java", "SQL", "Spring", "JPA", "Hibernate");
//
        resume.addSection(SectionType.ACHIEVEMENT, sectionAchievement);
//        resume.addSection(SectionType.EDUCATION, sectionEducation);
//        resume.addSection(SectionType.EXPERIENCE, sectionExperience);
        resume.addSection(SectionType.OBJECTIVE, sectionObjective);
        resume.addSection(SectionType.PERSONAL, sectionPersonal);
        resume.addSection(SectionType.QUALIFICATIONS, sectionQualification);

        return resume;
    }
}
