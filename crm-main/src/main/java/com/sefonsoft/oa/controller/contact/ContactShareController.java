package com.sefonsoft.oa.controller.contact;

import com.sefonsoft.oa.controller.common.BaseController;
import com.sefonsoft.oa.domain.contact.ContactInfoQueryDTO;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.contact.ContactInfo;
import com.sefonsoft.oa.service.contact.ContactInfoService;
import com.sefonsoft.oa.service.contact.ContactShareService;
import com.sefonsoft.oa.system.annotation.CurrentUser;
import com.sefonsoft.oa.system.annotation.MethodPermission;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.BeanUtils;
import com.sefonsoft.oa.system.utils.ObjTool;
import com.sefonsoft.oa.system.utils.PageResponse;
import com.sefonsoft.oa.system.utils.UtilMethod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

import static com.sefonsoft.oa.system.constant.MenuPermissionConstant.*;
import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.MAX_PARAM;
import static com.sefonsoft.oa.system.emun.ResponseMessage.*;

/**
 * 共享联系人
 */
@RestController
@Api(tags = "客户资源池联系人相关接口")
@RequestMapping("/contact/share")
public class ContactShareController extends BaseController {
    /**
     * 服务对象所以麻烦尽快
     */
    @Resource
    private ContactShareService contactShareService;


    /**
     * xielf edited
     * 条件查询联系人列表
     * @param contactInfoQueryDTO
     * @return Response
     */
    @ApiOperation(value = "详情页/联系人列表",response = ContactInfoQueryDTO.class)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response findEmployeeByCondition(@CurrentUser LoginUserDTO loginUserDTO, ContactInfoQueryDTO contactInfoQueryDTO) {

        String customerId = contactInfoQueryDTO.getCustomerId();
        if(null == customerId){
            return failure("客户编号不能为空");
        }

//        contactInfoQueryDTO.setDataDeptIdList(UtilMethod.generatorAvailableDeptIds(loginUserDTO));
//        contactInfoQueryDTO.setEmployeeId(loginUserDTO.getUserId());

        List<ContactInfoQueryDTO> contactInfoQuery = contactShareService.getCondition(contactInfoQueryDTO);
        int totalContactInfoQuery = contactShareService.findCondition(contactInfoQueryDTO);
        PageResponse pageResponse = new PageResponse<>(totalContactInfoQuery, contactInfoQuery);
        return success(pageResponse);
    }


    /**
     * 通过主键查询单条数据
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "根据主键id查询联系人",response = ContactInfo.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response selectOne(@ApiParam(required = true, value = "表主键id") @PathVariable Long id) {
        if (ObjTool.isNotNull(id)) {
            if (id < 0) {
                return failure(MIN_NUM_ERROR.getCode(), "id" + MIN_NUM_ERROR.getMessage() + 1);
            }
            ContactInfo contactInfo = contactShareService.queryById(id);
            return ObjTool.isNotNull(contactInfo) ? success(contactInfo) : failure(NO_RESPONSE_ERROR);
        }
        return failure(NO_PARAM_ERROR.getCode(), "id"+NO_PARAM_ERROR.getMessage());
    }

    /**
     * 通过主键查询单条数据
     * xielf
     * @param customerId
     * @return 多条数据
     */
    @ApiOperation(value = "根据customerId查询联系人",response = ContactInfo.class)
    @GetMapping(value = "/contactList")
    public Response contactList(@ApiParam(required = true, value = "表主键id") 
      @RequestParam("customerId") String customerId) {
        if (ObjTool.isNotNull(customerId)) {
            List<ContactInfo> contactInfo = contactShareService.queryContactInfoByCustomerId(customerId);
            if(contactInfo==null || contactInfo.size()==0){
                return new Response().failure("当前客户无联系人，请前往客户管理新增联系人");
            }
            return new Response<>().success(contactInfo);
        }
        return new Response<>().failure(NO_PARAM_ERROR.getCode(), "id"+NO_PARAM_ERROR.getMessage());
    }

