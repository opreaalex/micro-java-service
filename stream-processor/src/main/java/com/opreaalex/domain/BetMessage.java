package com.opreaalex.domain;

import java.util.Objects;

public class BetMessage {

    private BetMessageHeader header;
    private String name;
    private boolean displayed;
    private boolean suspended;

    public BetMessageHeader getHeader() {
        return header;
    }

    public void setHeader(BetMessageHeader header) {
        this.header = header;
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
                Objects.equals(header, that.header) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, name, displayed, suspended);
    }
}
