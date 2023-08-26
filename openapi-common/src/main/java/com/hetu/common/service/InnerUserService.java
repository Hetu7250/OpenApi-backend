package com.hetu.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hetu.common.model.entity.User;

/**
 * 用户服务
 *
 * @author yupi
 */
public interface InnerUserService {

    /***
     * @author 河荼
     * @Description
     * @Date 13:53 2023/8/25
     * @param accessKey,secretKey
     * @return model.entity.User
     */
    User getInvokeUser(String accessKey);



}
