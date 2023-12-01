package com.duu.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.duu.api.annotation.AuthCheck;
import com.duu.api.common.BaseResponse;
import com.duu.api.common.ErrorCode;
import com.duu.api.common.ResultUtils;
import com.duu.api.exception.BusinessException;
import com.duu.api.mapper.UserInterfaceInfoMapper;
import com.duu.api.model.vo.UserInterfaceInfoVO;
import com.duu.api.service.InterfaceInfoService;
import com.duu.common.model.entity.InterfaceInfo;
import com.duu.common.model.entity.UserInterfaceInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : duu
 * @data : 2023/11/27
 * @from ：https://github.com/0oHo0
 **/
@RestController
@RequestMapping("/analysis")
public class AnalysisController {
    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @GetMapping("/top")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<UserInterfaceInfoVO>> analysisTop3Interface(int limit) {
        List<UserInterfaceInfo> userInterfaceInfoS = userInterfaceInfoMapper.analysisTop3Interface(limit);
        Map<Long, List<UserInterfaceInfo>> interfaceIdObjMap =
                userInterfaceInfoS.stream().collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", interfaceIdObjMap.keySet());
        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.list(queryWrapper);
        if (interfaceInfoList == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        List<UserInterfaceInfoVO> userInterfaceInfoVOList = new ArrayList<>();
        interfaceInfoList.stream().forEach(item -> {
            UserInterfaceInfoVO userInterfaceInfoVO = new UserInterfaceInfoVO();
            BeanUtils.copyProperties(item, userInterfaceInfoVO);
            int totalNum = interfaceIdObjMap.get(item.getId()).get(0).getTotalNum();
            userInterfaceInfoVO.setTotalNum(totalNum);
            userInterfaceInfoVOList.add(userInterfaceInfoVO);
        });
        return ResultUtils.success(userInterfaceInfoVOList);
    }
}
