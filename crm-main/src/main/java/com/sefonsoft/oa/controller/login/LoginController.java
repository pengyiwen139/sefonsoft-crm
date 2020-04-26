package com.sefonsoft.oa.controller.login;

import com.sefonsoft.oa.controller.common.BaseController;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.domain.user.vo.Pretask;
import com.sefonsoft.oa.domain.user.vo.UserVO;
import com.sefonsoft.oa.service.user.SysUserService;
import com.sefonsoft.oa.system.interceptor.LoginInterceptor;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.MD5;
import com.sefonsoft.oa.system.utils.ObjTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.sefonsoft.oa.system.constant.UserConstant.OUT_JOB;
import static com.sefonsoft.oa.system.constant.UserConstant.USER_INFO;

import java.util.List;

/**
 * @author ：Aron
 * @version : 1.0
 * @description：登录接口
 * @date ：2019/11/16
 */
@RestController
@Api(tags = "登录接口")
public class LoginController extends BaseController {

    @Autowired
    private SysUserService userService;

    /**
     * 用户登录
     *
     * @param
     * @return Response
     */
    @ApiOperation(value = "用户登录", response = LoginUserDTO.class)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response login(@Valid @RequestBody LoginUserDTO loginUser,
                          @ApiParam(hidden = true) BindingResult result,
                          @ApiParam(hidden = true) HttpSession session) {

        String sVerifyCode = (String) session.getAttribute(VERIFY_CODE);
        session.removeAttribute(VERIFY_CODE);
        String verifyCode = loginUser.getVerifyCode();

        Integer num = (Integer) session.getAttribute(ERROR_NUM);
        if(num==null) {
          num=0;
        }

        if(num>=3 && (verifyCode==null || !verifyCode.equalsIgnoreCase(sVerifyCode))){
            return failure("验证码不正确");
        }

        if (result.hasErrors()) {
            return failure(result);
        }
        LoginUserDTO dbUserInfo = userService.getSimpleLoginInfo(loginUser);
        if (!ObjTool.isNotNull(dbUserInfo) || !MD5.crypt(loginUser.getPassword()).equals(dbUserInfo.getPassword())) {
            num++;
            session.setAttribute(ERROR_NUM,num);
            return failure("用户名或密码错误",num);
        }
        Integer jobStatus = dbUserInfo.getJobStatus();
        if (ObjTool.isNotNull(jobStatus) && jobStatus.equals(OUT_JOB)) {
            num++;
            session.setAttribute(ERROR_NUM,num);
            return failure("此员工已离职，禁止登录",num);
        }
//        if (!MD5.crypt(loginUser.getPassword()).equals(password)) {
//            num++;
//            session.setAttribute(ERROR_NUM,num);
//            return failure("登录密码错误",num);
//        }
        if (!ObjTool.isNotNull(dbUserInfo.getGradeId())) {
            return failure("当前登录账号没有职系数据，请联系管理员");
        }

        // 设置屏障
        LoginInterceptor.userSessionMap.put(dbUserInfo.getUserId(), session.getId());
        
        
        

        session.setAttribute(USER_INFO+dbUserInfo.getUserId(), dbUserInfo);
//        session.setMaxInactiveInterval(3600);
        String userId = dbUserInfo.getUserId();
        if (ObjTool.isNotNull(userId)) {
            userService.setLoginTime(userId);
        }
        session.removeAttribute(ERROR_NUM);
        
        // 检查前置任务
        List<Pretask> todos = userService.getPretask(dbUserInfo.getUserId());
        
        UserVO uvo= new UserVO();
        BeanUtils.copyProperties(dbUserInfo, uvo);
        
        uvo.setPretasks(todos);
        return success(uvo);
    }

    /**
     * 用户登出
     *
     * @param
     * @return Response
     */
    @ApiOperation(value = "用户登出")
    @RequestMapping(value = "/logout/{userId}", method = RequestMethod.POST)
    public Response logout(HttpSession session, HttpServletRequest request, @PathVariable String userId) {
        try {
            if (!ObjTool.isNotNull(userId)) {
                return failure("登出失败,未获取到用户信息");
            }
            session.removeAttribute(USER_INFO + userId);
            session.invalidate();
            Cookie cookie = request.getCookies()[0];
            cookie.setMaxAge(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success();
    }

}