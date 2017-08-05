package ncm.service.impl;

import ncm.dao.NCMWeekRepository;
import ncm.model.NCMWeekModel;
import ncm.service.NCMWeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class NCMWeekServiceImpl implements NCMWeekService {
    private NCMWeekRepository ncmRepository;

    @Autowired
    public NCMWeekServiceImpl(NCMWeekRepository ncmRepository) {
        this.ncmRepository = ncmRepository;
    }

    @Override
    public void insert(NCMWeekModel ncmModel) {
        ncmRepository.save(ncmModel);
    }

    @Override
    public void insert(List<NCMWeekModel> ncmModelList) {
        ncmRepository.save(ncmModelList);
    }

    @Override
    public List<NCMWeekModel> findAllByDate(Date date) {
        return ncmRepository.findAllByDate(date);
    }

    @Override
    public NCMWeekModel findBySongIdAndDate(Long songId, Date date) {
        return ncmRepository.findBySongIdAndDate(songId, date);
    }
}
