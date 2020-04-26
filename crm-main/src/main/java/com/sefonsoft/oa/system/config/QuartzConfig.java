package com.sefonsoft.oa.system.config;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.ScheduleBuilder;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sefonsoft.oa.service.sys.SysEnvService;
import com.sefonsoft.oa.system.quartz.ProjectGCJob;

@Configuration
public class QuartzConfig {
  
  @Autowired
  SysEnvService sysEnv;
  
  @Bean
  public JobDetail projectGCJob() {
    return JobBuilder.newJob(ProjectGCJob.class)
        .storeDurably()
        .build();
  }

  @Bean
  public Trigger projectGCJobTrigger() {
    
    ScheduleBuilder<?> tt = null;
    
    if ("true".equalsIgnoreCase(System.getProperty("projectGCJob.debug"))) {
      tt = SimpleScheduleBuilder.repeatSecondlyForever(5);
    } else {
      tt = CronScheduleBuilder.cronSchedule(sysEnv.project_gc_cron.getValue());
    }
    
    return TriggerBuilder.newTrigger()
        .forJob(projectGCJob())
        .withSchedule(tt)
        .build();
  }
  
}
