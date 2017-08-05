package ncm.monitor;

import ncm.model.NCMAllModel;
import ncm.model.NCMWeekModel;
import ncm.service.NCMAllService;
import ncm.util.NCMUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wenxiangzhou214164 on 2017/8/3.
 */
@Service
public class NCMAllMonitor extends NCMMonitor<NCMAllModel> {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private NCMAllService ncmService;

    @Autowired
    public NCMAllMonitor(NCMAllService ncmSerivce) {
        this.ncmService = ncmSerivce;
    }

    @Value(value = "${schedule.rate}")
    private int period;

    @Override
    public void getContent(Date date) {
        refresh();
        for (int i = 0; i < retry && (!ready || driver == null); i++) {
            ready = loginAndJump(false);
        }
        if (!ready) {
            logger.info("[getContent] login jump failed");
        }
        ts = new ArrayList<>();
        WebElement iframe = waitForElement(By.cssSelector("iframe#g_iframe"));
        if (iframe == null) {
            driver = null;
            ready = false;
        } else {
            driver.switchTo().frame(iframe);
            WebElement songsallBtn = waitForElement(By.cssSelector("span#songsall"));//所有时间
            if (songsallBtn != null) {
                songsallBtn.click();
                WebElement wrap = waitForElement(By.cssSelector("div#m-record"));
                if (wrap != null) {
                    String song, singer = null;
                    Long songId, singerId = null;
                    Double width = null;
                    List<WebElement> parents = waitForElements(wrap, By.cssSelector("span.txt"));
                    List<WebElement> widths = waitForElements(wrap, By.cssSelector("span.bg"));
                    for (int i = 0; i < parents.size(); i++) {
                        WebElement a = waitForElement(parents.get(i), By.cssSelector("span>a"));
                        if (a == null) {
                            logger.info("[getContent] rank:{}, a == null", i + 1);
                            continue;
                        }
                        String href = a.getAttribute("href");
                        songId = Long.valueOf(href.split("=")[1]);
                        WebElement b = waitForElement(a, By.cssSelector("b"));
                        if (b == null) {
                            logger.info("[getContent] rank:{}, song == null while a != null", i + 1);
                            continue;
                        }
                        song = b.getText().trim();
                        logger.info("[getContent] rank:{}, song:{}", i + 1, song);

                        if (widths.get(i) == null) {
                            logger.info("[getContent] rank:{}, width == null", i + 1);
                        } else {
                            String style = widths.get(i).getAttribute("style").split(":")[1].replaceAll("[^0-9]", "");
                            width = Double.parseDouble(style);
                        }

                        WebElement singerEle = waitForElement(parents.get(i), By.cssSelector("span[title]"));
                        if (singerEle == null) {
                            logger.info("[getContent] rank:{}, singer == null", i + 1);
                        } else {
                            singer = singerEle.getAttribute("title");
                            WebElement singerIdEle = waitForElement(singerEle, By.cssSelector("a"));
                            if (singerIdEle == null) {
                                logger.info("[getContent] rank:{}, singerId == null", i + 1);
                            } else {
                                singerId = Long.valueOf(singerIdEle.getAttribute("href").split("=")[1]);
                            }
                        }
                        NCMAllModel ncmModel = new NCMAllModel();
                        ncmModel.setSong(song);
                        ncmModel.setSongId(songId);
                        ncmModel.setSinger(singer);
                        ncmModel.setSingerId(singerId);
                        ncmModel.setWidth(width);
                        ncmModel.setRank(i + 1);
                        ncmModel.setDate(date);
                        ts.add(ncmModel);
                        logger.info("[getContent] rank:{}, {}", i + 1, ncmModel);
                    }
                }
            }
        }
    }

    @Override
    protected void handle(Date date) {
        setAllCalls(date);
        Date lastDate = new Date(date.getTime() - period);
        List<NCMAllModel> ncmModelList = ncmService.findAllByDate(lastDate);
        for (NCMAllModel ncmModel:
                ts) {
            long songId = ncmModel.getSongId();
            for (NCMAllModel lastNCMModel:
                    ncmModelList) {
                if (songId == lastNCMModel.getSongId()) {
                    ncmModel.setIncre(ncmModel.getTimes() - lastNCMModel.getTimes());
                    break;
                }
            }
        }
        ncmService.insert(ts);
        logger.info("[getContent] ncmModelList:{}", ts);
        if (ts.size() < 30) {
            logger.info("[getContent] reopen browser while ncmModelList.size:{}", ts.size());
            driver = null;
            ready = false;
        }
    }

    public void setAllCalls(Date date) {
        Date lastDate = new Date(date.getTime() - period);
        if (ts == null || ts.size() == 0) {
            return;
        }
        double minWidth = 100.0;
        int size =ts.size();
        for (int i = 0; i < size - 1; i++) {
            double gap = ts.get(i).getWidth() - ts.get(i + 1).getWidth();
            if (gap > Double.MIN_VALUE && gap < minWidth) {
                minWidth = gap;
            }
        }
        minWidth = minWidth < ts.get(size - 1).getWidth() ? minWidth : ts.get(size - 1).getWidth();
        for (NCMAllModel ncmAllModel:
                ts) {
            int times = (int) Math.round(ncmAllModel.getWidth() / minWidth);
            ncmAllModel.setTimes(times);
            ncmAllModel.setIncre(times);
        }
        List<NCMAllModel> lastNCMModelList = ncmService.findAllByDate(lastDate);
        double weight = 1.0;
        if (lastNCMModelList != null) {
            for (NCMAllModel ncmModel:
                    ts) {
                long songId = ncmModel.getSongId();
                for (NCMAllModel lastNCMModel:
                        lastNCMModelList) {
                    if (songId == lastNCMModel.getSongId()) {
                        if (ncmModel.getTimes() * weight < lastNCMModel.getTimes()) {
                            weight += 0.1;
                        }
                    }
                }
            }
        }
        for (NCMAllModel ncmModel:
             ts) {
            ncmModel.settimes((int) Math.round(ncmModel.getTimes() * weight));
        }
    }

}
