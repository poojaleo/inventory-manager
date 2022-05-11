package com.shopify.inventoryservice.dynamodb.tableconverters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import java.util.HashMap;
import java.util.Map;

public class ShipmentMapTypeConverter implements DynamoDBTypeConverter<String, Map<String, Integer>> {
    @Override
    public String convert(Map<String, Integer> object) {
        return Joiner.on(",").withKeyValueSeparator("=").join(object);
    }

    @Override
    public Map<String, Integer> unconvert(String object) {
        Map<String, String> stringMap = Splitter.on(',').withKeyValueSeparator('=').split(object);
        Map<String, Integer> integerMap = new HashMap<>();

        for(Map.Entry<String,String> entry : stringMap.entrySet()) {
            integerMap.put(entry.getKey(), Integer.parseInt(entry.getValue()));
        }

        return integerMap;
    }
}
