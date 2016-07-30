package net.gaddie.gandiclient.requestobjects;

import java.util.HashMap;
import java.util.Map;

public class RecordSpec implements XMLSerializable {
    private final HashMap<String, Object> payload;

    public RecordSpec(final String type, final String name, final String value) {
        payload = new HashMap<>();
        payload.put("type", type);
        payload.put("name", name);
        payload.put("value", value);
    }

    public RecordSpec ttl(final int ttl) {
        payload.put("ttl", ttl);
        return this;
    }

    @Override
    public Map<String, ?> payload() {
        return payload;
    }
}
