package br.com.livestream.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by wallace on 05/12/2017.
 */
@DynamoDBTable(tableName = "Record")
public class Record {

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    private String id;

    @DynamoDBAttribute
    private String bucket;

    @DynamoDBAttribute
    private int minutes;

    @DynamoDBAttribute
    private int loop;

    @DynamoDBAttribute
    public String name;

    //@DynamoDBTypeConverted(converter = GenreJSONConverter.class)
    @DynamoDBAttribute
    public String genre;

    @DynamoDBAttribute
    public String url;

    @DynamoDBTypeConverted(converter = TypeJSONConverter.class)
    public Type type;

    @DynamoDBAttribute
    public List<Schedule> schedules = new ArrayList<>();

    @DynamoDBIgnore
    public String uuid;

    @DynamoDBIgnore
    private File file;

    public Record() {
    }

    public Record(String name, String genre, String url, Type type) {
        this.name = name;
        this.genre = genre;
        this.type = type;
        this.url = url;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getLoop() {
        return loop;
    }

    public void setLoop(int loop) {
        this.loop = loop;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void createFile() throws IOException {
        this.uuid = UUID.randomUUID().toString();
        this.file = File.createTempFile(this.uuid, "." + type.name());
        System.out.println("File name --> " + file.getName());
        System.out.println(file.getAbsolutePath());
        file.deleteOnExit();
    }

    @Override
    public String toString() {
        return "Radio{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genre=" + genre +
                ", type=" + type +
                ", url='" + url + '\'' +
                ", schedules=" + schedules +
                ", uuid='" + uuid + '\'' +
                '}';
    }

}