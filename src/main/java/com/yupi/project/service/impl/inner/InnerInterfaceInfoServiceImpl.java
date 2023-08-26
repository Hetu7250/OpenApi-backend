package com.yupi.project.service.impl.inner;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hetu.common.model.entity.InterfaceInfo;
import com.hetu.common.service.InnerInterfaceInfoService;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.mapper.InterfaceInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @BelongsProject: OpenAPI-backend
 * @BelongsPackage: com.yupi.project.service.impl
 * @ClassName: InnerInterfaceInfoServiceImpl
 * @Description: TODO
 * @Author: FengZH
 * @Date: 2023/08/25 14:30:15
 * @Version: V1.0
 **/
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {
    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;
    @Override
    public InterfaceInfo getInterfaceInfo(String url, String method) {

        if (StringUtils.isAnyBlank(url,method)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        QueryWrapper<InterfaceInfo> interfaceInfoQueryWrapper = new QueryWrapper<>();
        interfaceInfoQueryWrapper.eq("url",url);
        interfaceInfoQueryWrapper.eq("method",method);

        return interfaceInfoMapper.selectOne(interfaceInfoQueryWrapper);
    }
}
