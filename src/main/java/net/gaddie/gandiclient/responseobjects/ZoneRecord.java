package net.gaddie.gandiclient.responseobjects;

public class ZoneRecord {
    private final Integer id;
    private final String name;
    private final Integer ttl;
    private final String type;
    private final String value;

    public ZoneRecord(final Integer id, final String name, final Integer ttl, final String type, final String value) {
        this.id = id;
        this.name = name;
        this.ttl = ttl;
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ttl=" + ttl +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
