package com.opreaalex.common.domain.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;

public class InstantDeserializer extends JsonDeserializer<Instant> {

    @Override
    public Instant deserialize(final JsonParser jsonParser,
                               final DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        return Instant.ofEpochMilli(jsonParser.getLongValue());
    }
}
