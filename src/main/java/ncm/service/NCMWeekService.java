package ncm.service;

import ncm.model.NCMWeekModel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

public interface NCMWeekService {

    @RequestMapping(value = "/week/save", method = RequestMethod.GET)
    void insert(@RequestBody NCMWeekModel ncmModel);

    @RequestMapping(value = "/week/saves", method = RequestMethod.GET)
    void insert(@RequestBody List<NCMWeekModel> ncmModelList);

    @RequestMapping(value = "/week/at", method = RequestMethod.GET)
    List<NCMWeekModel> findAllByDate(@RequestParam("date") Date date);

    @RequestMapping(value = "/week/song/at", method = RequestMethod.GET)
    NCMWeekModel findBySongIdAndDate(@RequestParam("songId") Long songId, @RequestParam("date") Date date);

}
