package com.sefonsoft.oa.service.sys;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.quartz.CronExpression;
import org.springframework.stereotype.Service;

import com.sefonsoft.oa.dao.sys.SysEnvDao;
import com.sefonsoft.oa.entity.sys.SysEnvEntity;

@Service
public class SysEnvService {
  
  private final Map<String, EnvValue<?>> registry = new LinkedHashMap<>();
  private final SysEnvDao sysEnvDao;

  /**
   * 每日项目领取限额
   */
  public final EnvValue<Integer>  sales_daily_acquire_project_max = intValue(
    "sales_daily_acquire_project_max",
     "每日项目领取限额",
     "1"
  );
  
  // ~~ 逾期检测 ~~
  /**
   * 自动清理逾期项目
   */
  public final EnvValue<Boolean> project_gc_enable = boolValue(
    "project_gc_enable",
    "自动清理逾期项目",
    "false"
  );
  /**
   * 逾期任务检测周期，使用 CRON 表达式
   */
  public final EnvValue<String> project_gc_cron = cronValue(
    "project_gc_cron",
    "逾期任务检测周期，使用 CRON 表达式",
    "0 1 0 * * ? *"
  );
  /**
   * 逾期任务提醒，单位天
   */
  public final EnvValue<Integer> project_gc_sales_warning_day = intValue(
    "project_gc_sales_warning_day",
    "逾期任务提醒，单位天",
    "7"
  );
  /**
   * 个人任务逾期，单位月
   */
  public final EnvValue<Integer> project_gc_sales_limit_month = intValue(
    "project_gc_sales_limit_month",
    "个人任务逾期，单位月",
    "6"
  );
  /**
   * 大区资源池项目逾期，单位天
   */
  public final EnvValue<Integer> project_gc_depts_limit_day = intValue(
    "project_gc_depts_limit_day",
    "大区资源池项目逾期，单位天",
    "3"
  );
  
  public SysEnvService(SysEnvDao sysEnvDao) {
    this.sysEnvDao = sysEnvDao;
  }
  
  public void setEnv(String name, String value) {
    EnvValue<?> cvt = registry.get(name);
    if (cvt == null) {
      throw new IllegalArgumentException("未注册的系统参数");
    }
    cvt.setValue(value);
  }
  
  public List<SysEnvEntity> getAll() {
    return registry.values().stream().map((v) -> {
      // sync db
      v.getValue();
      
      SysEnvEntity see = new SysEnvEntity();
      see.setConfigDes(v.desc);
      see.setConfigName(v.name);
      see.setConfigValue(v.value);
      see.setDefaultValue(v.defaultValue);
      return see;
    }).collect(Collectors.toList());
  }
  
  /**
   * 声明一个 Integer 变量
   * @param name env name
   * @param desc env description
   * @param defaultValue env default value
   * @return EnvValue
   */
  EnvValue<Integer> intValue(String name, String desc, String defaultValue) {
    return register(name, Integer::parseInt, desc, defaultValue);
  }
  /**
   * 声明一个 Boolean 变量
   * @param name env name
   * @param desc env description
   * @param defaultValue env default value
   * @return EnvValue
   */
  EnvValue<Boolean> boolValue(String name, String desc, String defaultValue) {
    return register(name, Boolean::new, desc, defaultValue);
  }
  
  /**
   * 声明一个 CRON 变量
   * @param name env name
   * @param desc env description
   * @param defaultValue env default value
   * @return EnvValue
   */
  EnvValue<String> cronValue(String name, String desc, String defaultValue) {
    return register(name, sv -> {
      try {
        return new CronExpression(sv).toString();
      } catch (ParseException e) {
        throw new IllegalArgumentException("错误的 cron 表达式");
      }
    }, desc, defaultValue) ;
  }
  
  private <T> EnvValue<T> register(String name, Function<String, T> converter, String desc, String defaultValue) {
    
    if (registry.containsKey(name)) {
      throw new IllegalArgumentException(name + "已注册");
    }
    EnvValue<T> env = new EnvValue<>(name, converter, desc, defaultValue);
    registry.put(name, env);
    return env;
  }

  
  private static final Object NONE = new Object();
  
  public class EnvValue<T> {
    
    private final String name;
    private final String desc;
    private final String defaultValue;
    private final Function<String, T> converter;
    
    private String value;
    private Object cv = NONE;

    public EnvValue(String name, Function<String, T> converter, String desc, String defaultValue) {
      this.name = name;
      this.desc = desc;
      this.defaultValue = defaultValue;
      this.converter = converter;
      this.converter.apply(this.defaultValue);
    }

    synchronized void setValue(String sv) {
      Object tmp = sv == null ? null : this.converter.apply(sv);
      if (sysEnvDao.update(name, sv) ||
          sysEnvDao.insertIngore(name, sv, defaultValue, desc)) {
        this.cv = tmp;
        this.value = sv;
      }
    }
    
    @SuppressWarnings("unchecked")
    public synchronized T getValue() {
      if (cv == NONE) {
        Map<String, String> dbv = sysEnvDao.getConfig(name);
        value = dbv == null ? defaultValue : dbv.get("config_value");
        cv = value == null ? null : converter.apply(value);
      }
      return (T) cv;
    }

    public String getDescription() {
      return desc;
    }
  }
}