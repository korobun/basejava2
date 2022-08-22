package com.alxkor.webapp.model;

public enum ContactType {
    ADDRESS("Адрес"),
    PHONE("Тел."),
    SKYPE("Skype") {
        @Override
        protected String toHtml0(String value) {
            return getTitle() + ": " + toLink("skype" + value, value);
        }
    },
    EMAIL("Почта") {
        @Override
        protected String toHtml0(String value) {
            return getTitle() + ": " + toLink("mailto" + value, value);
        }
    },
    LINKEDIN("Профиль LinkedIn") {
        @Override
        protected String toHtml0(String value) {
            return toLink(value);
        }
    },
    GITHUB("Профиль Github") {
        @Override
        protected String toHtml0(String value) {
            return toLink(value);
        }
    },
    STACKOVERFLOW("Профиль Stackoverflow") {
        @Override
        protected String toHtml0(String value) {
            return toLink(value);
        }
    },
    HOMEPAGE("Домашняя страница") {
        @Override
        protected String toHtml0(String value) {
            return toLink(value);
        }
    };

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String toHtml(String value) {
        return value == null ? "" : toHtml0(value);
    }

    protected String toHtml0(String value) {
        return title + ": " + value;
    }

    protected String toLink(String href) {
        return toLink(href, title);
    }
    protected String toLink(String href, String title) {
        return "<a href=\"" + href + "\">" + title + "</a>";
    }
}
