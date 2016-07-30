package net.gaddie.gandiclient.responseobjects;

import java.util.Date;
import java.util.List;

public class ZoneList {
    public final Integer id;
    public final Integer domains;
    public final String name;
    public final String owner;
    public final int version;
    public final List<Integer> versions;
    public final Date dateUpdates;

    public ZoneList(final Integer id, final Integer domains, final String name, final String owner, final int version, final List<Integer> versions, final Date dateUpdates) {
        this.id = id;
        this.domains = domains;
        this.name = name;
        this.owner = owner;
        this.version = version;
        this.versions = versions;
        this.dateUpdates = dateUpdates;
    }

    @Override
    public String toString() {
        return "DuplicateResult{" +
                "id=" + id +
                ", domains=" + domains +
                ", name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", version=" + version +
                ", versions=" + versions +
                ", dateUpdates='" + dateUpdates + '\'' +
                '}';
    }
}
