package com.sefonsoft.oa.controller.head;

import com.sefonsoft.oa.controller.common.BaseController;
import com.sefonsoft.oa.domain.head.HeadQueryDTO;
import com.sefonsoft.oa.domain.project.ProjectCensusDTO;
import com.sefonsoft.oa.service.head.HeadPageService;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.ObjTool;
import com.sefonsoft.oa.system.utils.UUIDUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sefonsoft.oa.system.emun.ResponseMessage.QUERY_ERROR;

/**
 * @author ：PengYiWen
 * @date ：Created in 2019/11/12 17:10
 * @description：首页相关接口
 * @modified By：
 */
@Api(tags = "首页客户，项目相关接口")
@RestController
public class HeadPageController extends BaseController {

    @Autowired
    private HeadPageService headPageService;

    /**
     * 客户个数统计
     * @return
     */
    @ApiOperation(value = "客户个数统计")
    @RequestMapping(value = "/customersCount", method = RequestMethod.POST)
    public Response customersCount(@ApiParam(required = false) @RequestBody HeadQueryDTO headQueryDTO) {
        Map<String, Object> map = new HashMap<>(1);
        Integer customersCount = headPageService.customersCount(headQueryDTO);
        if (!ObjTool.isNotNull(customersCount)) {
            return failure(QUERY_ERROR);
        }
        map.put("customersCount", customersCount);
        return success(map);
    }

    /**
     * 机会项目个数统计
     * @return
     */
    @ApiOperation(value = "机会项目个数统计")
    @RequestMapping(value = "/projectsCount", method = RequestMethod.POST)
    public Response projectsCount(@ApiParam(required = false) @RequestBody HeadQueryDTO headQueryDTO) {
        Map<String, Object> map = new HashMap<>(1);
        Integer projectsCount = headPageService.projectsCount(headQueryDTO);
        if (!ObjTool.isNotNull(projectsCount)) {
            return failure(QUERY_ERROR);
        }
        map.put("projectsCount", projectsCount);
        return success(map);
    }

    /**
     * 销售立项个数统计
     * @return
     */
    @ApiOperation(value = "销售立项个数统计")
    @RequestMapping(value = "/saleProjectsCount", method = RequestMethod.POST)
    public Response saleProjectsCount(@ApiParam(required = false) @RequestBody HeadQueryDTO headQueryDTO) {
        Map<String, Object> map = new HashMap<>(1);
        Integer saleProjectsCount = headPageService.saleProjectsCount(headQueryDTO);
        if (!ObjTool.isNotNull(saleProjectsCount)) {
            return failure(QUERY_ERROR);
        }
        map.put("saleProjectsCount", saleProjectsCount);
        return success(map);
    }

    /**
     * 销售漏斗统计
     *
     * @return
     */
    @ApiOperation(value = "销售漏斗统计", response = ProjectCensusDTO.class)
    @RequestMapping(value = "/salesFunnel", method = RequestMethod.POST)
    public Response salesFunnel(@ApiParam(required = false) @RequestBody HeadQueryDTO headQueryDTO) {
        ProjectCensusDTO projectCensusDTO = new ProjectCensusDTO(Integer.parseInt(UUIDUtil.getRandStr(3)), "初期沟通10%", new BigDecimal(UUIDUtil.getRandStr(3)+"."+UUIDUtil.getRandStr(4)), Integer.parseInt(UUIDUtil.getRandStr(3)));
        ProjectCensusDTO projectCensusDTO1 = new ProjectCensusDTO(Integer.parseInt(UUIDUtil.getRandStr(3)), "方案交流30%", new BigDecimal(UUIDUtil.getRandStr(3)+"."+UUIDUtil.getRandStr(4)), Integer.parseInt(UUIDUtil.getRandStr(3)));
        ProjectCensusDTO projectCensusDTO2 = new ProjectCensusDTO(Integer.parseInt(UUIDUtil.getRandStr(3)), "客户立项50%", new BigDecimal(UUIDUtil.getRandStr(3)+"."+UUIDUtil.getRandStr(4)), Integer.parseInt(UUIDUtil.getRandStr(3)));
        ProjectCensusDTO projectCensusDTO3 = new ProjectCensusDTO(Integer.parseInt(UUIDUtil.getRandStr(3)), "招投标中70%", new BigDecimal(UUIDUtil.getRandStr(3)+"."+UUIDUtil.getRandStr(4)), Integer.parseInt(UUIDUtil.getRandStr(3)));
        ProjectCensusDTO projectCensusDTO4 = new ProjectCensusDTO(Integer.parseInt(UUIDUtil.getRandStr(3)), "合同谈判90%", new BigDecimal(UUIDUtil.getRandStr(3)+"."+UUIDUtil.getRandStr(4)), Integer.parseInt(UUIDUtil.getRandStr(3)));
        ProjectCensusDTO projectCensusDTO5 = new ProjectCensusDTO(Integer.parseInt(UUIDUtil.getRandStr(3)), "合同落地100%%", new BigDecimal(UUIDUtil.getRandStr(3)+"."+UUIDUtil.getRandStr(4)), Integer.parseInt(UUIDUtil.getRandStr(3)));
        List<ProjectCensusDTO> list = new ArrayList<>();
        list.add(projectCensusDTO);
        list.add(projectCensusDTO1);
        list.add(projectCensusDTO2);
        list.add(projectCensusDTO3);
        list.add(projectCensusDTO4);
        list.add(projectCensusDTO5);
        return success(list);
    }

}