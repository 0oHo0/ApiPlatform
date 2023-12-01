package com.duu.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duu.api.common.ErrorCode;
import com.duu.api.exception.BusinessException;
import com.duu.api.service.UserInterfaceInfoService;
import com.duu.api.mapper.UserInterfaceInfoMapper;
import com.duu.common.model.entity.UserInterfaceInfo;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author 47228
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service实现
* @createDate 2023-11-23 22:40:12
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService{

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = userInterfaceInfo.getId();
        Integer status = userInterfaceInfo.getStatus();;
        Long userId = userInterfaceInfo.getUserId();
        Date createTime = userInterfaceInfo.getCreateTime();
        Date updateTime = userInterfaceInfo.getUpdateTime();
        Integer isDelete = userInterfaceInfo.getIsDelete();
        Integer leftNum = userInterfaceInfo.getLeftNum();
        Long interfaceInfoId = userInterfaceInfo.getInterfaceInfoId();
        Integer totalNum = userInterfaceInfo.getTotalNum();


//        // 创建时，所有参数必须非空
        if (add) {
            if (id<=0||userId<=0){
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
            if(leftNum<=0){
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
    }

    @Override
    public Boolean invokeCount(long interfaceId, long userId) {
        if (interfaceId <=0 || userId <=0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interfaceInfoId",interfaceId);
        updateWrapper.eq("userId",userId);
        updateWrapper.setSql("totalNum = totalNum+1,leftNum = leftNum - 1");
        return this.update(updateWrapper);
    }
}




