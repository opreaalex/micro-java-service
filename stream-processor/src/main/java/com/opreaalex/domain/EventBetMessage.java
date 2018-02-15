package com.opreaalex.domain;

import java.time.Instant;
import java.util.Objects;

public class EventBetMessage extends BetMessage {

    private String eventId;
    private String category;
    private String subCategory;
    private String name;
    private Instant startInstant;
    private boolean displayed;
    private boolean suspended;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getStartInstant() {
        return startInstant;
    }

    public void setStartInstant(Instant startInstant) {
        this.startInstant = startInstant;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    @Override
    public boolean equals(final Object o) {
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

        return displayed == message.displayed &&
                suspended == message.suspended &&
                Objects.equals(eventId, message.eventId) &&
                Objects.equals(category, message.category) &&
                Objects.equals(subCategory, message.subCategory) &&
                Objects.equals(name, message.name) &&
                Objects.equals(startInstant, message.startInstant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), eventId, category, subCategory,
                name, startInstant, displayed, suspended);
    }
}
