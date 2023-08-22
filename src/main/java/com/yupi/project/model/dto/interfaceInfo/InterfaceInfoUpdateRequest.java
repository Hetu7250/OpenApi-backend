package com.yupi.project.model.dto.interfaceInfo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 更新请求
 *
 * @TableName product
 */
@Data
public class InterfaceInfoUpdateRequest implements Serializable {

    /**
     * 主键
     */

    private Long id;

    /**
     * 接口名
     */
    private String name;

    /**
     * 接口地址
     */
    private String url;


    /**
     * 请求参数
     */
    private String requestParams;


    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 接口状态(0off 1on)
     */
    private Integer status;

    /**
     * 请求类型
     */
    private String method;


    /**
     * 描述
     */
    private String description;


    private static final long serialVersionUID = 1L;
}