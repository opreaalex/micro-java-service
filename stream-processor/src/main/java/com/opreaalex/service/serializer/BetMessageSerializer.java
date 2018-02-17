package com.opreaalex.service.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.opreaalex.domain.BetMessage;

import java.io.IOException;

public class BetMessageSerializer extends StdSerializer<BetMessage> {

    public BetMessageSerializer() {
        this(null);
    }

    private BetMessageSerializer(final Class<BetMessage> clazz) {
        super(clazz);
    }

    @Override
    public void serialize(final BetMessage msg,
                          final JsonGenerator generator,
                          final SerializerProvider provider)
            throws IOException {
        generator.writeStartObject();

        writeHeader(msg, generator);
        switch (msg.getType()) {
            case EVENT:
                writeEventBody(msg, generator);
                break;
            case MARKET:
                writeMarketBody(msg, generator);
                break;
            case OUTCOME:
                writeOutcomeBody(msg, generator);
                break;
        }
        writeFooter(msg, generator);

        generator.writeEndObject();
    }

    private void writeHeader(final BetMessage msg,
                             final JsonGenerator gen)
            throws IOException {
        gen.writeNumberField("id", msg.getId().longValue());
        gen.writeStringField("operation", msg.getOperation()
                .name().toLowerCase());
        gen.writeStringField("type", msg.getType()
                .name().toLowerCase());
        gen.writeNumberField("timestamp", msg.getTimestamp()
                .toEpochMilli());
    }

    private void writeEventBody(final BetMessage msg,
                                final JsonGenerator gen)
            throws IOException {
        gen.writeStringField("eventId", msg.getEventId());
        gen.writeStringField("category", msg.getCategory());
        gen.writeStringField("subCategory", msg.getSubCategory());
        gen.writeStringField("name", msg.getName());
        gen.writeNumberField("startTime", msg.getStartTime()
                .toEpochMilli());
    }

    private void writeMarketBody(final BetMessage msg, final JsonGenerator gen)
            throws IOException {
        gen.writeStringField("eventId", msg.getEventId());
        gen.writeStringField("marketId", msg.getMarketId());
        gen.writeStringField("name", msg.getName());
    }

    private void writeOutcomeBody(final BetMessage msg, final JsonGenerator gen)
            throws IOException {
        gen.writeStringField("marketId", msg.getMarketId());
        gen.writeStringField("outcomeId", msg.getOutcomeId());
        gen.writeStringField("name", msg.getName());
        gen.writeStringField("price", msg.getPrice());
    }

    // So called "footer"
    private void writeFooter(final BetMessage msg, final JsonGenerator gen)
            throws IOException {
        gen.writeBooleanField("displayed", msg.isDisplayed());
        gen.writeBooleanField("suspended", msg.isSuspended());
    }
}
