package ncm.dao;

import ncm.model.NCMAllModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface NCMAllRepository extends CrudRepository<NCMAllModel, Integer> {

    List<NCMAllModel> findAllByDate(Date date);

    NCMAllModel findBySongIdAndDate(Long songId, Date date);
}
