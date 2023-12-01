package com.duu.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duu.api.model.vo.UserInterfaceInfoVO;
import com.duu.common.model.entity.UserInterfaceInfo;

import java.util.List;

/**
* @author 47228
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Mapper
* @createDate 2023-11-23 22:40:12
* @Entity com.duu.api.model.entity.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    List<UserInterfaceInfo> analysisTop3Interface(int limit);
}




