package br.com.livestream.service;

import br.com.livestream.model.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Created by wallace on 02/01/2018.
 */
public class ScheduleService implements Runnable {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    final int[] count = {0};
    private final JmsTemplate jmsTemplate;
    private final Record record;

    public ScheduleService(Record record, JmsTemplate jmsTemplate) {
        this.record = record;
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void run() {
        for (int i = 0; i < this.record.getLoop(); i++) {
            log.info("Schedule Loop[ --> " + i + "] Thread --> " + Thread.currentThread().getName());
            try {
                this.jmsTemplate.convertAndSend("record", record);
                TimeUnit.MINUTES.sleep(this.record.getMinutes() + 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
