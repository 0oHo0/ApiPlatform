package com.duu.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.duu.common.model.entity.InterfaceInfo;

/**
* @author 47228
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-11-21 16:18:37
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);

    boolean onlineInterfaceInfo(Long id);

    boolean offlineInterfaceInfo(Long id);

    InterfaceInfo getInterfaceInfo(String path, String method);
}
