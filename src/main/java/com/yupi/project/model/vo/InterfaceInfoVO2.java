package com.yupi.project.model.vo;

import com.hetu.common.model.entity.InterfaceInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoVO2 extends InterfaceInfo {


    /**
    * 剩余调用次数
    */
    private Integer leftNum;

    private static final long serialVersionUID = 1L;
}