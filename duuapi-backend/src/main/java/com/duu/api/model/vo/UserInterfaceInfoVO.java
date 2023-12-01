package com.duu.api.model.vo;

import com.duu.common.model.entity.InterfaceInfo;
import com.duu.common.model.entity.UserInterfaceInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : duu
 * @data : 2023/11/27
 * @from ：https://github.com/0oHo0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class UserInterfaceInfoVO extends InterfaceInfo{

    private Integer totalNum;

    private static final long serialVersionUID = 1L;
}
