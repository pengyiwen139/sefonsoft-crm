package com.sefonsoft.oa.system.utils;

import com.sefonsoft.oa.controller.importexport.ExportProjectController;
import com.sefonsoft.oa.service.project.ProjectInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.time.LocalDateTime;

// @Configuration      //1.主要用于标记配置类，兼备Component的效果。
// @EnableScheduling   // 2.开启定时任务
// 2020-03-03 17:51   新需求，不再删除逾期的立项项目，而是将其移动到大区或者公司资源池
public class SaticScheduleTask {
    private static final Logger logger = LoggerFactory.getLogger(SaticScheduleTask.class);
    @Resource
    private ProjectInfoService projectInfoService;
    //3.添加定时任务
//    @Scheduled(cron = "5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    @Scheduled(fixedRate=5000)
    private void configureTasks() {
        logger.info("执行离职过期项目定时任务开始时间: " + LocalDateTime.now());
        // 删除过期项目
        // projectInfoService.projectTerminatedOverdueListTask();
        logger.info("执行离职过期项目任务完成时间: " + LocalDateTime.now());
    }
}