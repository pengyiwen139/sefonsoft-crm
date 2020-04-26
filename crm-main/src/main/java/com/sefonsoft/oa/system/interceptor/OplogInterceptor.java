package com.sefonsoft.oa.system.interceptor;


import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.OpLogs;
import com.sefonsoft.oa.service.OpLogsService;
import com.sefonsoft.oa.system.annotation.OpLog;
import com.sefonsoft.oa.system.exception.MsgException;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.response.enums.ResponseState;
import com.sefonsoft.oa.system.utils.CookieUtil;
import com.sefonsoft.oa.system.utils.ObjTool;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Date;
import java.util.List;

import static com.sefonsoft.oa.system.constant.UserConstant.USER_INFO;

/**
 * @author xielf
 */
@Component
@Aspect
public class OplogInterceptor {

  /**
   * 日志
   */
  private static final Logger LOG = LoggerFactory.getLogger(OplogInterceptor.class);

  /**
   * 数据库日志
   */
  private OpLogsService opLogsService;


  @Autowired
  public void setOpLogsService(OpLogsService opLogsService) {
    this.opLogsService = opLogsService;
  }


  /**
   * 切点
   */
  @Pointcut("@annotation(com.sefonsoft.oa.system.annotation.OpLog)")
  public void pointCut() {

  }

  /**
   * 方法前后切面
   *
   * @param point
   * @return
   * @throws Throwable
   */
  @Around("pointCut()")
  public Object recordLog(ProceedingJoinPoint point) throws Throwable {

    final MethodSignature signature = (MethodSignature) point.getSignature();
    OpLog opLog = signature.getMethod().getAnnotation(OpLog.class);
    Object obj = null;
    OpLogs opLogs = new OpLogs();
    opLogs.setOpTime(new Date());
    try {
      String moduleName = opLog.module().getModuleName();
      String opName = opLog.opType().getOpName();

      //获取httpservletrequest
      HttpServletRequest request = HttpRequestHolder.currentServletRequest();
      HttpSession session = request.getSession(false);

      String employeeId = "";

      if (ObjTool.isNotNull(request)) {
        String loginUserId = CookieUtil.getLoginUserIdByCookie(request);
        if (ObjTool.isNotNull(loginUserId, session)) {
          Object sessionAttribute = session.getAttribute(USER_INFO + loginUserId);
          if (ObjTool.isNotNull(sessionAttribute)
              && sessionAttribute instanceof LoginUserDTO) {
            employeeId = ((LoginUserDTO) sessionAttribute).getUserId();
          }
        }
      }
      opLogs.setEmployeeId(employeeId);
      opLogs.setOpModule(moduleName);
      StringBuilder b = new StringBuilder();
      Object[] args = point.getArgs();
      String[] argNames = signature.getParameterNames();
      for (int i = 0; i < signature.getParameterNames().length; i++) {
        b.append(String.format("%s : %s \n", argNames[i], args[i].toString()));
      }
      opLogs.setOpType(Short.valueOf(opLog.opType().getOpType().toString()));

      obj = point.proceed();
      if (obj instanceof Response) {
        Object resData = ((Response) obj).getData();

        if (resData instanceof List) {
          ((List) resData).forEach(v -> {
            switch (opLog.module()) {
              case CUSTOMER:
                break;
              case PROJECT:
                break;
              case BIZ_OPPORTS:
                break;
              case CUSTOMER_PROPERTIES:
                break;
              default:
                break;
            }
          });
        }
        if (((Response) obj).getMeta().getCode() != ResponseState.SUCCESS.getCode()) {
          opLogs.setOpResult(Short.valueOf("0"));
          opLogs.setOpDetail(((Response) obj).getMeta().getMessage() + " " + ((Response) obj).getData());
        }
        else{
          opLogs.setOpResult(Short.valueOf("1"));
          opLogs.setOpDetail(opName + "操作成功");
        }
      } else {
        opLogs.setOpResult(Short.valueOf("1"));
        opLogs.setOpDetail(opName + "操作成功");
      }
    } catch (MsgException e) {
      opLogs.setOpDetail(e.toString());
      opLogs.setOpResult(Short.valueOf("0"));
      throw e;
    } catch (Throwable e) {
      opLogs.setOpDetail(e.toString());
      opLogs.setOpResult(Short.valueOf("0"));
      e.printStackTrace();
      throw e;
    } finally {
      opLogsService.addOpLogs(opLogs);
      LOG.info("记录操作日志###");
    }
    return obj;
  }


}
