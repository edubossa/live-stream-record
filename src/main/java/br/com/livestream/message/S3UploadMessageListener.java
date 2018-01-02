package br.com.livestream.message;

import br.com.livestream.model.Genre;
import br.com.livestream.model.Record;
import br.com.livestream.model.Status;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Created by wallace on 22/12/2017.
 */
@Component
public class S3UploadMessageListener {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AmazonS3 amazonS3;

    @JmsListener(destination = "upload-s3", containerFactory = "myFactory", concurrency = "20-50")
    public void receiveMessage(Record record) {
        log.info("upload-s3 receive --> " + record);
        try {
            amazonS3.putObject(record.getBucket(),
                    Genre.getGenre(record.getGenre()).getDescription() + "/" + record.getUuid() + "." + record.getType().name(), record.getFile());

        } catch (AmazonS3Exception e) {
            record.setStatus(Status.Error.name());
            record.setMessage(e.getMessage());
            log.error("Erro ao fazer upload para o amazonS3 --> " + record.getName() + " MSG: " + e.getMessage());
            e.printStackTrace();
        }

    }

}