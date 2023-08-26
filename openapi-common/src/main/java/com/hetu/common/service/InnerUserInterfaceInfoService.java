package com.hetu.common.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hetu.common.model.entity.UserInterfaceInfo;

/**
 *
 */
public interface InnerUserInterfaceInfoService {


    /**
     * 调用接口统计
     * @param interfaceInfoId
     * @param userId
     */
    boolean invokeCount(long interfaceInfoId, long userId);


    /**
     * @param interfaceInfoId
     * @param userId
     * @return boolean
     */
    boolean checkLeftNum(long interfaceInfoId, long userId);

}
