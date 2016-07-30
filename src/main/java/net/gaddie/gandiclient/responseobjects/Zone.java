package net.gaddie.gandiclient.responseobjects;


public class Zone {
    public final Integer id;
    public final String name;
    public final String dateUpdated;
    public final Integer version;
    public final Boolean isPublic;

    public Zone(final Integer id, final String name, final String dateUpdated, final Integer version, final Boolean isPublic) {
        this.id = id;
        this.name = name;
        this.dateUpdated = dateUpdated;
        this.version = version;
        this.isPublic = isPublic;
    }

    @Override
    public String toString() {
        return "Zone{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateUpdated='" + dateUpdated + '\'' +
                ", version=" + version +
                ", isPublic=" + isPublic +
                '}';
    }
}
