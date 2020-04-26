package com.sefonsoft.oa.controller.common;


import com.sefonsoft.oa.service.OpLogsService;
import com.sefonsoft.oa.system.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志
 *
 * @author xielf
 */
@RestController
@Api(tags = "--------操作日志")
public class OpLogController extends BaseController {


  private OpLogsService opLogsService;


  @Autowired
  public void setOpLogsService(OpLogsService opLogsService) {
    this.opLogsService = opLogsService;
  }

  /**
   * 日志列表
   *
   * @param pageNo
   * @param pageSize
   * @return
   */
  @ApiOperation("日志")
  @GetMapping("opLogList")
  public Response opLogList(@RequestParam(defaultValue = "1") int pageNo,
                            @RequestParam(defaultValue = "10") int pageSize) {

    try {
      return success(opLogsService.getOpLogList(pageNo, pageSize));
    } catch (Exception e) {
      e.printStackTrace();
      return failure("查询日志出现错误");
    }
  }
}
