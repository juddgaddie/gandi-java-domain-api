package net.gaddie.gandiclient;

import java.net.MalformedURLException;
import java.net.URL;


public enum Service {
    PRODUCTION("https://rpc.gandi.net/xmlrpc/"),
    STAGING("https://rpc.ote.gandi.net/xmlrpc/");

    private final URL url;

    Service(final String s) {

        try {
            url = new URL(s);
        } catch (final MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public URL getUrl() {
        return url;
    }
}
