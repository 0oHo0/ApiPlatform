package com.duu.api.service.impl.Inner;

import com.duu.api.service.UserService;
import com.duu.common.model.entity.User;
import com.duu.common.service.InnerUserService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author : duu
 * @data : 2023/11/26
 * @from ：https://github.com/0oHo0
 **/
@DubboService
public class InnerUserServiceImpl implements InnerUserService {
    @Resource
    private UserService userService;
    @Override
    public User getInvokeUser(String accessKey) {
        User invokeUser = userService.getInvokeUser(accessKey);
        return invokeUser;
    }
}
