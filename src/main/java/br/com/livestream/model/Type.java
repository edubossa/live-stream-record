package br.com.livestream.model;

/**
 * Created by wallace on 05/12/2017.
 */
public enum Type {

    mp3, aac;

    public static Type getType(String type) {
        for (Type t: values()) {
            if (t.name().equals(type)) return t;
        }
        return null;
    }

}
