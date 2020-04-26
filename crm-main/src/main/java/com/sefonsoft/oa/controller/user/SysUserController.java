package com.sefonsoft.oa.controller.user;

import com.sefonsoft.oa.controller.common.BaseController;
import com.sefonsoft.oa.domain.user.SysUserQueryDTO;
import com.sefonsoft.oa.domain.user.vo.Pretask;
import com.sefonsoft.oa.entity.sysemployee.SysEmployee;
import com.sefonsoft.oa.entity.user.SysUser;
import com.sefonsoft.oa.service.user.SysUserService;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.BeanUtils;
import com.sefonsoft.oa.system.utils.MD5;
import com.sefonsoft.oa.system.utils.ObjTool;
import com.sefonsoft.oa.system.utils.PageResponse;
import com.sefonsoft.oa.system.utils.StringUtils;

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

import static com.sefonsoft.oa.system.emun.ResponseMessage.*;

/**
 * (SysUser)表控制层
 *
 * @author Aron
 * @since 2019-11-06 17:49:28
 */
@Api(tags = "用户接口")
@RestController
public class SysUserController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private SysUserService sysUserService;


    /**
     * 通过主键查询单条数据
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "根据主键id查询用户",response = SysEmployee.class)
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public Response selectOne(@ApiParam(required = true, value = "表主键id") @PathVariable Long id) {
        if (ObjTool.isNotNull(id)) {
            if (id < 0) {
                return failure(MIN_NUM_ERROR.getCode(), "id" + MIN_NUM_ERROR.getMessage() + 1);
            }
            SysUser sysUser = sysUserService.queryById(id);
            return ObjTool.isNotNull(sysUser) ? success(sysUser) : failure(NO_RESPONSE_ERROR);
        }
        return failure(NO_PARAM_ERROR.getCode(), "id"+NO_PARAM_ERROR.getMessage());
    }

    /**
     * 新增用户
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "新增用户",response = SysEmployee.class)
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public Response insert(@Valid @RequestBody SysUser sysUser, BindingResult result) {
        if (result.hasErrors()) {
            return failure(result);
        }

        String employeeId = sysUser.getUserId();
        if(null==employeeId){
            return failure("用户编号不能为空");
        }
        int employeeIdCount = sysUserService.checkUnique(employeeId);
        if (employeeIdCount>0)
        {
            return failure(UNIQUE_EMPLOYEEID_ERROR);
        }
        return sysUserService.insert(sysUser) ? success() : failure(INSERT_ERROR);
    }

    /**
     * 修改用户信息
     * @param sysUser
     * @param result
     * @return Response
     */
    @ApiOperation(value = "修改用户信息",response = SysEmployee.class)
    @RequestMapping(value = "/user", method = RequestMethod.PATCH)
    public Response update(@Valid @RequestBody SysUser sysUser, BindingResult result) {
        if (result.hasErrors()) {
            return failure(result);
        }
        String employeeId = sysUser.getUserId();
        if(null==employeeId){
            return failure("用户编号不能为空");
        }
        SysUser sysUserInfo = BeanUtils.copyProperties(sysUser, SysUser.class);

        //判断密码是否需要加密，如果用户在这个线程中修改了密码（密码长度不等于32），则需要给密码加密；如果用户在这个线程中没有修改密码，则密码原封不动
        String password = sysUser.getPassword();
        if ((ObjTool.isNotNull(password)) && (password.length() != 32)) {
            String dbPassword = sysUserService.getDbPwdByUserId(employeeId);
            password = MD5.crypt(password);
            if (dbPassword.equals(password)) {
                return failure("新密码不能与原密码相同");
            }
            sysUserInfo.setPassword(password);
        }

        return sysUserService.update(sysUserInfo) > 0 ? success() : failure(UPDATE_ERROR);
    }
    
    
    /**
     * 修改用户基础信息
     * @param sysUser
     * @param result
     * @return Response
     */
    @ApiOperation(value = "修改用户基础信息")
    @PutMapping(value = "/user/checkProfile")
    public Response updateProfile(@Valid @RequestBody CheckProfilesVO sysUser) {
      sysUser.setUserId(getLoginDTO().getUserId());
      sysUserService.checkProfile(sysUser);
      return success();
    }
    
//    @SuppressWarnings("unchecked")
//    @ApiOperation(value = "获取用户前置任务")
//    @GetMapping(value = "/user/pretask")
//    @Deprecated
//    public Response<Pretask> todo() {
//      List<Pretask> todos = sysUserService.getPretask(getLoginDTO().getUserId());
//      return success(todos);
//    }
    

    /**
     * 根据id删除用户
     * @return
     */
    @ApiOperation("删除单个用户")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public Response delete(@ApiParam(required = true, value = "表主键id") @PathVariable Long id) {

        return sysUserService.deleteById(id) ? success() : failure(DELETE_ERROR);
    }

    /**
     * 根据id批量删除用户
     * @return Response
     */
    @ApiOperation("批量删除用户")
    @RequestMapping(value = "/user/delAll", method = RequestMethod.DELETE)
    @ApiImplicitParam(name="ids", value="id以逗号分割", required=true, paramType="query" ,allowMultiple=true, dataType = "String")
    public Response deleteIds(String ids) {
        if(ids==null){
            return failure("删除id不能为空");
        }
        List<String> idArray = Arrays.asList(ids.split(","));

        return sysUserService.deleteByIds(idArray)? success() : failure(DELETE_ERROR);
    }

    /**
     * 条件查询用户工列表
     * @param sysUserQueryDTO
     * @return Response
     */
    @ApiOperation(value = "条件查询用户列表",response = SysUserQueryDTO.class)
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Response findUserByCondition(SysUserQueryDTO sysUserQueryDTO) {
        List<SysUserQueryDTO> userDto = sysUserService.getCondition(sysUserQueryDTO);
        int totalUserDto = sysUserService.findCondition(sysUserQueryDTO);
        PageResponse pageResponse = new PageResponse<>(totalUserDto, userDto);
        return new Response<>().success(pageResponse);
    }

    /**
     * 重置密码
     * @return Response
     */
    @ApiOperation(value = "重置密码")
    @RequestMapping(value = "/resetPwd/{userId}", method = RequestMethod.POST)
    public Response resetPwd(@PathVariable String userId) {
        if (!ObjTool.isNotNull(userId)) {
            return failure("用户编号不能为空");
        }
        return sysUserService.resetPwd(userId) ? success() : failure(UPDATE_ERROR);
    }

}