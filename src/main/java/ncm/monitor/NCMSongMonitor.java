package ncm.monitor;

import ncm.model.NCMCommentModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NCMSongMonitor extends NCMMonitor<NCMCommentModel>{
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final String prefix = "http://music.163.com/#/song?id=";

    private List<NCMCommentModel> ncmModelList;
    private List<String> songIds;

    public NCMSongMonitor(List<String> songIds) {
        this.songIds = songIds;
    }

    @Override
    protected void getContent(Date date) {
        int all = 0;
        for (String songId:
             songIds) {
            url = prefix + songId;
            logger.info("[getContent] NO.{} of {}url:{}", ++all, songIds.size(), url);
            refresh();
            for (int i = 0; i < retry && (!ready || driver == null); i++) {
                ready = loginAndJump(false);
            }
            if (!ready) {
                logger.info("[getContent] login jump failed");
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ncmModelList = new ArrayList<>();
            WebElement iframe = waitForElement(By.cssSelector("iframe#g_iframe"));
            if (iframe == null) {
                driver = null;
                ready = false;
            } else {
                driver.switchTo().frame(iframe);
                List<WebElement> cntWraps = waitForElements(By.cssSelector("div.cntwrap"));
                int count = 0;
                if (cntWraps != null) {
                    for (WebElement cntWrap :
                            cntWraps) {
                        logger.info("[getContent] NO.{} of {}url:{}, line:{} of all:{}",  all, songIds.size(), url, ++count, cntWraps.size());
                        WebElement line = waitForElement(cntWrap, By.cssSelector("div.cnt.f-brk"));
                        WebElement a = waitForElement(line, By.cssSelector("a"));
                        String userId = a.getAttribute("href").split("=")[1];
                        if (userId.equals(user)) {
                            NCMCommentModel ncmModel = new NCMCommentModel();
                            WebElement upstairs = waitForElement(cntWrap, By.cssSelector("div.que.f-brk.f-pr.s-fc3"), 3 * 1000);
                            if (upstairs != null) {
                                WebElement aUp = waitForElement(upstairs, By.cssSelector("a"));
                                String userIdUp = aUp.getAttribute("href").split("=")[1];
                                String userNamUp = aUp.getText();
                                String commentUp = upstairs.getText();
                                ncmModel.setOri(commentUp);
                                ncmModel.setOriUserId(Long.valueOf(userIdUp));
                                ncmModel.setOriUserName(userNamUp);
                            }
                            String userName = a.getText();
                            String comment = line.getText();
                            String timeStr = waitForElement(cntWrap, By.cssSelector("div.time.s-fc4")).getText();
                            int praize = 0;
                            try {
                                praize = Integer.parseInt(waitForElement(cntWrap, By.cssSelector("a[data-type=\"like\"]")).getText().replaceAll("[^0-9]", ""));
                            } catch (NumberFormatException e) {
                            }
                            ncmModel.setName(userName);
                            ncmModel.setSongId(Long.valueOf(url.split("=")[1]));
                            ncmModel.setComment(comment);
                            ncmModel.setDate(calDate(timeStr));
                            ncmModel.setPraise(praize);
                            logger.info("[getContent] line:{}, {}", count, ncmModel);
                            ncmModelList.add(ncmModel);
                        } else {
                            WebElement upstairs = waitForElement(cntWrap, By.cssSelector("div.que.f-brk.f-pr.s-fc3"), 3 * 1000);
                            if (upstairs != null) {
                                WebElement aUp = waitForElement(upstairs, By.cssSelector("a"));
                                String userIdUp = aUp.getAttribute("href").split("=")[1];
                                if (userIdUp.equals(user)) {
                                    NCMCommentModel ncmModel = new NCMCommentModel();
                                    String userNamUp = aUp.getText();
                                    String commentUp = upstairs.getText();
                                    ncmModel.setComment(commentUp);
                                    ncmModel.setName(userNamUp);
                                    ncmModel.setReplyUserId(Long.valueOf(userId));
                                    String userName = a.getText();
                                    String comment = line.getText();
                                    String timeStr = waitForElement(cntWrap, By.cssSelector("div.time.s-fc4")).getText();
                                    int praize = 0;
                                    try {
                                        praize = Integer.parseInt(waitForElement(cntWrap, By.cssSelector("a[data-type=\"like\"]")).getText().replaceAll("[^0-9]", ""));
                                    } catch (NumberFormatException e) {
                                    }
                                    ncmModel.setReply(comment);
                                    ncmModel.setPraise(praize);
                                    ncmModel.setDate(calDate(timeStr));
                                    ncmModel.setSongId(Long.valueOf(url.split("=")[1]));
                                    ncmModel.setReplyUserName(userName);
                                    logger.info("[getContent] line:{}, {}", count, ncmModel);
                                    ncmModelList.add(ncmModel);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void handle(Date date) {
        logger.info("[handle] size:{} ncmModelList:{}", ncmModelList.size(), ncmModelList);
    }

    private Date calDate(String dateStr) {
        if (dateStr.contains("年")) {
            String year = dateStr.split("年")[0];
            String month = dateStr.split("年")[1].split("月")[0];
            String date = dateStr.split("月")[1].split("日")[0];
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, Integer.parseInt(year));
            cal.set(Calendar.MONTH, Integer.parseInt(month));
            cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date));
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            return cal.getTime();
        }
        else if (dateStr.contains("月")) {
            String month = dateStr.split("月")[0];
            String date = dateStr.split("月")[1].split("日")[0];
            String hour = dateStr.split("日")[1].split(":")[0].replaceAll("[^0-9]", "");
            String minute = dateStr.split(":")[1];
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MONTH, Integer.parseInt(month));
            cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date));
            cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
            cal.set(Calendar.MINUTE, Integer.parseInt(minute));
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            return cal.getTime();
        }
        else if (dateStr.contains("分钟前")){
            String minAhead = dateStr.replaceAll("[^0-9]", "");
            return new Date(new Date().getTime() - Integer.parseInt(minAhead) * 60 * 1000);
        }
        else {
            return new Date();
        }
    }

    public static void main(String[] args) {
        NCMSongMonitor ncmSongMonitor = new NCMSongMonitor(new ArrayList<String>(){{add("437250607"); add("19107212");}});
        ncmSongMonitor.setUser("78364914");
        ncmSongMonitor.task(new Date());
    }
}
