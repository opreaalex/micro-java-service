package com.opreaalex.domain;

import java.util.Objects;

public class MarketBetMessage extends BetMessage {

    private String eventId;
    private String marketId;
    private String name;
    private boolean displayed;
    private boolean suspended;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        final MarketBetMessage message = (MarketBetMessage) o;

        return displayed == message.displayed &&
                suspended == message.suspended &&
                Objects.equals(eventId, message.eventId) &&
                Objects.equals(marketId, message.marketId) &&
                Objects.equals(name, message.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), eventId, marketId, name, displayed, suspended);
    }
}
