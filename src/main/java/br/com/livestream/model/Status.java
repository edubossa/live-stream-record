package br.com.livestream.model;

/**
 * Created by wallace on 12/12/2017.
 */
public enum Status {

    Success("successful recording"),
    Error ("recording not performed successfully");

    private String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
