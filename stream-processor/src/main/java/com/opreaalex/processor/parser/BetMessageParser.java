package com.opreaalex.processor.parser;

import com.opreaalex.processor.domain.BetMessage;
import com.opreaalex.processor.domain.BetMessageOperation;
import com.opreaalex.processor.domain.BetMessageType;
import com.opreaalex.processor.parser.exception.StreamLineParserException;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class BetMessageParser implements StreamLineParser {

    // Length of all encountered messages
    private static final int TOKENS_LEN_EVENT = 11;
    private static final int TOKENS_LEN_MARKET = 9;
    private static final int TOKENS_LEN_OUTCOME = 10;

    private static final List<Integer> ALLOWED_LENGTHS = Arrays.asList(
            TOKENS_LEN_EVENT,
            TOKENS_LEN_MARKET,
            TOKENS_LEN_OUTCOME);

    // Header indexes
    private static final int IDX_ID = 0;
    private static final int IDX_OPERATION = 1;
    private static final int IDX_TYPE = 2;
    private static final int IDX_INSTANT = 3;
    // Event indexes
    private static final int IDX_EVENT_ID = 4;
    private static final int IDX_EVENT_CATEGORY = 5;
    private static final int IDX_EVENT_SUB_CATEGORY = 6;
    private static final int IDX_EVENT_NAME = 7;
    private static final int IDX_EVENT_START_TIME = 8;
    private static final int IDX_EVENT_DISPLAYED = 9;
    private static final int IDX_EVENT_SUSPENDED = 10;
    // Market indexes
    private static final int IDX_MARKET_EVENT_ID = 4;
    private static final int IDX_MARKET_ID = 5;
    private static final int IDX_MARKET_NAME = 6;
    private static final int IDX_MARKET_DISPLAYED = 7;
    private static final int IDX_MARKET_SUSPENDED = 8;
    // Outcome indexes
    private static final int IDX_OUTCOME_MARKET_ID = 4;
    private static final int IDX_OUTCOME_ID = 5;
    private static final int IDX_OUTCOME_NAME = 6;
    private static final int IDX_OUTCOME_PRICE = 7;
    private static final int IDX_OUTCOME_DISPLAYED = 8;
    private static final int IDX_OUTCOME_SUSPENDED = 9;


    // Regex for splitting the line into tokens
    private static final String REGEX_TOKENS = "(?<!\\\\)" + Pattern.quote("|");

    // Regex patterns to use for validation
    private static final String REGEX_ID = "^(0|[1-9][0-9]*)$";
    private static final String REGEX_OPERATION = "^(create|update)$";
    private static final String REGEX_TYPE = "^(event|market|outcome*)$";
    private static final String REGEX_INSTANT = "^(0|[1-9][0-9]*)$";
    private static final String REGEX_BOOLEAN = "^(1|0*)$";

    @Override
    public BetMessage parseLine(final String line)
            throws StreamLineParserException {
        final String[] tokens = readTokens(line);

        final BetMessage msg = new BetMessage();
        loadHeader(tokens, msg);

        switch (msg.getType()) {
            case EVENT:
                loadEventBody(tokens, msg);
                break;
            case MARKET:
                loadMarketBody(tokens, msg);
                break;
            case OUTCOME:
                loadOutcomeBody(tokens, msg);
                break;
        }

        return msg;
    }

    private void loadHeader(final String[] tokens, final BetMessage msg)
            throws StreamLineParserException {
        msg.setId(readId(tokens[IDX_ID]));
        msg.setOperation(readOperation(tokens[IDX_OPERATION]));
        msg.setType(readType(tokens[IDX_TYPE]));
        msg.setTimestamp(readInstant(
                tokens[IDX_INSTANT], "Invalid timestamp"));
    }

    private void loadEventBody(final String[] tokens, final BetMessage msg)
            throws StreamLineParserException {
        msg.setEventId(tokens[IDX_EVENT_ID]);
        msg.setCategory(tokens[IDX_EVENT_CATEGORY]);
        msg.setSubCategory(tokens[IDX_EVENT_SUB_CATEGORY]);
        msg.setStartTime(readInstant(
                tokens[IDX_EVENT_START_TIME], "Invalid start time"));
        msg.setName(tokens[IDX_EVENT_NAME]);
        msg.setDisplayed(readBoolean(
                tokens[IDX_EVENT_DISPLAYED], "Invalid displayed"));
        msg.setSuspended(readBoolean(
                tokens[IDX_EVENT_SUSPENDED], "Invalid suspended"));
    }

    private void loadMarketBody(final String[] tokens, final BetMessage msg)
            throws StreamLineParserException {
        msg.setEventId(tokens[IDX_MARKET_EVENT_ID]);
        msg.setMarketId(tokens[IDX_MARKET_ID]);
        msg.setName(tokens[IDX_MARKET_NAME]);
        msg.setDisplayed(readBoolean(
                tokens[IDX_MARKET_DISPLAYED], "Invalid displayed"));
        msg.setSuspended(readBoolean(
                tokens[IDX_MARKET_SUSPENDED], "Invalid suspended"));
    }

    private void loadOutcomeBody(final String[] tokens, final BetMessage msg)
            throws StreamLineParserException {
        msg.setMarketId(tokens[IDX_OUTCOME_MARKET_ID]);
        msg.setOutcomeId(tokens[IDX_OUTCOME_ID]);
        msg.setPrice(tokens[IDX_OUTCOME_PRICE]);
        msg.setName(tokens[IDX_OUTCOME_NAME]);
        msg.setDisplayed(readBoolean(
                tokens[IDX_OUTCOME_DISPLAYED], "Invalid displayed"));
        msg.setSuspended(readBoolean(
                tokens[IDX_OUTCOME_SUSPENDED], "Invalid suspended"));
    }

    private String[] readTokens(final String line)
            throws StreamLineParserException {
        final String[] rawTokens = line.split(REGEX_TOKENS);
        // Exclude the first token, which we always expect to be empty
        final String[] tokens = Arrays.copyOfRange(
                rawTokens, 1, rawTokens.length);

        if (!ALLOWED_LENGTHS.contains(tokens.length)) {
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

    private BetMessageType readType(final String tokenVal)
            throws StreamLineParserException {
        if (!tokenVal.matches(REGEX_TYPE)) {
            throw new StreamLineParserException("Invalid type");
        }

        return BetMessageType.valueOf(tokenVal.toUpperCase());
    }

    private Instant readInstant(final String tokenVal, final String errMsg)
            throws StreamLineParserException {
        if (!tokenVal.matches(REGEX_INSTANT)) {
            throw new StreamLineParserException(errMsg);
        }

        return Instant.ofEpochMilli(Long.valueOf(tokenVal));
    }

    private boolean readBoolean(final String tokenVal, final String errMsg)
            throws StreamLineParserException {
        if (!tokenVal.matches(REGEX_BOOLEAN)) {
            throw new StreamLineParserException(errMsg);
        }

        return (Integer.valueOf(tokenVal) == 1);
    }
}
