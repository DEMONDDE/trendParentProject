package cn.config;

import cn.util.IndexDataSyncJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 胡建德
 */
@Configuration
public class QuartzConfiguration {

    private static final int interval = 1;

    @Bean
    public JobDetail weatherDataSyncJobDetail() {
        return JobBuilder.newJob(IndexDataSyncJob.class).withIdentity("indexDataSyncJob")
                .storeDurably().build();
    }

    @Bean
    public Trigger weatherDataSyncTrigger() {
        SimpleScheduleBuilder schedBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMinutes(interval).repeatForever();

        return TriggerBuilder.newTrigger().forJob(weatherDataSyncJobDetail())
                .withIdentity("indexDataSyncTrigger").withSchedule(schedBuilder).build();
    }
}
