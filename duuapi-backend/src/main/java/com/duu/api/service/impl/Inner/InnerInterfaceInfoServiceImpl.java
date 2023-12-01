package com.duu.api.service.impl.Inner;

import com.duu.api.service.InterfaceInfoService;
import com.duu.common.model.entity.InterfaceInfo;
import com.duu.common.service.InnerInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author : duu
 * @data : 2023/11/26
 * @from ：https://github.com/0oHo0
 **/
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {
    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {

    }

    @Override
    public InterfaceInfo getInterfaceInfo(String path, String method) {
        return interfaceInfoService.getInterfaceInfo(path, method);
    }
}
