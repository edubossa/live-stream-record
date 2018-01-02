package br.com.livestream.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

/**
 * Created by wallace on 23/12/2017.
 */
public class GenreJSONConverter implements DynamoDBTypeConverter<String, Genre> {

    @Override
    public String convert(Genre genre) {
        return genre.name();
    }

    @Override
    public Genre unconvert(String genre) {
        return Genre.getGenre(genre);
    }
}
