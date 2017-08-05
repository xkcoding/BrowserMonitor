package ncm.service;

import ncm.model.NCMAllModel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

public interface NCMAllService {

    @RequestMapping(value = "/all/save", method = RequestMethod.GET)
    void insert(@RequestBody NCMAllModel ncmModel);
    
    @RequestMapping(value = "/all/saves", method = RequestMethod.GET)
    void insert(@RequestBody List<NCMAllModel> ncmModelList);

    @RequestMapping(value = "/all/at", method = RequestMethod.GET)
    List<NCMAllModel> findAllByDate(@RequestParam("date") Date date);

    @RequestMapping(value = "/all/song/at", method = RequestMethod.GET)
    NCMAllModel findBySongIdAndDate(@RequestParam("songId") Long songId, @RequestParam("date") Date date);

}