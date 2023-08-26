package com.yupi.project.service.impl.inner;


import com.hetu.common.service.InnerUserInterfaceInfoService;
import com.yupi.project.service.UserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @BelongsProject: OpenAPI-backend
 * @BelongsPackage: com.yupi.project.service.impl
 * @ClassName: InnerUserInterfaceInfoServiceImpl
 * @Description: TODO
 * @Author: FengZH
 * @Date: 2023/08/25 14:36:03
 * @Version: V1.0
 **/
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {
    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.invokeCount(interfaceInfoId,userId);
    }

    @Override
    public boolean checkLeftNum(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.checkLeftNum(interfaceInfoId,userId);
    }
}
