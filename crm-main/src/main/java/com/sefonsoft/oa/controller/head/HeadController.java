package com.sefonsoft.oa.controller.head;

import com.sefonsoft.oa.controller.common.BaseController;
import com.sefonsoft.oa.domain.head.HeadQueryDTO;
import com.sefonsoft.oa.service.head.HeadPageService;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.ObjTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.sefonsoft.oa.system.emun.ResponseMessage.QUERY_ERROR;

/**
 * @author ：Aron
 * @version : 首页展示接口
 * @description：1.0
 * @date ：2019/11/13
 */
@Api(tags = "销售首页合同相关接口")
@RestController
public class HeadController extends BaseController {


    @Autowired
    private HeadPageService headPageService;

    /**
     * 销售合同个数统计
     * @return
     */
    @ApiOperation(value = "销售合同个数")
    @RequestMapping(value = "/contractCount", method = RequestMethod.POST)
    public Response saleProjectsCount(@ApiParam(required = false) @RequestBody HeadQueryDTO headQueryDTO) {
        Map<String, Object> map = new HashMap<>(1);
        Integer contractCount = headPageService.contractCount(headQueryDTO);
        if (!ObjTool.isNotNull(contractCount)) {
            return failure(QUERY_ERROR);
        }
        map.put("contractCount", contractCount);
        return success(map);
    }

    /**
     * 销售回款金额
     * @return
     */
    @ApiOperation(value = "销售回款")
    @RequestMapping(value = "/rebackPrice", method = RequestMethod.POST)
    public Response rebackPrice() {

        //double rebackPrice = 166.14555;
        double max=169.14555,min=140.123;
        double rebackPrice = (double) (Math.random()*(max-min)+min);
        BigDecimal b = new BigDecimal(rebackPrice);
        rebackPrice = b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        Map map = new HashMap();
        map.put("rebackPrice", rebackPrice);
        return success(map);
    }


    /**
     * 合同金额
     * @return
     */
    @ApiOperation(value = "合同金额")
    @RequestMapping(value = "/contractPrice", method = RequestMethod.POST)
    public Response contractPrice() {

       //double targetSalePrice = 100;
        double max=199.14555,min=150.123;
        double targetSalePrice = (double) (Math.random()*(max-min)+min);
        BigDecimal a = new BigDecimal(targetSalePrice);
        targetSalePrice = a.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();

        //double realSalePrice = 114.14555;
        double realSalePrice = (double) (Math.random()*(max-min)+min);
        BigDecimal b = new BigDecimal(realSalePrice);
        realSalePrice = b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        Map map = new HashMap();
        map.put("targetSalePrice", targetSalePrice);
        map.put("realSalePrice", realSalePrice);

        return success(map);
    }



    /**
     * 回款金额
     * @return
     */
    @ApiOperation(value = "回款金额")
    @RequestMapping(value = "/receivablesPrice", method = RequestMethod.POST)
    public Response receivablesPrice() {

        //double targetReceivablesPrice = 200;
        double max=140.14555,min=100.423;
        double targetReceivablesPrice = (double) (Math.random()*(max-min)+min);
        BigDecimal a = new BigDecimal(targetReceivablesPrice);
        targetReceivablesPrice = a.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();

        //double realReceivablesPrice = 214.14555;
        double max1=180.14555,min1=130.423;
        double realReceivablesPrice = (double) (Math.random()*(max1-min1)+min1);
        BigDecimal b = new BigDecimal(realReceivablesPrice);
        realReceivablesPrice = b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        Map map = new HashMap();
        map.put("targetReceivablesPrice", targetReceivablesPrice);
        map.put("realReceivablesPrice", realReceivablesPrice);

        return success(map);
    }


}
