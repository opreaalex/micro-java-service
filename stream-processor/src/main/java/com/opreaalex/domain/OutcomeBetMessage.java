package com.opreaalex.domain;

import java.util.Objects;

public class OutcomeBetMessage extends BetMessage {

    private String marketId;
    private String outcomeId;
    private String price;

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getOutcomeId() {
        return outcomeId;
    }

    public void setOutcomeId(String outcomeId) {
        this.outcomeId = outcomeId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
        final OutcomeBetMessage message = (OutcomeBetMessage) o;

        return Objects.equals(marketId, message.marketId) &&
                Objects.equals(outcomeId, message.outcomeId) &&
                Objects.equals(price, message.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), marketId, outcomeId, price);
    }
}
