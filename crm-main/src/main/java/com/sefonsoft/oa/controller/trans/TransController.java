package com.sefonsoft.oa.controller.trans;
import com.sefonsoft.oa.domain.trans.TransCondition;
import com.sefonsoft.oa.domain.trans.TransDto;
import com.sefonsoft.oa.system.utils.ValidateUtils;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.service.trans.TransService;
import com.sefonsoft.oa.system.utils.PageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Trans测试控制器
 * @author Aron
 * @version 1.0.0
 */
@RestController
public class TransController {
    private static final Logger logger = LoggerFactory.getLogger(TransController.class);

    @Autowired
    private TransService transService;

    @RequestMapping(value = "/br/trans", method = RequestMethod.POST)
    public Response<?> addTrans(@Valid @RequestBody TransDto transDto, BindingResult result) {
        if (result.hasErrors()) {
            return new Response<>().failure(ValidateUtils.getValidateErrors(result.getAllErrors()));
        }
        try {
            transDto = transService.addTrans(transDto);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new Response<>().failure(e.getMessage());
        }
        return new Response<>().success(transDto);
    }



    @RequestMapping(value = "/br/trans/{id}", method = RequestMethod.DELETE)
    public Response deleteTrans(@PathVariable Long id) {
        int number = transService.deleteTrans(id);
        return new Response<>().success();
    }


    @RequestMapping(value = "/br/trans/{id}", method = RequestMethod.PATCH)
    public Response editTrans(@PathVariable Long id, @Valid @RequestBody TransDto transDto, BindingResult result) {
        if (result.hasErrors()) {
            return new Response<>().failure(ValidateUtils.getValidateErrors(result.getAllErrors()));
        }
        transService.editTrans(transDto);
        return new Response<>().success(transDto);
    }



    @RequestMapping(value = "/br/trans/{id}", method = RequestMethod.GET)
    public Response findTransById(@PathVariable Long id) {
        TransDto transDto = transService.findTransById(id);
        return new Response<>().success(transDto);
    }



    @RequestMapping(value = "/br/trans", method = RequestMethod.GET)
    public Response findTransByCondition(TransCondition transCondition) {
        List<TransDto> transDtos = transService.getTransListByCondition(transCondition);
        int totalTrans = transService.findTranTotalByCondition(transCondition);
        PageResponse pageResponse = new PageResponse<>(totalTrans, transDtos);
        return new Response<>().success(pageResponse);
    }


}