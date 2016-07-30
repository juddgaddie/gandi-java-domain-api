# Gandi Java DNS API
A Java library for creating, adding, updating and deleting DNS zones hosted by Gandi

Gandi API documentation: http://doc.rpc.gandi.net/domain/

Patches very welcome. If you would like the binaries in Maven Central just raise an issue and I'll sort it out.

This was created in order to perform Lets Encrypt DNS challenge validation with https://github.com/shred/acme4j

Example Usage

```java
        GandiClient gandiClient = new GandiClient(Service.STAGING, "secretApiKey");
        List<Zone> zoneList = gandiClient.getZoneList();
        zoneList.stream().forEach(System.out::println);

        Zone fiZone = zoneList.stream().filter(zone -> "Default .fi zone file (1)".equals(zone.name)).findFirst().get();
        System.out.println(fiZone);

        // You cannot edit an active zone file, you need to create a new version
        Integer newVersionId = gandiClient.newZoneVersion(fiZone);
        gandiClient.addZoneRecord(fiZone, newVersionId, new RecordSpec("TXT", "myrecord", "someValue"));
```

### Build

```bash
./gradlew build
```


