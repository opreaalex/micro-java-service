package com.opreaalex.archiver.store.mapper;

import com.opreaalex.archiver.util.MongoDbClient;
import com.opreaalex.common.domain.BetMessage;

import java.util.HashMap;
import java.util.Map;

public class BetMessageMapper implements MongoDbClient.CollectionMapper<BetMessage> {

    @Override
    public Map<String, Object> mapEntry(final BetMessage entry) {
        final Map<String, Object> map = new HashMap<>();
        map.put("id", entry.getId().intValue());
        map.put("operation", entry.getOperation().name().toLowerCase());
        map.put("type", entry.getType().name().toLowerCase());
        map.put("timestamp", entry.getTimestamp().toEpochMilli());

        switch (entry.getType()) {
            case EVENT:
                map.put("eventId", entry.getEventId());
                map.put("category", entry.getCategory());
                map.put("subCategory", entry.getSubCategory());
                map.put("startTime", entry.getStartTime().toEpochMilli());
                break;
            case MARKET:
                map.put("eventId", entry.getEventId());
                map.put("marketId", entry.getMarketId());
                break;
            case OUTCOME:
                map.put("marketId", entry.getMarketId());
                map.put("outcomeId", entry.getOutcomeId());
                map.put("price", entry.getPrice());
                break;
        }

        map.put("name", entry.getName());
        map.put("displayed", entry.isDisplayed());
        map.put("suspended", entry.isSuspended());

        return map;
    }
}
