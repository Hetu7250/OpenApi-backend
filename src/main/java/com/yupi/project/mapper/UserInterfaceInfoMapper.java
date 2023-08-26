package com.yupi.project.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hetu.common.model.entity.UserInterfaceInfo;

import java.util.List;

public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    /**
     * @return java.util.List<com.hetu.common.model.entity.UserInterfaceInfo>
     */
    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);
}




