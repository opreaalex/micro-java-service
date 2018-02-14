package com.opreaalex.domain;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Objects;

public class BetMessageHeader {

    private BigInteger id;
    private BetMessageOperation operation;
    private Instant instant;

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

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BetMessageHeader message = (BetMessageHeader) o;

        return Objects.equals(id, message.id) &&
                operation == message.operation &&
                Objects.equals(instant, message.instant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, operation, instant);
    }
}
