package br.com.livestream.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

/**
 * Created by wallace on 13/12/2017.
 */
@DynamoDBDocument
public class Schedule {

    @DynamoDBAttribute(attributeName = "dh_initial")
    private String dhInitial;

    @DynamoDBAttribute(attributeName = "dh_final")
    private String dhFinal;

    @DynamoDBAttribute
    private String link;

    @DynamoDBAttribute
    public String status;

    @DynamoDBAttribute
    public String message;

    public Schedule() {
        this.status = Status.Success.name();
        this.message = Status.Success.getDescription();
    }

    public Schedule(String dhInitial) {
        this.dhInitial = dhInitial;
    }

    public String getDhInitial() {
        return dhInitial;
    }

    public void setDhInitial(String dhInitial) {
        this.dhInitial = dhInitial;
    }

    public String getDhFinal() {
        return dhFinal;
    }

    public void setDhFinal(String dhFinal) {
        this.dhFinal = dhFinal;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "dhInitial='" + dhInitial + '\'' +
                ", dhFinal='" + dhFinal + '\'' +
                ", link='" + link + '\'' +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

}