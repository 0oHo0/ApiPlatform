package com.duu.common.service;


import com.duu.common.model.entity.InterfaceInfo;

/**
* @author 47228
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-11-21 16:18:37
*/
public interface InnerInterfaceInfoService{
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);

    InterfaceInfo getInterfaceInfo(String path,String method);
}
