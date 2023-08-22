package com.yupi.project.model.dto.interfaceInfo;

import com.yupi.project.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 *
 * @author yupi
 */

@Data
public class InterfaceInfoInvokeRequest extends PageRequest implements Serializable {

    private Long id;

    /**
     * 请求参数
     */
    private String userRequestParams;


    private static final long serialVersionUID = 1L;
}