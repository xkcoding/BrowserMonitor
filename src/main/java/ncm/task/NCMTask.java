package ncm.task;

import ncm.monitor.NCMAllMonitor;
import ncm.monitor.NCMWeekMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wenxiangzhou214164 on 2017/8/3.
 */
@Configuration
@EnableScheduling
public class NCMTask {
    private NCMWeekMonitor ncmWeekMonitor;
    private NCMAllMonitor ncmAllMonitor;

    @Value(value = "${schedule.rate}")
    private int period;

    @Autowired
    public NCMTask(NCMWeekMonitor ncmWeekMonitor, NCMAllMonitor ncmAllMonitor) {
        this.ncmWeekMonitor = ncmWeekMonitor;
        this.ncmAllMonitor = ncmAllMonitor;
    }

    @Scheduled(cron = "${schedule.cronTab}")
    public void task() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                ncmWeekMonitor.task(new Date(new Date().getTime() / period * period));
            }
        });
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                ncmAllMonitor.task(new Date(new Date().getTime() / period * period));
            }
        });
    }
}
