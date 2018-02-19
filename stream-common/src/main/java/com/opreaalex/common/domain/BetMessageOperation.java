package com.opreaalex.common.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum BetMessageOperation {

    CREATE,
    UPDATE;

    @JsonCreator
    public static BetMessageOperation fromString(final String key) {
        return (key != null) ?
                BetMessageOperation.valueOf(key.toUpperCase()) : null;
    }
}
