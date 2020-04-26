package com.sefonsoft.oa.system.quartz;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.sefonsoft.oa.service.project.ProjectInfoService;
import com.sefonsoft.oa.service.sys.SysEnvService;
import com.sefonsoft.oa.system.utils.DateUtils;

/**
 * 清理逾期项目，清理规则
 * <li>所有已审批的项目
 * <li>个人项目，逾期六个月，进入大区资源池
 * <li>大区项目，三天无人认领，进入公司资源池
 */
@DisallowConcurrentExecution
public class ProjectGCJob extends QuartzJobBean {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProjectGCJob.class);

  ProjectInfoService projectInfoService;
  
  SysEnvService sysEnvService;

  public ProjectGCJob(ProjectInfoService projectInfoService, SysEnvService sysEnvService) {
    this.projectInfoService = projectInfoService;
    this.sysEnvService = sysEnvService;
  }
  
  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    
    LocalDateTime dt = DateUtils.toLocalDateTime(context.getScheduledFireTime())
        .toLocalDate().atTime(LocalTime.MIN);
    
    // 释放项目
    List<Map<String, String>> releasedProjects = projectInfoService.gcOverdueProject(dt);
    if (!releasedProjects.isEmpty()) {
      LOGGER.info("检测到 {} 个逾期项目", releasedProjects.size());
    }
    for(Map<String, String> item : releasedProjects) {
      LOGGER.info("清理项目： {} ", item);
    }
  }
}
