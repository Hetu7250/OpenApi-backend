package com.yupi.project.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hetu.common.model.entity.UserInterfaceInfo;

/**
 * @BelongsProject: OpenAPI-backend
 * @BelongsPackage: com.yupi.project.service
 * @ClassName: UserInterfaceInfoService
 * @Description: TODO
 * @Author: FengZH
 * @Date: 2023/08/25 14:41:18
 * @Version: V1.0
 **/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    void validInterfaceInfo(UserInterfaceInfo userInterfaceInfo,boolean add);


    boolean invokeCount(long interfaceInfoId, long userId);

    boolean checkLeftNum(long interfaceInfoId, long userId);
}
