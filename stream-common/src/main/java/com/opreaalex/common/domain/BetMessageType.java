package com.opreaalex.common.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum BetMessageType {

    EVENT,
    MARKET,
    OUTCOME;

    @JsonCreator
    public static BetMessageType fromString(final String key) {
        return (key != null) ?
                BetMessageType.valueOf(key.toUpperCase()) : null;
    }
}
