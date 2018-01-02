package br.com.livestream.api;

import br.com.livestream.model.Record;
import br.com.livestream.repository.RecordRepository;
import br.com.livestream.service.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wallace on 21/12/2017.
 */
@RestController
public class LiveStreamController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RecordRepository repository;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @RequestMapping(value = "/live_streams", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Record>> findAll(@RequestParam(value = "name", defaultValue = "") String name,
                                                @RequestParam(value = "genre", defaultValue = "") String genre) {
        log.info("GET /live_streams?name=" + name + "&genre=" + genre);
        List<Record> records = new ArrayList<>();
        if (!StringUtils.isEmpty(name)) {
            records.addAll(this.repository.findByName(name));
        } else if (!StringUtils.isEmpty(genre)) {
            records.addAll(this.repository.findByGenre(genre));
        } else {
            this.repository.findAll().forEach(record -> {
                records.add(record);
            });
        }
        return new ResponseEntity<List<Record>>(records, HttpStatus.OK);
    }

    @RequestMapping(value = "/live_streams/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Record> findById(@PathVariable("id") String id) {
        log.info("GET /live_streams/" +  id);
        Record record = this.repository.findOne(id);
        return (record != null) ? new ResponseEntity<Record>(record, HttpStatus.OK) : new ResponseEntity<Record>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/live_streams", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@RequestBody Record record) {
        log.info("POST /live_streams | RequestBody --> " +  record);
        this.taskExecutor.execute(new ScheduleService(record, this.jmsTemplate));
    }

}