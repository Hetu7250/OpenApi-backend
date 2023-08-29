package com.yupi.project.model.vo;

import com.hetu.common.model.entity.InterfaceInfo;
import com.hetu.common.model.entity.UserInterfaceInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class UserInterfaceInfoVO extends UserInterfaceInfo {


    private String interfaceInfoName;

    private static final long serialVersionUID = 1L;
}