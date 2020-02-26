package com.app.services.dataGenerator;

import com.app.model.Movie;

import java.util.List;

public class MovieStoresJsonConverter extends JsonConverter<List<Movie>> {
    public MovieStoresJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
