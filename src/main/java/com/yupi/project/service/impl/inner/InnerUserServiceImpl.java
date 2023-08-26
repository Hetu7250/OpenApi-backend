package com.yupi.project.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hetu.common.model.entity.User;
import com.hetu.common.service.InnerUserService;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.mapper.UserMapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;


import javax.annotation.Resource;

/**
@BelongsProject: OpenAPI-backend
@BelongsPackage: com.yupi.project.service.impl
@ClassName: InnerUserServiceImpl
@Description: TODO
@Author: FengZH
@Date: 2023/08/25 14:28:00
@Version: V1.0
**/
@DubboService
public class InnerUserServiceImpl implements InnerUserService {

    @Resource
    UserMapper userMapper;

    @Override
    public User getInvokeUser(String accessKey) {
        if (StringUtils.isAnyBlank(accessKey)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("accessKey",accessKey);
        return userMapper.selectOne(userQueryWrapper);
    }
}
