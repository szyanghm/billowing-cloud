package com.red.dust.billowing.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BillowingJob  {

    @Scheduled(cron = "0/3 * * * * ?")
    public void test(){
        log.info("执行定时任务1");
    }

//    @Override
//    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        log.info("执行定时任务2");
//    }
}