    /**
     * 新增联系人
     * @param contactInfo
     * @return Response
     */
    @MethodPermission(menuIdArray = C_CONTACT_ADD)
    @ApiOperation(value = "新增联系人",response = ContactInfo.class)
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Response insert(@Valid @RequestBody ContactInfo contactInfo, BindingResult result,
                           @CurrentUser LoginUserDTO LoginUserDTO) {
        if (result.hasErrors()) {
            return failure(result);
        }
        String customerId = contactInfo.getCustomerId();
        if(null==customerId){
            return failure("客户编号不能为空");
        }
        Response response = checkcontactPatam(contactInfo);
        if (ObjTool.isNotNull(response)) {
            return response;
        }
        contactInfo.setOperator(LoginUserDTO.getUserId());
        contactInfo.setEmployeeId(LoginUserDTO.getUserId());
        return contactShareService.insert(contactInfo) ? success() : failure(INSERT_ERROR);
    }

    /**
     * 修改联系人信息
     * @param contactInfo
     * @param result
     * @return Response
     */
    @MethodPermission(menuIdArray = C_CONTACT_UPDATE)
    @ApiOperation(value = "修改联系人信息",response = ContactInfo.class)
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Response update(@Valid @RequestBody ContactInfo contactInfo, BindingResult result) {
        if (result.hasErrors()) {
            return failure(result);
        }
        if (!ObjTool.isNotNull(contactInfo.getId())) {
            return failure(PKEY_ERROR);
        }
        Response response = checkcontactPatam(contactInfo);
        if (ObjTool.isNotNull(response)) {
            return response;
        }
        ContactInfo contactInfos = BeanUtils.copyProperties(contactInfo, ContactInfo.class);
        return contactShareService.update(contactInfos) > 0 ? success() : failure(UPDATE_ERROR);
    }

    /**
     * 根据id删除联系人
     * @return
     */
    @MethodPermission(menuIdArray = C_CONTACT_DELETE)
    @ApiOperation("删除单个联系人")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response delete(@ApiParam(required = true, value = "表主键id") @PathVariable Long id) {

        return contactShareService.deleteById(id) ? success() : failure(DELETE_ERROR);
    }

    /**
     * 根据id批量删除联系人
     * @return
     */
    @MethodPermission(menuIdArray = C_CONTACT_DELETE)
    @ApiOperation("批量删除联系人")
    @RequestMapping(value = "/delAll", method = RequestMethod.DELETE)
    @ApiImplicitParam(name="ids", value="id以逗号分割", required=true, paramType="query" ,allowMultiple=true, dataType = "String")
    public Response deleteIds(String ids) {
        if(ids==null){
            return failure("删除id不能为空");
        }
        List<String> idArray = Arrays.asList(ids.split(","));

        return contactShareService.deleteByIds(idArray)? success() : failure(DELETE_ERROR);
    }

    public Response checkcontactPatam(ContactInfo contactInfo) {
        if (contactShareService.maxFormat(contactInfo.getOfficePlane(), 32)) {
            return failure("办工座机"+ MAX_PARAM+"最长不能超过"+32);
        }
        if (contactShareService.maxFormat(contactInfo.getUniversity(), 64)) {
            return failure("毕业学校"+ MAX_PARAM+"最长不能超过"+64);
        }
        if (contactShareService.maxFormat(contactInfo.getMajor(), 64)) {
            return failure("专业"+ MAX_PARAM+"最长不能超过"+64);
        }
        if (contactShareService.maxFormat(contactInfo.getFamilyInfo(), 128)) {
            return failure("家庭状况"+ MAX_PARAM+"最长不能超过"+128);
        }
        if (contactShareService.maxFormat(contactInfo.getOther(), 128)) {
            return failure("其他"+ MAX_PARAM+"最长不能超过"+128);
        }
        return null;
    }

}