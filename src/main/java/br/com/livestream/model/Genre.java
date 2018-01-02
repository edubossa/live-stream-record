package br.com.livestream.model;

/**
 * Created by wallace on 08/12/2017.
 */
public enum Genre {

    Jazz("jazz"),
    BlackGospel("black-gospel"),
    Fusion("fusion"),
    BossaNova("bossa-nova"),
    MPB("mpb");

    private String description;

    Genre(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Genre getGenre(String genre) {
        for (Genre g: values()) {
            if (g.name().equals(genre)) return g;
        }
        return null;
    }

}
