package br.com.livestream.message;

import br.com.livestream.model.Genre;
import br.com.livestream.model.Record;
import br.com.livestream.model.Schedule;
import br.com.livestream.model.Status;
import br.com.livestream.repository.RecordRepository;
import com.amazonaws.services.s3.internal.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wallace on 22/12/2017.
 */
@Component
public class RecordMessageListener {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${link.s3}")
    private String linkS3;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private RecordRepository repository;


    @JmsListener(destination = "record", containerFactory = "myFactory", concurrency = "20-50")
    public void receiveMessage(Record record) {

        List<Record> records = this.repository.findByName(record.getName());
        Schedule schedule = null;
        if (records.size() > 0) {
            Record r = records.get(0);
            record.setId(r.getId());
            record.setSchedules(r.getSchedules());
        }

        final int[] time = {0};
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                log.info("Gravando " + record.getName() + " --> " + time[0] + " Minutos");
                time[0]++;
            }
        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 1000*60);
        //timer.scheduleAtFixedRate(timerTask, 0, 1000);

        try {
            schedule = new Schedule();
            schedule.setDhInitial(LocalDateTime.now().toString());

            InputStream is = new URL(record.getUrl()).openStream();;
            log.info("Gravando ..." + record.toString());
            record.createFile();
            OutputStream os = new FileOutputStream(record.getFile());
            byte[] buffer = new byte[1 * Constants.MB];
            int bytesRead;
                //read from is to buffer
                while((bytesRead = is.read(buffer)) !=-1){
                    os.write(buffer, 0, bytesRead);
                    if (time[0] == record.getMinutes()) {
                        time[0] = 0;
                        log.warn("Cancelando o timer");
                        timer.cancel();
                        //Record to AWS
                        log.info("::::::::::::::: UPLOAD S3 :::::::::::::::");
                        schedule.setDhFinal(LocalDateTime.now().toString());
                        schedule.setLink(linkS3 + record.getBucket() + "/" + Genre.getGenre(record.getGenre()).getDescription()
                                + "/" + record.getUuid() + "." + record.getType().name());
                        record.getSchedules().add(schedule);
                        jmsTemplate.convertAndSend("upload-s3", record);
                        break;
                    }
                }

            is.close();
            os.flush();
            os.close();
            log.info(":::::::::::::::" + record.getName() + " Processo Finalizado :::::::::::::::");

        } catch (Exception e) {
            timer.cancel();
            schedule.setStatus(Status.Error.name());
            schedule.setMessage(e.getMessage());
            log.error("Erro gravacao Stream --> " + record.getName() + " MSG: " + e.getMessage());
            e.printStackTrace();

        } finally {
            this.jmsTemplate.convertAndSend("dynamo-db", record);
        }

    }

}