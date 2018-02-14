package com.opreaalex.parser;

import com.opreaalex.domain.*;
import com.opreaalex.parser.exception.StreamLineParserException;

import java.math.BigInteger;
import java.time.Instant;
import java.util.regex.Pattern;

public class BetMessageParser implements StreamLineParser {

    private static final int MIN_NUM_TOKENS = 6;

    private static final int INDEX_ID = 1;
    private static final int INDEX_OPERATION = 2;
    private static final int INDEX_TYPE = 3;
    private static final int INDEX_INSTANT = 4;

    private static final String TYPE_EVENT = "event";
    private static final String TYPE_MARKET = "market";
    private static final String TYPE_OUTCOME = "outcome";

    private static final String REGEX_LINE = "(?<!\\\\)" + Pattern.quote("|");

    private static final String REGEX_ID = "^(0|[1-9][0-9]*)$";
    private static final String REGEX_OPERATION = "(create|update)";
    private static final String REGEX_TYPE = "(event|market|outcome)";
    private static final String REGEX_INSTANT = "^(0|[1-9][0-9]*)$";

    public BaseBetMessage parseLine(final String line)
            throws StreamLineParserException {
        final String[] tokens = readTokens(line);

        final BetMessageHeader header = new BetMessageHeader();
        header.setId(readId(tokens[INDEX_ID]));
        header.setOperation(readOperation(tokens[INDEX_OPERATION]));
        header.setInstant(readInstant(tokens[INDEX_INSTANT]));

        final BaseBetMessage message;
        final String type = readType(tokens[INDEX_TYPE]);
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

    private EventBetMessage parseEventBetMessage(final String[] tokens) {
        final EventBetMessage message = new EventBetMessage();
        // TODO Implement
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

    private BigInteger readId(final String tokenVal)
            throws StreamLineParserException {
        if (!tokenVal.matches(REGEX_ID)) {
            throw new StreamLineParserException("Invalid id");
        }

        return new BigInteger(tokenVal);
    }

    private BetMessageOperation readOperation(final String tokenVal)
            throws StreamLineParserException {
        if (!tokenVal.matches(REGEX_OPERATION)) {
            throw new StreamLineParserException("Invalid operation");
        }

        return BetMessageOperation.valueOf(tokenVal.toUpperCase());
    }

    private String readType(final String tokenVal)
            throws StreamLineParserException {
        if (!tokenVal.matches(REGEX_TYPE)) {
            throw new StreamLineParserException("Invalid type");
        }

        return tokenVal;
    }

    private Instant readInstant(final String tokenVal)
            throws StreamLineParserException {
        if (!tokenVal.matches(REGEX_INSTANT)) {
            throw new StreamLineParserException("Invalid instant");
        }

        return Instant.ofEpochMilli(Long.valueOf(tokenVal));
    }
}
