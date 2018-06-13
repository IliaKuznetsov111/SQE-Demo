package com.ikuznietsov.enums.query.parameters;

public enum NasaQueryParameters {
    FILTER("q", "search text to filter results"),
    LIMIT("limit", "number of tracks to return"),
    API_KEY("api_key", "api.nasa.gov key for expanded usage");

    private final String parameter;
    private final String description;

    NasaQueryParameters(String parameter, String description) {
        this.parameter = parameter;
        this.description = description;
    }

    public String getParameter() {
        return parameter;
    }

    public String getDescription() {
        return description;
    }
}