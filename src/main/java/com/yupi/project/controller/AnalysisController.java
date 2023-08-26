package com.yupi.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hetu.common.model.entity.InterfaceInfo;
import com.hetu.common.model.entity.UserInterfaceInfo;
import com.yupi.project.annotation.AuthCheck;
import com.yupi.project.common.BaseResponse;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.common.ResultUtils;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.mapper.UserInterfaceInfoMapper;
import com.yupi.project.model.vo.InterfaceInfoVO;
import com.yupi.project.service.InterfaceInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @BelongsProject: OpenAPI-backend
 * @BelongsPackage: com.yupi.project.controller
 * @ClassName: AnalysisController
 * @Description: TODO
 * @Author: FengZH
 * @Date: 2023/08/26 16:07:14
 * @Version: V1.0
 **/
@RestController
@RequestMapping("/analysis")
public class AnalysisController {
    @Resource
    UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    InterfaceInfoService interfaceInfoService;

    @GetMapping("/top/interface/invoke")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<InterfaceInfoVO>> listTopInvokeInterfaceInfo(){
        //查询调用次数最多的接口
        List<UserInterfaceInfo> userInterfaceInfos = userInterfaceInfoMapper.listTopInvokeInterfaceInfo(3);
        //按照接口id分组
        Map<Long, List<UserInterfaceInfo>> interfaceInfoiIdObjMap
                = userInterfaceInfos.stream().collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));
        //创建查寻接口信息条件包装器
        QueryWrapper<InterfaceInfo> interfaceInfoQueryWrapper = new QueryWrapper<>();
        interfaceInfoQueryWrapper.in("id",interfaceInfoiIdObjMap.keySet());
        List<InterfaceInfo> list = interfaceInfoService.list(interfaceInfoQueryWrapper);
        //判断是否为空
        if (CollectionUtils.isEmpty(list)){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        //构建接口信息VO列表,使用流式处理将接口信息映射为接口信息VO对象,并加入列表
        return ResultUtils.success(list.stream().map(interfaceInfo->{
            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
            BeanUtils.copyProperties(interfaceInfo,interfaceInfoVO);
            int totalNum = interfaceInfoiIdObjMap.get(interfaceInfo.getId()).get(0).getTotalNum();
            interfaceInfoVO.setTotalNum(totalNum);
            return interfaceInfoVO;
        }).collect(Collectors.toList()));
    }
}
