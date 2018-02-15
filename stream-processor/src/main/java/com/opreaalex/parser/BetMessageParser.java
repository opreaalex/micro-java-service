package com.opreaalex.parser;

import com.opreaalex.domain.*;
import com.opreaalex.parser.exception.StreamLineParserException;

import java.math.BigInteger;
import java.time.Instant;
import java.util.regex.Pattern;

public class BetMessageParser implements StreamLineParser {

    private static final int MIN_NUM_TOKENS = 6;

    // Header indexes
    private static final int IDX_ID = 1;
    private static final int IDX_OPERATION = 2;
    private static final int IDX_TYPE = 3;
    private static final int IDX_INSTANT = 4;
    // Event indexes
    private static final int IDX_EVENT_ID = 5;
    private static final int IDX_CATEGORY = 6;
    private static final int IDX_SUB_CATEGORY = 7;
    private static final int IDX_NAME = 8;
    private static final int IDX_START_INSTANT = 9;
    private static final int IDX_DISPLAYED = 10;
    private static final int IDX_SUSPENDED = 11;

    // Event types
    private static final String TYPE_EVENT = "event";
    private static final String TYPE_MARKET = "market";
    private static final String TYPE_OUTCOME = "outcome";

    // Regex for splitting line into tokens
    private static final String REGEX_LINE = "(?<!\\\\)" + Pattern.quote("|");

    // Regex patterns to use for validation
    private static final String REGEX_ID = "^(0|[1-9][0-9]*)$";
    private static final String REGEX_OPERATION = "^(create|update)$";
    private static final String REGEX_TYPE = "^(event|market|outcome*)$";
    private static final String REGEX_INSTANT = "^(0|[1-9][0-9]*)$";
    private static final String REGEX_BOOLEAN = "^(1|0*)$";

    public BetMessage parseLine(final String line)
            throws StreamLineParserException {
        final String[] tokens = readTokens(line);

        final BetMessageHeader header = new BetMessageHeader();
        header.setId(readBigInteger(
                tokens[IDX_ID], REGEX_ID, "Invalid id"));
        header.setOperation(readOperation(
                tokens[IDX_OPERATION], REGEX_OPERATION));
        header.setInstant(readInstant(
                tokens[IDX_INSTANT], REGEX_INSTANT, "Invalid instant"));

        final BetMessage message;
        final String type = readString(
                tokens[IDX_TYPE], REGEX_TYPE, "Invalid type");
        if (TYPE_EVENT.equals(type)) {
            message = parseEventBetMessage(tokens);
            message.setHeader(header);
        } else if (TYPE_MARKET.equals(type)) {
            message = parseMarketBetMessage(tokens);
            message.setHeader(header);
        } else if (TYPE_OUTCOME.equals(type)) {
            message = parseOutcomeBetMessage(tokens);
            message.setHeader(header);
        } else {
            throw new IllegalStateException("Unsupported type");
        }

        return message;
    }

    private EventBetMessage parseEventBetMessage(final String[] tokens)
            throws StreamLineParserException {
        final EventBetMessage message = new EventBetMessage();
        message.setEventId(readString(
                tokens[IDX_EVENT_ID], null, null));
        message.setCategory(readString(
                tokens[IDX_CATEGORY], null, null));
        message.setSubCategory(readString(
                tokens[IDX_SUB_CATEGORY], null, null));
        message.setName(readString(
                tokens[IDX_NAME], null, null));
        message.setStartInstant(readInstant(
                tokens[IDX_START_INSTANT], REGEX_INSTANT, "Invalid start instant"));
        message.setDisplayed(readBoolean(
                tokens[IDX_DISPLAYED], "Invalid displayed"));
        message.setSuspended(readBoolean(
                tokens[IDX_SUSPENDED], "Invalid suspended"));

        return message;
    }

    private MarketBetMessage parseMarketBetMessage(final String[] tokens) {
        final MarketBetMessage message = new MarketBetMessage();
        // TODO Implement
        return message;
    }

    private OutcomeBetMessage parseOutcomeBetMessage(final String[] tokens) {
        final OutcomeBetMessage message = new OutcomeBetMessage();
        // TODO Implement
        return message;
    }

    private String[] readTokens(final String line)
            throws StreamLineParserException {
        final String[] tokens = line.split(REGEX_LINE);
        if (tokens.length < MIN_NUM_TOKENS) {
            throw new StreamLineParserException("Invalid message format");
        }

        return tokens;
    }

    private BigInteger readBigInteger(final String tokenVal,
                                      final String regex,
                                      final String errMsg)
            throws StreamLineParserException {
        if (regex != null && !tokenVal.matches(REGEX_ID)) {
            throw new StreamLineParserException(errMsg);
        }

        return new BigInteger(tokenVal);
    }

    private BetMessageOperation readOperation(final String tokenVal,
                                              final String regex)
            throws StreamLineParserException {
        if (regex != null && !tokenVal.matches(REGEX_OPERATION)) {
            throw new StreamLineParserException("Invalid operation");
        }

        return BetMessageOperation.valueOf(tokenVal.toUpperCase());
    }

    private String readString(final String tokenVal,
                              final String regex,
                              final String errMsg)
            throws StreamLineParserException {
        if (regex != null && !tokenVal.matches(regex)) {
            throw new StreamLineParserException(errMsg);
        }

        return tokenVal;
    }

    private Instant readInstant(final String tokenVal,
                                final String regex,
                                final String errMsg)
            throws StreamLineParserException {
        if (regex != null && !tokenVal.matches(REGEX_INSTANT)) {
            throw new StreamLineParserException(errMsg);
        }

        return Instant.ofEpochMilli(Long.valueOf(tokenVal));
    }

    private boolean readBoolean(final String tokenVal,
                                final String errMsg)
            throws StreamLineParserException {
        if (!tokenVal.matches(REGEX_BOOLEAN)) {
            throw new StreamLineParserException(errMsg);
        }

        return (Integer.valueOf(tokenVal) == 1);
    }
}
