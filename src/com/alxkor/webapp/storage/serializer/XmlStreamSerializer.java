package com.alxkor.webapp.storage.serializer;

import com.alxkor.webapp.model.*;
import com.alxkor.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements StreamSerializer {
    private final XmlParser xmlParser;

    public XmlStreamSerializer() {
        xmlParser = new XmlParser(Resume.class,
                Link.class,
                ListContent.class,
                ListOrganization.class,
                Organization.class,
                Organization.Position.class,
                TextContent.class);
    }

    @Override
    public void doWriting(Resume r, OutputStream os) throws IOException {
        xmlParser.marshall(r, new OutputStreamWriter(os, StandardCharsets.UTF_8));
    }

    @Override
    public Resume doReading(InputStream is) throws IOException {
        return xmlParser.unmarshall(new InputStreamReader(is));
    }
}
