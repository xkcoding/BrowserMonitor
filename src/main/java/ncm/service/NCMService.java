package ncm.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static ncm.constant.NCMConstant.weekUrl;


/**
 * Created by wenxiangzhou214164 on 2017/7/25.
 */
public abstract class NCMService<T> {
    private Logger logger = LoggerFactory.getLogger(getClass());

    protected List<T> ts;
    protected WebDriver m_pDriver = null;
    protected boolean ready = false;

    public void task() {
        while (!ready || m_pDriver == null) {
            ready = loginAndJump();
        }
        refresh();
        getContent();
        handle();
    }

    protected boolean loginAndJump() {
        if (m_pDriver != null) {
            m_pDriver.close();
        }
        if (null == m_pDriver) {
            m_pDriver = makeDriver();
            logger.info("[loginAndJump] open browser");
        }
        if (m_pDriver == null) {
            logger.info("[loginAndJump] make driver failed");
            return false;
        }
        m_pDriver.navigate().to(weekUrl);
        return true;
    }

    protected void refresh() {
        ts = null;
        try {
            m_pDriver.navigate().refresh();
            logger.info("[refresh] refresh");
            Thread.sleep(3000);
        } catch (Exception e) {
            logger.info("[refresh] reopen browser while refresh failed");
            m_pDriver = null;
            ready = false;
        }
    }

    protected abstract void getContent();

    protected abstract void handle();

    public WebDriver makeDriver() {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setEnableNativeEvents(true);
        profile.setPreference("intl.charset.default", "UTF-8");
//        profile.setPreference("general.useragent.override", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:2.0b9pre) Gecko/20101228 Firefox/4.0b9pre");
        String path = NCMService.class.getResource("/geckodriverAll/win64/geckodriver.exe").getPath();
        System.setProperty("webdriver.gecko.driver", path);
        WebDriver webDriver = new FirefoxDriver(profile);
        return webDriver;
    }

    public WebElement waitForElement(WebElement we, By by) {
        return waitForElement(we, by, 1 * 1000);
    }

    public WebElement waitForElement(By by) {
        return waitForElement(by, 1 * 1000);
    }

    public WebElement waitForElement(By by, long lTimeout) {
        long lStart = System.currentTimeMillis();
        while (System.currentTimeMillis() - lStart < lTimeout) {
            try {
                WebElement pElemenet = m_pDriver.findElement(by);
                if (null != pElemenet) {
                    return pElemenet;
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e) {
            }
        }
        logger.info("[waitForElement] waitForElement {} timeout.", by.toString());
        return null;
    }

    public WebElement waitForElement(WebElement we, By by, long lTimeout) {
        long lStart = System.currentTimeMillis();
        while (System.currentTimeMillis() - lStart < lTimeout) {
            try {
                WebElement pElemenet = we.findElement(by);
                if (null != pElemenet) {
                    return pElemenet;
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e) {
            }
        }
        logger.info("[waitForElement] waitForElement {} timeout.", by.toString());
        return null;
    }

    public List<WebElement> waitForElements(WebElement we, By by) {
        return waitForElements(we, by, 10 * 1000);
    }

    public List<WebElement> waitForElements(WebElement we, By by, long lTimeout) {
        long lStart = System.currentTimeMillis();
        while (System.currentTimeMillis() - lStart < lTimeout) {
            try {
                List<WebElement> pElemenets = we.findElements(by);
                if (null != pElemenets) {
                    return pElemenets;
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e) {
            }
        }
        logger.info("[waitForElements] waitForElements {} timeout.", by.toString());
        return null;
    }

}
