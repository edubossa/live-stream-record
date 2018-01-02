package br.com.livestream;

import br.com.livestream.model.*;
import br.com.livestream.repository.RecordRepository;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LiveStreamRecordApplicationTests {

	@Autowired
	RecordRepository recordRepository;

	@Test
	public void contextLoads() {


		try {

			Record record = new Record();
			record.setBucket("live-stream-genre");
			record.setLoop(3);
			record.setMinutes(60);
			record.setName("Radio FM do Povo - 94.3 FM");
			record.setGenre(Genre.BossaNova.name());
			record.setType(Type.mp3);
			record.setUrl("http://streaming13.hstbr.net:8280/live");

			Schedule schedule = new Schedule();
			schedule.setDhInitial(LocalDateTime.now().toString());
			schedule.setDhFinal(LocalDateTime.now().toString());
			schedule.setLink("https://s3-sa-east-1.amazonaws.com/live-stream-genre/bossa-nova/44b47d34-637f-4c18-b638-9141e29420b7.mp3");

			record.getSchedules().add(schedule);
			record.getSchedules().add(schedule);
			record.getSchedules().add(schedule);

			Record save = this.recordRepository.save(record);
			System.out.println("Record ID --> " + save.getId());
			Assert.assertNotNull(save.getId());

		} catch (DynamoDBMappingException e) {
			Assert.fail(e.getMessage());
			//e.printStackTrace();
		}


	}

}
