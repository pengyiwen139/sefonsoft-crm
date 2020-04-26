package com.sefonsoft.oa.controller.common;


import com.sefonsoft.oa.service.common.IndustryService;
import com.sefonsoft.oa.system.response.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xielf
 */
@RestController
@RequestMapping("/industry")
public class IndustryController extends BaseController{


  private IndustryService industryService;

  @Autowired
  public void setIndustryService(IndustryService industryService) {
    this.industryService = industryService;
  }


  /**
   * 获取行业
   * @return
   */
  @GetMapping
  @ApiOperation("获取行业")
  public Response get(){
    try {
      return success(industryService.industryList());
    } catch (Exception e) {
      e.printStackTrace();
      return failure("获取行业出错");
    }
  }

}
