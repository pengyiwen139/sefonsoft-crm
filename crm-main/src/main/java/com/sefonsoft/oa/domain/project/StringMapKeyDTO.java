package com.sefonsoft.oa.domain.project;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author ：PengYiWen
 * @date ：Created in 2019/11/16 19:18
 * @description：用于接收键值对的实体类
 * @modified By：
 */
@ApiModel("用于存储键值对的实体类，key为字符串类型")
@Data
public class StringMapKeyDTO {

    private String id;
    private String name;

}
