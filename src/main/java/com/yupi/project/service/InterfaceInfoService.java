package com.yupi.project.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hetu.common.model.entity.InterfaceInfo;

public interface InterfaceInfoService extends IService<InterfaceInfo> {
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
