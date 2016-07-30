package net.gaddie.gandiclient.requestobjects;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class RecordDeleteOptions implements XMLSerializable {
    private final Map<String, List<String>> payload = new HashMap<>();

    public RecordDeleteOptions() {
        payload.put("type", new LinkedList<>());
        payload.put("name", new LinkedList<>());
    }

    public RecordDeleteOptions add(final String name, final String type) {
        payload.get("type").add(type);
        payload.get("name").add(name);
        return this;
    }

    @Override
    public Map<String, List<String>> payload() {
        return payload;
    }
}
