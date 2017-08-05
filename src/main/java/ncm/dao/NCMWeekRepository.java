package ncm.dao;

import ncm.model.NCMWeekModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface NCMWeekRepository extends CrudRepository<NCMWeekModel, Integer>{

    List<NCMWeekModel> findAllByDate(Date date);

    NCMWeekModel findBySongIdAndDate(Long songId, Date date);
}
