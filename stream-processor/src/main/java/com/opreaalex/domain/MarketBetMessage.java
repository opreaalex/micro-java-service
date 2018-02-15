package com.opreaalex.domain;

import java.util.Objects;

public class MarketBetMessage extends BetMessage {

    private String eventId;
    private String marketId;

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
        final MarketBetMessage message = (MarketBetMessage) o;

        return Objects.equals(eventId, message.eventId) &&
                Objects.equals(marketId, message.marketId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), eventId, marketId);
    }
}
