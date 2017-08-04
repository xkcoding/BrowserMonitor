package ncm.task;

import ncm.service.NCMAllService;
import ncm.service.NCMWeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wenxiangzhou214164 on 2017/8/3.
 */
@Configuration
@EnableScheduling
public class NCMTask {
    private NCMWeekService ncmWeekService;
    private NCMAllService ncmAllService;

    @Autowired
    public NCMTask(NCMWeekService ncmWeekService, NCMAllService ncmAllService) {
        this.ncmWeekService = ncmWeekService;
        this.ncmAllService = ncmAllService;
    }

    @Scheduled(cron = "${schedule.cronTab}")
    public void task() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                ncmWeekService.task();
            }
        });
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                ncmAllService.task();
            }
        });
    }
}
