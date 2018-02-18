package com.opreaalex.processor.domain;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Objects;

public class BetMessage {

    // Header
    private BigInteger id;
    private BetMessageOperation operation;
    private BetMessageType type;
    private Instant timestamp;

    // Body
    private String eventId;
    private String marketId;
    private String outcomeId;
    private String category;
    private String subCategory;
    private Instant startTime;
    private String price;

    // Common
    private String name;
    private boolean displayed;
    private boolean suspended;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BetMessageOperation getOperation() {
        return operation;
    }

    public void setOperation(BetMessageOperation operation) {
        this.operation = operation;
    }

    public BetMessageType getType() {
        return type;
    }

    public void setType(BetMessageType type) {
        this.type = type;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

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

    public String getOutcomeId() {
        return outcomeId;
    }

    public void setOutcomeId(String outcomeId) {
        this.outcomeId = outcomeId;
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

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BetMessage that = (BetMessage) o;

        return displayed == that.displayed &&
                suspended == that.suspended &&
                Objects.equals(id, that.id) &&
                operation == that.operation &&
                type == that.type &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(eventId, that.eventId) &&
                Objects.equals(marketId, that.marketId) &&
                Objects.equals(outcomeId, that.outcomeId) &&
                Objects.equals(category, that.category) &&
                Objects.equals(subCategory, that.subCategory) &&
                Objects.equals(name, that.name) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, operation, type, timestamp,
                eventId, marketId, outcomeId,
                category, subCategory, startTime, price,
                name, displayed, suspended);
    }

    @Override
    public String toString() {
        return "BetMessage{" +
                "id=" + id +
                ", operation=" + operation +
                ", type=" + type +
                ", timestamp=" + timestamp +
                ", eventId='" + eventId + '\'' +
                ", marketId='" + marketId + '\'' +
                ", outcomeId='" + outcomeId + '\'' +
                ", category='" + category + '\'' +
                ", subCategory='" + subCategory + '\'' +
                ", startTime='" + startTime + '\'' +
                ", price='" + price + '\'' +
                ", name='" + name + '\'' +
                ", displayed=" + displayed +
                ", suspended=" + suspended +
                '}';
    }
}
