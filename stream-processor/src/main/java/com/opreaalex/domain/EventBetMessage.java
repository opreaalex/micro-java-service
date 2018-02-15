package com.opreaalex.domain;

import java.time.Instant;
import java.util.Objects;

public class EventBetMessage extends BetMessage {

    private String eventId;
    private String category;
    private String subCategory;
    private Instant startInstant;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public Instant getStartInstant() {
        return startInstant;
    }

    public void setStartInstant(Instant startInstant) {
        this.startInstant = startInstant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        final EventBetMessage message = (EventBetMessage) o;

        return Objects.equals(eventId, message.eventId) &&
                Objects.equals(category, message.category) &&
                Objects.equals(subCategory, message.subCategory) &&
                Objects.equals(startInstant, message.startInstant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), eventId, category, subCategory, startInstant);
    }
}
