package com.duu.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.duu.common.model.entity.UserInterfaceInfo;

/**
* @author 47228
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service
* @createDate 2023-11-23 22:40:12
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    Boolean invokeCount(long interfaceId,long userId);

}
