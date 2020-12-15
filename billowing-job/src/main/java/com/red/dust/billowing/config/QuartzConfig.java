//package com.red.dust.billowing.config;
//
//
//import com.red.dust.billowing.job.BillowingJob;
//import org.quartz.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class QuartzConfig {
//
//    private static final int TIME = 2;
//
//    @Bean
//    public JobDetail syncJobDetail(){
//        return JobBuilder.newJob(BillowingJob.class).withIdentity("billowingJob").storeDurably().build();
//    }
//
//    @Bean
//    public Trigger syncTrigger(){
//        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(TIME).repeatForever();
//        return TriggerBuilder.newTrigger().forJob(syncJobDetail()).withIdentity("syncTrigger").withSchedule(scheduleBuilder).build();
//    }
//}
