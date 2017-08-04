package ncm.service;

import ncm.model.NCMAllModel;
import ncm.util.NCMUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wenxiangzhou214164 on 2017/8/3.
 */
@Service
public class NCMAllService extends NCMService<NCMAllModel> {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value(value = "${schedule.rate}")
    private int period;

    @Override
    public void getContent() {
        ts = new ArrayList<>();
        WebElement iframe = waitForElement(By.cssSelector("iframe#g_iframe"));
        if (iframe == null) {
            m_pDriver = null;
            ready = false;
        } else {
            m_pDriver.switchTo().frame(iframe);
            WebElement songsallBtn = waitForElement(By.cssSelector("span#songsall"));
            if (songsallBtn != null) {
                songsallBtn.click();
                WebElement wrap = waitForElement(By.cssSelector("div#m-record"));
                if (wrap != null) {
                    String song, singer = null;
                    Long songId, singerId = null;
                    Double width = null;
                    List<WebElement> parents = waitForElements(wrap, By.cssSelector("span.txt"));
                    List<WebElement> widths = waitForElements(wrap, By.cssSelector("span.bg"));
                    Date date = new Date(new Date().getTime() / period * period);
                    for (int i = 0; i < parents.size(); i++) {
                        WebElement a = waitForElement(parents.get(i), By.cssSelector("span>a"));
                        if (a == null) {
                            logger.info("[getRecord] rank:{}, a == null", i + 1);
                            continue;
                        }
                        String href = a.getAttribute("href");
                        songId = Long.valueOf(href.split("=")[1]);
                        logger.info("[getRecord] rank:{}, songId:{}", i + 1, songId);
                        WebElement b = waitForElement(a, By.cssSelector("b"));
                        if (b == null) {
                            logger.info("[getRecord] rank:{}, song == null while a != null", i + 1);
                            continue;
                        }
                        song = b.getText().trim();
                        logger.info("[getRecord] rank:{}, song:{}", i + 1, song);

                        if (widths.get(i) == null) {
                            logger.info("[getRecord] rank:{}, width == null", i + 1);
                        } else {
                            String style = widths.get(i).getAttribute("style");//获取attribute style
                            width = Double.valueOf(style.split(":")[1].split("%")[0]);
                            logger.info("[getRecord] rank:{}, width:{}", i + 1, width);
                        }

                        WebElement singerEle = waitForElement(parents.get(i), By.cssSelector("span[title]"));
                        if (singerEle == null) {
                            logger.info("[getRecord] rank:{}, singer == null", i + 1);
                        } else {
                            singer = singerEle.getAttribute("title");
                            logger.info("[getRecord] rank:{}, singer:{}", i + 1, singer);
                            WebElement singerIdEle = waitForElement(singerEle, By.cssSelector("a"));
                            if (singerIdEle == null) {
                                logger.info("[getRecord] rank:{}, singerId == null", i + 1);
                            } else {
                                singerId = Long.valueOf(singerIdEle.getAttribute("href").split("=")[1]);
                                logger.info("[getRecord] rank:{}, singerId:{}", i + 1, singerId);
                            }
                        }
                        NCMAllModel ncmAllModel = new NCMAllModel();
                        ncmAllModel.setSong(song);
                        ncmAllModel.setSongId(songId);
                        ncmAllModel.setSinger(singer);
                        ncmAllModel.setSingerId(singerId);
                        ncmAllModel.setWidth(width);
                        ncmAllModel.setRank(i + 1);
                        ncmAllModel.setDate(date);
                        ts.add(ncmAllModel);
                    }
                }
            }
        }
    }

    @Override
    public void handle() {
        NCMUtil.setAllCalls(ts);
        logger.info("[handle] ncmModelList:{}", ts);
        if (ts.size() < 30) {
            logger.info("[handle] reopen browser while ncmModelList.size:{}", ts.size());
            m_pDriver = null;
            ready = false;
        }
    }

    public static void main(String[] args) throws Exception {
        NCMService ncmService = new NCMAllService();
        while (true) {
            ncmService.task();
            Thread.sleep(10000);
        }
    }
}
