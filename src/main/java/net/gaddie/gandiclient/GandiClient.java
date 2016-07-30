package net.gaddie.gandiclient;


import de.timroes.axmlrpc.XMLRPCClient;
import de.timroes.axmlrpc.XMLRPCException;
import net.gaddie.gandiclient.requestobjects.RecordDeleteOptions;
import net.gaddie.gandiclient.requestobjects.RecordSpec;
import net.gaddie.gandiclient.requestobjects.XMLSerializable;
import net.gaddie.gandiclient.responseobjects.Zone;
import net.gaddie.gandiclient.responseobjects.ZoneList;
import net.gaddie.gandiclient.responseobjects.ZoneRecord;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static de.timroes.axmlrpc.XMLRPCClient.FLAGS_DEBUG;
import static de.timroes.axmlrpc.XMLRPCClient.FLAGS_NONE;

public class GandiClient {
    private final XMLRPCClient xmlrpcClient;
    private final String apiKey;

    public GandiClient(final Service service, final String apiKey, final boolean debug) {
        this(service, apiKey, debug ? FLAGS_DEBUG : FLAGS_NONE);
    }

    public GandiClient(final Service service, final String apiKey) {
        this(service, apiKey, false);
    }

    private GandiClient(final Service service, final String apiKey, final int flags) {
        this.xmlrpcClient = new XMLRPCClient(service.getUrl(), flags);
        this.apiKey = apiKey;
    }

    private static Integer toId(final Map<String, Object> map) {
        return (Integer) map.get("id");
    }

    public String getVersion() {
        final Map<String, String> call = call("version.info", Map.class);
        return call.get("api_version");

    }

    public List<Zone> getZoneList() {
        return Arrays.stream(call("domain.zone.list", Object[].class)).
                map(Map.class::cast).
                map(map -> new Zone(toId(map),
                        toName(map),
                        (String) map.get("date_updates"),
                        toVersion(map),
                        (Boolean) map.get("public"))).collect(Collectors.toList());
    }

    public ZoneList cloneZone(final Zone zoneToClone) {
        final Map<String, Object> cloneResult = call("domain.zone.clone", Map.class, zoneToClone.id);
        final List<Integer> versions = Arrays.stream(Object[].class.cast(cloneResult.get("versions"))).map(Integer.class::cast).collect(Collectors.toList());
        return new ZoneList(toId(cloneResult),
                (Integer) cloneResult.get("domains"),
                toName(cloneResult),
                (String) cloneResult.get("owner"),
                toVersion(cloneResult),
                versions,
                (Date) cloneResult.get("date_updated"));
    }

    public Integer newZoneVersion(final Zone previousZone) {
        return call("domain.zone.version.new", Integer.class, previousZone.id);
    }

    public Integer deleteZoneRecords(final Zone zone, final Integer version, final RecordDeleteOptions recordDeleteOptions) {
        return call("domain.zone.record.delete", Integer.class, zone.id, version, recordDeleteOptions);
    }

    public ZoneRecord addZoneRecord(final Zone zone, final Integer version, final RecordSpec recordSpec) {
        final Map call = call("domain.zone.record.add", Map.class, zone.id, version, recordSpec);
        return mapToRecordFunction().apply(call);
    }

    public List<ZoneRecord> setZoneRecords(final Zone zone, final Integer version, final List<RecordSpec> recordSpecs) {
        final Object[] call = call("domain.zone.record.set", Object[].class, zone.id, version, recordSpecs);
        return objArrayToRecords(call);
    }

    public boolean setActiveVersion(final Zone zone, final Integer version) {
        return call("domain.zone.version.set", Boolean.class, zone.id, version);
    }

    public List<ZoneRecord> listZoneContents(final Zone zone, final int version) {
        final Object[] records = call("domain.zone.record.list", Object[].class, zone.id, version);

        final List<ZoneRecord> zoneRecordList = objArrayToRecords(records);
        return zoneRecordList;
    }


    public List<ZoneRecord> listActiveZoneContents(final Zone zone) {
        return listZoneContents(zone, 0);
    }

    private <T> T call(final String method, final Class<T> clazz, final Object... args) {
        final Object[] objects = new Object[args.length + 1];
        System.arraycopy(args, 0, objects, 1, args.length);

        swopOutNonSeriliasibleObjectsWithPayload(objects);
        objects[0] = apiKey;
        try {
            return clazz.cast(xmlrpcClient.call(method, objects));
        } catch (final XMLRPCException e) {
            throw new GandiClientException(e);
        }
    }

    private void swopOutNonSeriliasibleObjectsWithPayload(final Object[] objects) {
        for (int i = 0; i < objects.length; i++) {
            final Object curObj = objects[i];
            if (curObj instanceof Iterable) {
                final List list = (List) curObj;
                for (int z = 0; z < list.size(); z++) {
                    final Object item = list.get(z);
                    if (item instanceof XMLSerializable) {
                        final Map<String, ?> payload = ((XMLSerializable) item).payload();
                        list.set(z, payload);
                    }
                }


            }
            if (curObj instanceof XMLSerializable) {
                final XMLSerializable xmlSerializable = (XMLSerializable) curObj;
                objects[i] = xmlSerializable.payload();
            }
        }
    }

    private List<ZoneRecord> objArrayToRecords(final Object[] records) {
        return Arrays.stream(records).map(Map.class::cast).
                map(mapToRecordFunction()).collect(Collectors.toList());
    }

    private Function<Map, ZoneRecord> mapToRecordFunction() {
        return map -> new ZoneRecord(toId(map),
                toName(map),
                (Integer) map.get("ttl"),
                (String) map.get("type"),
                (String) map.get("value"));
    }

    private Integer toVersion(final Map<String, Object> map) {
        return (Integer) map.get("version");
    }

    private String toName(final Map<String, Object> map) {
        return (String) map.get("name");
    }

    public static final class GandiClientException extends RuntimeException {

        public GandiClientException(final Throwable e) {
            super(e);
        }

        @Override
        public String getMessage() {
            return super.getMessage();
        }
    }


}
