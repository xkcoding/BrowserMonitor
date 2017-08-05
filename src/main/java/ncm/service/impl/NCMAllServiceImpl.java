package ncm.service.impl;

import ncm.dao.NCMAllRepository;
import ncm.model.NCMAllModel;
import ncm.service.NCMAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class NCMAllServiceImpl implements NCMAllService{

    private NCMAllRepository ncmRepository;

    @Autowired
    public NCMAllServiceImpl(NCMAllRepository ncmRepository) {
        this.ncmRepository = ncmRepository;
    }

    @Override
    public void insert(NCMAllModel ncmModel) {
        ncmRepository.save(ncmModel);
    }

    @Override
    public void insert(List<NCMAllModel> ncmModelList) {
        ncmRepository.save(ncmModelList);
    }

    @Override
    public List<NCMAllModel> findAllByDate(Date date) {
        return ncmRepository.findAllByDate(date);
    }

    @Override
    public NCMAllModel findBySongIdAndDate(Long songId, Date date) {
        return ncmRepository.findBySongIdAndDate(songId, date);
    }
}
