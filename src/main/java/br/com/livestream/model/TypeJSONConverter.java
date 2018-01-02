package br.com.livestream.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

/**
 * Created by wallace on 27/12/2017.
 */
public class TypeJSONConverter implements DynamoDBTypeConverter<String, Type> {

    @Override
    public String convert(Type type) {
        return type.name();
    }

    @Override
    public Type unconvert(String type) {
        return Type.getType(type);
    }
}
