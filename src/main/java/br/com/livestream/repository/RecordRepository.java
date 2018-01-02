package br.com.livestream.repository;

import br.com.livestream.model.Record;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by wallace on 22/12/2017.
 */
@EnableScan
public interface RecordRepository extends CrudRepository<Record, String> {

    List<Record> findByName(String name);

    List<Record> findByGenre(String genre);

}
