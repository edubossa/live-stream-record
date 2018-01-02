package br.com.livestream.message;

import br.com.livestream.model.Record;
import br.com.livestream.repository.RecordRepository;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Created by wallace on 22/12/2017.
 *
 * http://www.baeldung.com/spring-data-dynamodb
 * https://github.com/michaellavelle/spring-data-dynamodb
 */
@Component
public class DynamoDBMessageListener {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RecordRepository repository;

    @JmsListener(destination = "dynamo-db", containerFactory = "myFactory", concurrency = "20-50")
    public void receiveMessage(Record record) {
        log.info("dynamo-db receive --> " + record);

        try {

            this.repository.save(record);

        } catch (DynamoDBMappingException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

    }

}