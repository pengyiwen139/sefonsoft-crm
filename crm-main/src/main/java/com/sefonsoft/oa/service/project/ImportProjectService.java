package com.sefonsoft.oa.service.project;

import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.system.response.Response;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;

public interface ImportProjectService {

    Response importProjects(MultipartFile file, LoginUserDTO loginUserDTO, HttpSession session) throws IOException, IllegalAccessException, ParseException, InstantiationException;

}