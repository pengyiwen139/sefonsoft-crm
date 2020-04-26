package com.sefonsoft.oa.controller.importexport;

import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.service.project.ImportProjectService;
import com.sefonsoft.oa.system.annotation.CurrentUser;
import com.sefonsoft.oa.system.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;

/**
 * @author ：PengYiWen
 * @date ：Created in 2019/12/25 14:27
 * @description：导入项目
 * @modified By：
 */
@Api(tags = "导入项目")
@RestController
@Deprecated
public class ImportProjectController extends Response<Object> {

    @Autowired
    private ImportProjectService importProjectService;

    @ApiOperation(value = "excel导入项目")
    @RequestMapping(value = "/importProjects", method = RequestMethod.PUT)
    public Response importProjects(@RequestParam("file") MultipartFile file, @CurrentUser LoginUserDTO loginUserDTO, HttpSession session) throws IOException, InvalidFormatException, InstantiationException, IllegalAccessException, ParseException {
        return importProjectService.importProjects(file, loginUserDTO, session);
    }

}