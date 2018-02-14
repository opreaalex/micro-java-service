package com.opreaalex.domain;

import java.util.Objects;

public class BaseBetMessage {

    private BetMessageHeader header;

    public BetMessageHeader getHeader() {
        return header;
    }

    public void setHeader(BetMessageHeader header) {
        this.header = header;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BaseBetMessage message = (BaseBetMessage) o;

        return Objects.equals(header, message.header);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header);
    }
}
