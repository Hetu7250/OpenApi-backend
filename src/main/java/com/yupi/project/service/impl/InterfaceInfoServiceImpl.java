package com.yupi.project.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hetu.common.model.entity.InterfaceInfo;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.mapper.InterfaceInfoMapper;
import com.yupi.project.service.InterfaceInfoService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService {
    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = interfaceInfo.getId();
        String name = interfaceInfo.getName();
        String url = interfaceInfo.getUrl();
        String requestHeader = interfaceInfo.getRequestHeader();
        String responseHeader = interfaceInfo.getResponseHeader();
        Integer status = interfaceInfo.getStatus();
        String method = interfaceInfo.getMethod();
        Long userId = interfaceInfo.getUserId();
        Date create_time = interfaceInfo.getCreate_time();
        Date update_time = interfaceInfo.getUpdate_time();
        String description = interfaceInfo.getDescription();
        Integer is_deleted = interfaceInfo.getIs_deleted();
        // 创建时，所有参数必须非空
        if (add) {
            if (StringUtils.isAnyBlank(name, url, requestHeader, responseHeader, method, description)
                    ) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if (StringUtils.isNotBlank(name) && name.length() > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口名过长");
        }
    }
}




