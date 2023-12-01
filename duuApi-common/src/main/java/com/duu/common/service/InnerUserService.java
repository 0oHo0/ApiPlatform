package com.duu.common.service;


import com.duu.common.model.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务
 *
 * @author duu
 */
public interface InnerUserService{

    User getInvokeUser(String accessKey);
}
