package com.duu.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duu.api.common.ErrorCode;
import com.duu.api.exception.BusinessException;
import com.duu.api.mapper.InterfaceInfoMapper;

import com.duu.api.model.enums.InterfaceStatusEnum;
import com.duu.api.service.InterfaceInfoService;

import com.duu.common.model.entity.InterfaceInfo;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author 47228
* @description 针对表【interface_info(接口信息)】的数据库操作Service实现
* @createDate 2023-11-21 16:18:37
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
        String description = interfaceInfo.getDescription();
        String url = interfaceInfo.getUrl();
        String requestHeader = interfaceInfo.getRequestHeader();
        String responseHeader = interfaceInfo.getResponseHeader();
        Integer status = interfaceInfo.getStatus();
        String method = interfaceInfo.getMethod();
        Long userid = interfaceInfo.getUserId();
        Date createTime = interfaceInfo.getCreateTime();
        Date updateTime = interfaceInfo.getUpdateTime();
        Integer isDelete = interfaceInfo.getIsDelete();

//        // 创建时，所有参数必须非空
//        if (add) {
//            if (StringUtils.isAnyBlank(content, job, place, education, loveExp) || ObjectUtils.anyNull(age, gender)) {
//                throw new BusinessException(ErrorCode.PARAMS_ERROR);
//            }
//        }
//        if (StringUtils.isNotBlank(content) && content.length() > 8192) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容过长");
//        }
//        if (reviewStatus != null && !interfaceInfoReviewStatusEnum.getValues().contains(reviewStatus)) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        if (age != null && (age < 18 || age > 100)) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "年龄不符合要求");
//        }
//        if (gender != null && !interfaceInfoGenderEnum.getValues().contains(gender)) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "性别不符合要求");
//        }
    }

    @Override
    public boolean onlineInterfaceInfo(Long id) {
        if (id<=0)
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        InterfaceInfo interfaceInfo = this.getById(id);
        if (interfaceInfo==null)
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        interfaceInfo.setStatus(InterfaceStatusEnum.ONLINE.getValue());
        // todo 验证接口是否可用

        return this.updateById(interfaceInfo);
    }

    @Override
    public boolean offlineInterfaceInfo(Long id) {
        if (id<=0)
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        InterfaceInfo interfaceInfo = this.getById(id);
        if (interfaceInfo==null)
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        interfaceInfo.setStatus(InterfaceStatusEnum.OFFLINE.getValue());
        return this.save(interfaceInfo);
    }

    @Override
    public InterfaceInfo getInterfaceInfo(String path, String method) {
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url",path);
        queryWrapper.eq("method",method);
        InterfaceInfo interfaceInfo = this.getOne(queryWrapper);
        if (interfaceInfo == null)
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        return interfaceInfo;
    }
}




