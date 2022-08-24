package com.alxkor.webapp.util;

import com.alxkor.webapp.model.Organization;

public class HtmlUtil {
    public static String formatDates(Organization.Position position) {
        return DateUtil.format(position.getFrom()) + " - " + DateUtil.format(position.getTo());
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
}
