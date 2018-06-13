package com.ikuznietsov.enums.properties;

import static com.google.common.base.Preconditions.checkNotNull;

public enum NasaProperties {
    NASA_URL("nasa.url"),
    NASA_API_KEY("nasa.api.key");

    private final String property;

    NasaProperties(String property) {
        this.property = checkNotNull(System.getProperty(property), "failed on initializing Nasa properties");
    }

    public String getValue() {
        return property;
    }
}
