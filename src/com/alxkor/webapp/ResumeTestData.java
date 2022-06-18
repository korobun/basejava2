package com.alxkor.webapp;

import com.alxkor.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume gKislin = new Resume("Григорий Кислин");

        gKislin.addContacts(ContactType.PHONE, "+7(921) 855-0482");
        gKislin.addContacts(ContactType.SKYPE, "skype:grigory.kislin");
        gKislin.addContacts(ContactType.EMAIL, "gkislin@yandex.ru");
        gKislin.addContacts(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        gKislin.addContacts(ContactType.GITHUB, "https://github.com/gkislin");

        Section sectionObjective =
                new TextContent("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям.");

        Section sectionPersonal =
                new TextContent("Аналитический склад ума, сильная логика, креативность, инициативность. " +
                        "Пурист кода и архитектуры.");

        List<String> achievementList = new ArrayList<>(Arrays.asList("Организация команды и успешная реализация Java проектов " +
                        "для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей " +
                        "спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект " +
                        "для комплексных DIY смет",
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. " +
                        "Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". " +
                        "Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, " +
                        "DuoSecurity, Google Authenticator, Jira, Zendesk."));

        Section sectionAchievement = new ListContent(achievementList);

        List<String> qualificationList = new ArrayList<>(Arrays.asList("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB"));

        Section sectionQualification = new ListContent(qualificationList);

        Organization javaOnlineProject = new Organization(
                LocalDate.of(2013, 10, 1),
                LocalDate.now(),
                "Java Online Projects",
                "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок.");

        Organization wrike = new Organization(
                LocalDate.of(2014, 10, 1),
                LocalDate.of(2016, 1, 1),
                "Wrike",
                "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, " +
                        "MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");

        List<Organization> workPlaces = new ArrayList<>(Arrays.asList(javaOnlineProject, wrike));

        Section sectionExperience = new ListOrganization(workPlaces);

        Organization university = new Organization(
                LocalDate.of(1987, 9, 1),
                LocalDate.of(1993, 7, 1),
                "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                "Инженер (программист Fortran, C)",
                null);

        List<Organization> educationPlaces = new ArrayList<>(Arrays.asList(university));

        Section sectionEducation = new ListOrganization(educationPlaces);

        gKislin.addSection(SectionType.ACHIEVEMENT, sectionAchievement);
        gKislin.addSection(SectionType.EDUCATION, sectionEducation);
        gKislin.addSection(SectionType.EXPERIENCE, sectionExperience);
        gKislin.addSection(SectionType.OBJECTIVE, sectionObjective);
        gKislin.addSection(SectionType.PERSONAL, sectionPersonal);
        gKislin.addSection(SectionType.QUALIFICATIONS, sectionQualification);

        System.out.println(gKislin.getInfo());
    }
}
