package com.hetu.common.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hetu.common.model.entity.InterfaceInfo;

/**
 *
 */
public interface InnerInterfaceInfoService {
    /***
     * @author 河荼
     * @Description
     * @Date 13:53 2023/8/25
     * @param url,method
     * @return model.entity.User
     */
    InterfaceInfo getInterfaceInfo(String url, String method);
}
